create or replace PROCEDURE procAddInvoiceLine(p_id_rental invoice_line.id_rental%type) IS
             
 v_id_vehicle   INTEGER;
 v_rental_duration  INTEGER;
 v_rental_cost  INTEGER;
 v_rental_end_date_hour  DATE;
 v_rental_earned_points INTEGER;

BEGIN
    SELECT  id_vehicle, rental_duration, rental_cost, rental_end_date_hour, earned_points
        INTO v_id_vehicle, v_rental_duration, v_rental_cost, v_rental_end_date_hour, v_rental_earned_points
    FROM rental WHERE id_rental = p_id_rental;   

    INSERT INTO invoice_line VALUES (p_id_rental, NULL, v_id_vehicle, v_rental_duration, v_rental_cost, v_rental_end_date_hour, v_rental_earned_points);

END;
/