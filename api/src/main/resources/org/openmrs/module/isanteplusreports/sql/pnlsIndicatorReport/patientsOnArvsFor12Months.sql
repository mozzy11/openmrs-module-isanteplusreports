SELECT p.patient_id
FROM isanteplus.patient p
		  WHERE TIMESTAMPDIFF(MONTH,p.date_started_arv ,:endDate) >= 12 ;
		  AND p.voided = 0; 