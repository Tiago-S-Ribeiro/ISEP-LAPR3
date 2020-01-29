create or replace FUNCTION funcMakeRental(p_id_vehicle INTEGER, p_id_user INTEGER) RETURN INTEGER
IS
    v_id_rental     INTEGER;  
    v_id_park_picking  INTEGER;
    
BEGIN
    SELECT id_park INTO v_id_park_picking FROM vehicle WHERE id_vehicle = p_id_vehicle;
    
    INSERT INTO rental VALUES(DEFAULT, v_id_park_picking, NULL, p_id_vehicle, p_id_user, NULL, DEFAULT, NULL, NULL, DEFAULT);

    SELECT id_rental INTO v_id_rental FROM rental WHERE rental_end_date_hour IS NULL;

    RETURN v_id_rental;
END;