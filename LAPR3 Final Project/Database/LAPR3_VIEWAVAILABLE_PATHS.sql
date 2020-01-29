CREATE VIEW available_paths AS(
SELECT DISTINCT pp.* FROM path_places pp INNER JOIN point_interest poi ON pp.id_point_from = poi.id_point OR pp.id_point_to = poi.id_point
MINUS
SELECT DISTINCT pp.* FROM path_places pp INNER JOIN park p ON pp.id_point_from = p.id_park OR pp.id_point_to = p.id_park WHERE p.park_state = 0);