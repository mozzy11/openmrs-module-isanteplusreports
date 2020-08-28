SELECT ps.patient_id 
FROM isanteplus.patient_status_arv ps ,isanteplus.patient_on_art pat , isanteplus.patient p
    WHERE ps.patient_id = pat.patient_id
    AND p.patient_id = pat.patient_id
	 AND ps.id_status IN (6,8)
	 AND p.date_started_arv  < :startDate 
    AND ps.date_started_status BETWEEN :startDate AND :endDate
    AND pat.tb_screened =1  
    AND pat.tb_status = 'NEGATIVE'
	 AND TIMESTAMPDIFF(MONTH,pat.date_tb_screened ,:endDate) <= 6 ;