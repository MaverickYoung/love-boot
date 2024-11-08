INSERT INTO poop_log (user_id, log_time, poop_type, version, is_deleted, create_time, update_time)
VALUES (101, '2024-11-08 08:15:34', 1, 0, false, '2024-11-08 08:20:00', '2024-11-08 08:20:00'),
       (101, '2024-10-27 14:22:10', 2, 0, false, '2024-10-27 14:30:00', '2024-10-27 14:30:00'),
       (101, '2024-07-04 22:58:55', 1, 0, false, '2024-07-04 23:05:00', '2024-07-04 23:05:00'),
       (102, '2024-05-20 11:13:09', 2, 0, false, '2024-05-20 11:20:00', '2024-05-20 11:20:00'),
       (102, '2024-02-28 21:37:15', 2, 0, false, '2024-02-28 21:45:00', '2024-02-28 21:45:00');

SELECT to_char(log_time, 'YYYY-MM') AS month,
       user_id,
       COUNT(*)                     AS poop_count
FROM poop_log
WHERE is_deleted = false
  AND log_time >= to_date('2024-01', 'YYYY-MM')
  AND log_time <= to_date('2024-12', 'YYYY-MM')
GROUP BY month, user_id
ORDER BY month, user_id