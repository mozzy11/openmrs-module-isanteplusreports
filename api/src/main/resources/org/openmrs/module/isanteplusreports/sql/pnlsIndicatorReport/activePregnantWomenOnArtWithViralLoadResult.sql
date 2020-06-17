SELECT pl.patient_id            								            
FROM isanteplus.patient_status_arv ps ,isanteplus.patient_laboratory pl ,isanteplus.patient_pregnancy pp
    WHERE ps.patient_id = pl.patient_id
    AND pl.patient_id IN (SELECT pp.patient_id FROM isanteplus.patient_pregnancy)
	 AND ps.id_status IN (6,8)
	 AND pl.test_id IN (856,1305)
    AND ps.date_started_status BETWEEN :startDate AND :endDate 
	 AND pl.test_done =1 
	 AND TIMESTAMPDIFF(MONTH, pl.date_test_done ,:endDate) >= 12;