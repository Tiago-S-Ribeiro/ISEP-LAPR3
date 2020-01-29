create or replace FUNCTION funcAddBicycle(p_id_park INTEGER, p_id_vehicle_type INTEGER, p_vehicle_description vehicle.vehicle_description%type,
    p_vehicle_state INTEGER, p_weight vehicle.weight%type, p_aerodynamic_coefficient vehicle.aerodynamic_coefficient%type, 
    p_frontal_area vehicle.frontal_area%type, p_wheel_size INTEGER) RETURN INTEGER
IS
    v_id_vehicle    INTEGER;
BEGIN
    
    INSERT INTO vehicle VALUES(DEFAULT, p_id_park, p_id_vehicle_type, p_vehicle_description, p_vehicle_state, p_weight, p_aerodynamic_coefficient, p_frontal_area);   
    
    SELECT id_vehicle INTO v_id_vehicle FROM vehicle WHERE vehicle_description LIKE p_vehicle_description;
    
    INSERT INTO bicycle VALUES(v_id_vehicle, p_wheel_size);

    RETURN v_id_vehicle;
END;
/