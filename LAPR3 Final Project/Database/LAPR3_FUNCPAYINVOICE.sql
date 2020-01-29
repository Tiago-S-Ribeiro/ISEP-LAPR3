create or replace FUNCTION funcPayInvoice(p_id_invoice INTEGER) RETURN INTEGER
IS

    v_total_cost    receipt.total_cost%type;
    v_id_user       INTEGER;

BEGIN

    UPDATE invoice 
        SET state_invoice = 1
        WHERE id_invoice = p_id_invoice;

    SELECT id_user, total_cost INTO v_id_user, v_total_cost FROM invoice WHERE id_invoice = p_id_invoice;
    INSERT INTO receipt VALUES (DEFAULT, p_id_invoice, v_id_user, v_total_cost, DEFAULT);
END;
/