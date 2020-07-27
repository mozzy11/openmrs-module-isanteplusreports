SELECT p.patient_id
   FROM isanteplus.patient p, isanteplus.patient_on_art pa , isanteplus.patient_status_arv psa
       WHERE p.patient_id =pa.patient_id 
       AND p.patient_id = psa.patient_id
       AND psa.id_status IN (6 ,8) 
       AND pa.last_folowup_vist_date  IS NOT NULL
	   AND DATEDIFF (:endDate , pa.last_folowup_vist_date ) <= 365
       AND DATEDIFF(pa.last_folowup_vist_date ,IFNULL(DATE(pa.second_last_folowup_vist_date), DATE(pa.first_vist_date))) BETWEEN 180 AND 360;