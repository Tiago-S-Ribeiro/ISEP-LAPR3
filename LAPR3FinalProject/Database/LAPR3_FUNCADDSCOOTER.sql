create or replace FUNCTION funcAddScooter(p_id_park INTEGER, p_id_vehicle_type INTEGER, p_vehicle_description vehicle.vehicle_description%type,
    p_vehicle_state INTEGER, p_weight vehicle.weight%type, p_aerodynamic_coefficient vehicle.aerodynamic_coefficient%type, 
    p_frontal_area vehicle.frontal_area%type, p_id_scooter_type INTEGER, 
    p_max_batery_capacity scooter.max_batery_capacity%type, p_actual_batery_capacity scooter.actual_batery_capacity%type, 
    motor scooter.motor%type) RETURN INTEGER
IS
    v_id_vehicle    INTEGER;
BEGIN
    
    INSERT INTO vehicle VALUES(DEFAULT, p_id_park, p_id_vehicle_type, p_vehicle_description, p_vehicle_state, p_weight, p_aerodynamic_coefficient, p_frontal_area);   
    
    SELECT id_vehicle INTO v_id_vehicle FROM vehicle WHERE vehicle_description LIKE p_vehicle_description;
    
    INSERT INTO scooter VALUES(v_id_vehicle, p_id_scooter_type, p_max_batery_capacity, p_actual_batery_capacity, motor);

    RETURN v_id_vehicle;
END;
/