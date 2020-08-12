SELECT p.patient_id
FROM isanteplus.patient p  
      WHERE (p.date_started_arv IS NULL OR p.date_started_arv > :endDate)
		AND p.vih_status = 1
		AND p.voided =0 ;
 