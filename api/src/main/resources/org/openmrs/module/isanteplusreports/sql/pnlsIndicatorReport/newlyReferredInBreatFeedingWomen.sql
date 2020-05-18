SELECT p.patient_id
FROM isanteplus.patient p ,isanteplus.patient_delivery pd
        WHERE p.transferred_in = 0
        AND (p.birthdate <>'' AND p.birthdate is not null)
        AND p.date_started_arv IS NOT NULL
		  AND p.date_started_arv BETWEEN :startDate AND :endDate  
		  AND p.voided = 0
		  AND p.gender = "F"
		  AND p.patient_id 
		                 IN (
							   SELECT pd.patient_id
							   FROM isanteplus.patient_delivery 
							   WHERE pd.voided =0
							   AND pd.delivery_date < :startDate  );
							   