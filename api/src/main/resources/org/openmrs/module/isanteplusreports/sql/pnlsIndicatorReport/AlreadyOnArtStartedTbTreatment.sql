SELECT p.patient_id
FROM isanteplus.patient p
        WHERE (p.birthdate <>'' AND p.birthdate is not null)
        AND p.date_started_arv IS NOT NULL
		AND p.date_started_arv < :startDate 
		AND p.voided = 0;