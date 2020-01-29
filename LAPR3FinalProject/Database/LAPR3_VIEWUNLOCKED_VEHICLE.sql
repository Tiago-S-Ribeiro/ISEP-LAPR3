CREATE VIEW unlocked_vehicle AS(
SELECT r.id_user, r.id_vehicle
FROM rental r 
WHERE r.rental_begin_date_hour IS NOT NULL AND r.rental_end_date_hour IS NULL);