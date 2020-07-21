	SELECT pat.patient_id
   FROM isanteplus.patient_on_art pat , isanteplus.patient_laboratory pl
   WHERE pat.patient_id = pl.patient_id 
   AND pat.tb_bacteriological_test_status IS NOT NULL
	AND pl.date_test_done BETWEEN :startDate AND :endDate ; 