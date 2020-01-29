create or replace FUNCTION funcAddPoi(p_latitude POINT_INTEREST.latitude%type, 
                                      p_longitude POINT_INTEREST.longitude%type,
                                      p_elevation POINT_INTEREST.elevation%type,
                                      p_description POINT_INTEREST.poi_description%type)
    RETURN INTEGER IS
    v_poi_id INTEGER;

BEGIN
    INSERT INTO point_interest(id_point, latitude, longitude, elevation, poi_description)
                    VALUES(DEFAULT, p_latitude, p_longitude, p_elevation, p_description);
    SELECT id_point INTO v_poi_id FROM point_interest WHERE latitude = p_latitude AND longitude = p_longitude AND elevation = p_elevation;

    RETURN v_poi_id;
END;
/