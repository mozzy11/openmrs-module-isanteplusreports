SELECT test_name, visit_date, CASE WHEN test_done = 1 THEN 'COMPLETE' ELSE 'INCOMPLETE' END as 'status'
FROM isanteplus.patient_laboratory
WHERE order_destination IS NOT NULL
AND visit_date BETWEEN :startDate AND :endDate;