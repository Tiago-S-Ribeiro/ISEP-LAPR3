create or replace FUNCTION funcAddInvoice(p_id_user invoice.id_user%type, month_invoice INTEGER)RETURN INTEGER 
IS 

    v_total_cost    invoice.total_cost%type;
    v_limit_points  CONSTANT    INTEGER := 10;
    v_cost_points   invoice.total_cost%type;
    v_user_points   INTEGER;
    v_id_invoice    INTEGER;
    v_issue_date    DATE;

BEGIN
    BEGIN
        SELECT id_invoice INTO v_id_invoice FROM invoice WHERE issue_date = TRUNC(SYSDATE);
        RETURN v_id_invoice;
        EXCEPTION 
            WHEN NO_DATA_FOUND THEN
                v_id_invoice := 0;
    END;
    

    SELECT TO_DATE(EXTRACT(DAY FROM SYSDATE) || '/' || month_invoice || '/' || EXTRACT(YEAR FROM SYSDATE), 'dd/mm/yyyy')
        INTO v_issue_date FROM dual;
        
    SELECT SUM(il.rental_cost) INTO v_total_cost
    FROM invoice_line il INNER JOIN rental r ON il.id_rental = r.id_rental
    WHERE (TRUNC(il.rental_end_date_hour) BETWEEN (SELECT add_months(v_issue_date,-1) from dual) AND v_issue_date ) AND r.id_user = p_id_user
    GROUP BY r.id_user;

    SELECT points INTO v_user_points FROM user_app WHERE id_user = p_id_user;
    
    v_cost_points := TRUNC(v_total_cost/v_limit_points, 0);
    
    IF(v_cost_points > 0) THEN
        WHILE(TRUNC(v_user_points/v_limit_points, 0) < v_cost_points) 
            LOOP
                v_cost_points := v_cost_points - 1;
            END LOOP;
    END IF;
    
    v_total_cost := v_total_cost - v_cost_points;

    INSERT INTO invoice VALUES (DEFAULT, p_id_user, v_total_cost, DEFAULT, 0);

    SELECT id_invoice, issue_date INTO v_id_invoice, v_issue_date
    FROM invoice WHERE TO_CHAR(issue_date, 'DD-MM-YYYY') LIKE TO_CHAR(SYSDATE, 'DD-MM-YYYY');

    UPDATE invoice_line
        SET
            id_invoice = v_id_invoice
            WHERE (rental_end_date_hour BETWEEN (SELECT add_months(SYSDATE,-1)  from dual) AND SYSDATE) 
                AND id_rental IN (SELECT id_rental FROM rental WHERE id_user = p_id_user);

    UPDATE user_app 
        SET points = points - (v_cost_points * v_limit_points)
            WHERE id_user = p_id_user;

    RETURN v_id_invoice;

END;
/