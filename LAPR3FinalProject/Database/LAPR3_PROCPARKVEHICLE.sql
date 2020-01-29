create or replace PROCEDURE procParkVehicle(p_id_park park.id_park%type , p_id_user rental.id_user%type) IS

    v_id_rental  INTEGER;
BEGIN

    SELECT id_rental INTO v_id_rental FROM rental 
    WHERE (rental_begin_date_hour IS NOT NULL AND rental_end_date_hour IS NULL) AND id_user = p_id_user;
    
UPDATE rental
    SET
        id_park_delivery = p_id_park,
        rental_end_date_hour = DEFAULT
    WHERE id_user = p_id_user AND rental_end_date_hour IS NULL;
    
    procAddInvoiceLine(v_id_rental);
END;
/