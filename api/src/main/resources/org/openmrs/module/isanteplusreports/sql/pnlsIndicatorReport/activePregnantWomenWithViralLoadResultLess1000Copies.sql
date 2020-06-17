SELECT ps.patient_id
FROM isanteplus.patient_status_arv ps ,isanteplus.patient_laboratory pl ,isanteplus.patient_pregnancy pp
     WHERE ps.patient_id = pl.patient_id
     AND ps.patient_id IN (SELECT pp.patient_id FROM isanteplus.patient_pregnancy)
	 AND (ps.id_status =6 OR ps.id_status = 8) 
     AND ps.date_started_status BETWEEN :startDate AND :endDate 
	 AND CAST(pl.test_result AS UNSIGNED) < 1000
	 AND pl.test_done =1 
	 AND TIMESTAMPDIFF(MONTH, pl.date_test_done ,:endDate) > 12
	 AND pp.voided =0 ;