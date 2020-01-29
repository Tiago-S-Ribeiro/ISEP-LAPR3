create or replace FUNCTION funcValidateLogin(p_email user_app.email%type, p_pwd user_app.pwd%type) 
    RETURN INTEGER IS 
    
    v_email user_app.email%type;
    v_pwd   user_app.pwd%type;
    v_id    user_app.id_user%type;

BEGIN

    SELECT email, pwd, id_user INTO v_email, v_pwd, v_id
    FROM user_app 
    WHERE email LIKE p_email and pwd LIKE (SELECT standard_hash(p_pwd, 'MD5')FROM dual);

    RETURN v_id;

    EXCEPTION WHEN NO_DATA_FOUND  THEN
        RETURN 0;
END;
/