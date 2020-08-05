SELECT pat.patient_id
   FROM isanteplus.patient_on_art pat  , isanteplus.patient_laboratory pl
	WHERE pat.patient_id = pl.patient_id 
	AND pat.tb_other_test = 1
	AND pl.date_test_done BETWEEN :startDate AND : endDate ;