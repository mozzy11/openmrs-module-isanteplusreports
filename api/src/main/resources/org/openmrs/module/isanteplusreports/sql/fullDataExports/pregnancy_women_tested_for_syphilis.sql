SELECT DISTINCT ppr.patient_id 
	FROM isanteplus.patient_pregnancy ppr
	INNER JOIN  isanteplus.patient_laboratory plab
	ON ppr.patient_id=plab.patient_id
	WHERE plab.test_id=163626
	AND plab.test_done=1
	AND plab.voided <> 1
	AND ppr.start_date BETWEEN :startDate AND :endDate