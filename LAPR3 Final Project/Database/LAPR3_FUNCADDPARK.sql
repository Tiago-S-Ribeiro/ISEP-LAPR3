create or replace FUNCTION funcAddPark(p_ref_park park.ref_park%type,
    p_park_description point_interest.poi_description%type, p_latitude point_interest.latitude%type, p_longitude point_interest.longitude%type, 
    p_park_state park.park_state%type, p_park_input_voltage park.park_input_voltage%type, 
    p_park_input_current park.park_input_current%type, p_elevation point_interest.elevation%type,  p_capacity_bike INTEGER, p_capacity_scooter INTEGER) 
    RETURN INTEGER IS 
    
    v_id_park   INTEGER;
    v_id_type_bicycle  INTEGER;
    v_id_type_scooter   INTEGER;
BEGIN
    SELECT id_vehicle_type INTO v_id_type_bicycle FROM vehicle_type WHERE REGEXP_LIKE(vehicle_type_description ,'bicycle','i');    
    SELECT id_vehicle_type INTO v_id_type_scooter FROM vehicle_type WHERE REGEXP_LIKE(vehicle_type_description ,'scooter','i'); 
    
    INSERT INTO point_interest VALUES(DEFAULT, p_park_description, p_latitude, p_longitude, p_elevation);
    
    SELECT id_point INTO v_id_park FROM point_interest WHERE latitude = p_latitude AND longitude = p_longitude AND elevation = p_elevation;
    
    INSERT INTO park VALUES
        (v_id_park, p_ref_park, p_park_state, p_park_input_voltage, p_park_input_current);
        
    INSERT INTO park_capacity VALUES (v_id_type_bicycle, v_id_park, p_capacity_bike, p_capacity_bike); 
    INSERT INTO park_capacity VALUES (v_id_type_scooter, v_id_park, p_capacity_scooter, p_capacity_scooter); 
    
  
    RETURN v_id_park;
END;
/