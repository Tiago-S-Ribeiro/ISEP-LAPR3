create or replace PROCEDURE procRemoveVehicle(p_id_vehicle vehicle.id_vehicle%type) IS

BEGIN
    Update Vehicle  Set vehicle_state = 0 where id_vehicle = p_id_vehicle;
END;
/