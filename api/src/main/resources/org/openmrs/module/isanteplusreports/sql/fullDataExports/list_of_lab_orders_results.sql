SELECT test_name as 'Test', visit_date as 'Date', CASE WHEN test_done = 1 THEN 'COMPLETE' ELSE 'INCOMPLETE' END as 'status'
FROM isanteplus.patient_laboratory
WHERE order_destination IS NOT NULL
AND IF ('ALL' = :result, 1, test_done = IF ('COMPLETE' = :result, 1 ,0))
AND visit_date BETWEEN :startDate AND :endDate;