create or replace TRIGGER trgCapacityToVehicle
BEFORE INSERT OR UPDATE OF vehicle_state, id_park ON vehicle
FOR EACH ROW
    
WHEN ((NEW.vehicle_state =1 AND NEW.id_park IS NOT NULL ) OR (OLD.vehicle_state = 1 AND NEW.vehicle_state = 0 ) OR (OLD.vehicle_state = 0 AND NEW.vehicle_state = 1) 
    OR (OLD.id_park IS NULL AND NEW.id_park IS NOT NULL ) OR (OLD.id_park IS NOT NULL AND NEW.id_park IS NULL))    
BEGIN


    IF INSERTING THEN
        UPDATE park_capacity
            SET availability_vehicle = availability_vehicle -1
            WHERE id_park = :NEW.id_park AND id_vehicle_type = : NEW.id_vehicle_type;

    ELSIF UPDATING('vehicle_state') THEN
        IF(:OLD.vehicle_state = 0 ) THEN
            UPDATE park_capacity
                SET availability_vehicle = availability_vehicle -1
                WHERE id_park = :NEW.id_park AND id_vehicle_type = : OLD.id_vehicle_type;
        ELSE    
            UPDATE park_capacity
                SET availability_vehicle = availability_vehicle +1
                WHERE id_park = :NEW.id_park AND id_vehicle_type = : OLD.id_vehicle_type;
        END IF;

    ELSIF UPDATING ('id_park') THEN
        IF :OLD.id_park IS NULL THEN
            UPDATE park_capacity
                SET availability_vehicle = availability_vehicle -1
                WHERE id_park = :NEW.id_park AND id_vehicle_type = : OLD.id_vehicle_type;
        ELSE    
            UPDATE park_capacity
                SET availability_vehicle = availability_vehicle +1
                WHERE id_park = :OLD.id_park AND id_vehicle_type = : OLD.id_vehicle_type;
        END IF;
    END IF;
END;
/