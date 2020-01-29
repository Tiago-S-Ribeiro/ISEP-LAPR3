create or replace PROCEDURE procAddPath(p_id_point_from PATH_PLACES.id_point_from%type, 
                                        p_id_point_to PATH_PLACES.id_point_to%type,
                                        p_kinetic_coefficient PATH_PLACES.kinetic_coefficient%type,
                                        p_wind_direction PATH_PLACES.wind_direction%type,
                                        p_wind_speed PATH_PLACES.wind_speed%type) IS

BEGIN
    INSERT INTO path_places(id_point_from, id_point_to, kinetic_coefficient, wind_direction, wind_speed)
                    VALUES(p_id_point_from, p_id_point_to, p_kinetic_coefficient, p_wind_direction, p_wind_speed);
END;
/