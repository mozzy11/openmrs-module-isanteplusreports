SELECT COUNT(DISTINCT ppr.patient_id) as Total 
FROM isanteplus.patient_pregnancy ppr, isanteplus.patient_laboratory plab
WHERE ppr.patient_id=plab.patient_id
AND (plab.test_id=1042 OR plab.test_id=1040)
AND plab.test_result IN (664,703,1138,1304)
AND plab.voided <> 1
AND ppr.voided <> 1
AND ((ppr.start_date BETWEEN :startDate AND :endDate) OR (ppr.end_date BETWEEN :startDate AND :endDate))
AND plab.date_test_done BETWEEN :startDate AND :endDate;