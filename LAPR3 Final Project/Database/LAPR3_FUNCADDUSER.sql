create or replace FUNCTION funcAddUser(p_email user_app.email%type, p_username user_app.username%type, p_credit_card user_app.credit_card%type,
    p_cycling_average_speed user_app.cycling_average_speed%type, p_height user_app.height%type, p_weight user_app.weight%type, 
    p_gender user_app.gender%type, p_pwd user_app.pwd%type) RETURN INTEGER
IS
    v_id    INTEGER;
    v_pwd_encrypted VARCHAR(100);
BEGIN
    SELECT standard_hash(p_pwd, 'MD5') INTO v_pwd_encrypted FROM dual;

    INSERT INTO user_app VALUES
        (DEFAULT, p_email, p_username, p_credit_card, p_cycling_average_speed, p_height, p_weight, p_gender, v_pwd_encrypted, DEFAULT);

    SELECT id_user INTO v_id FROM user_app WHERE email LIKE p_email;
    
    RETURN v_id; 

END;
/