create or replace TRIGGER trgParkVehicle
BEFORE UPDATE OF rental_end_date_hour ON rental
FOR EACH ROW
DECLARE
    v_duration  INTEGER;
    v_rental_cost NUMERIC(5,2);
    v_elevation_origin    point_interest.elevation%type;
    v_elevation_destination point_interest.elevation%type;
    v_elevation_difference  point_interest.elevation%type;
    v_user_points   INTEGER;
    v_id_vehicle_type   INTEGER;
BEGIN

    v_rental_cost := 0;
    v_user_points := 0;

    UPDATE vehicle
        SET 
            id_park = :NEW.id_park_delivery
            WHERE id_vehicle = :OLD.id_vehicle;

    SELECT id_vehicle_type INTO v_id_vehicle_type FROM vehicle WHERE id_vehicle = :OLD.id_vehicle;
    
    IF(v_id_vehicle_type = 2) THEN
    
        UPDATE scooter 
            SET
                actual_batery_capacity = max_batery_capacity*100
                WHERE id_scooter =  :OLD.id_vehicle;
        
    END IF;
    
    SELECT (:NEW.rental_end_date_hour - :OLD.rental_begin_date_hour) * 24 * 60 INTO  :NEW.rental_duration FROM dual;

    SELECT (:NEW.rental_end_date_hour - :OLD.rental_begin_date_hour) * 24 * 60 INTO v_duration FROM dual;
    IF v_duration > 60 THEN
        v_rental_cost := (v_duration/60) * 1.5;
    END IF;

    SELECT v_rental_cost INTO :NEW.rental_cost FROM dual;

    SELECT elevation INTO v_elevation_origin FROM point_interest WHERE id_point = :OLD.id_park_picking;
    SELECT elevation INTO v_elevation_destination FROM point_interest WHERE id_point = :NEW.id_park_delivery;
    v_elevation_difference := v_elevation_destination - v_elevation_origin;

    IF v_elevation_difference BETWEEN 25 AND 50 THEN
        v_user_points := 5;
    ELSIF v_elevation_difference > 50 THEN
        v_user_points :=15;
    END IF;
     
    :NEW.earned_points := v_user_points;
    
    UPDATE user_app
        SET 
            points = points + v_user_points     
        WHERE id_user = :OLD.id_user; 
    
END;
/