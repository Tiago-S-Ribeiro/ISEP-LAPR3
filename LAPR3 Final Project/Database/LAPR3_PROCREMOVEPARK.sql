create or replace PROCEDURE procRemovePark(p_id_park park.id_park%TYPE) IS

BEGIN
    UPDATE PARK
    SET park_state = 0
    WHERE id_park = p_id_park;
    
    UPDATE VEHICLE
    SET vehicle_state = 0
    WHERE id_park = p_id_park;
END;
/