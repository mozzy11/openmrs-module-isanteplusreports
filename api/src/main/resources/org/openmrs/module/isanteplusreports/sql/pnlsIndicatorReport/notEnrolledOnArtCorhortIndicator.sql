SELECT SELECT pa.patient_id
   FROM isanteplus.patient_on_art pa
	    WHERE pa.tested_hiv_postive = 1
		 AND pa.enrolled_on_art = 0
		 AND pa.date_started_arv > :endDate ;