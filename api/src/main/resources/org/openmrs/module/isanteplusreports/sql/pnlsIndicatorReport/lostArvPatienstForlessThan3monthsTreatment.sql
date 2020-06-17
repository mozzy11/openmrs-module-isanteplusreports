SELECT psa.patient_id
   FROM isanteplus.patient_status_arv psa , isanteplus.patient p
	    WHERE psa.patient_id IN (SELECT psa.patient_id FROM isanteplus.patient_status_arv psa WHERE TIMESTAMPDIFF(MONTH,psa.date_started_status ,:startDate) <=3 AND psa.id_status IN (6,8) ) 
       AND psa.patient_id = p.patient_id
		 AND psa.id_status IN (1,2,3,9)
       AND psa.date_started_status  BETWEEN :startDate AND :endDate
       AND TIMESTAMPDIFF(MONTH,p.date_started_arv ,:endDate) < 3 ;