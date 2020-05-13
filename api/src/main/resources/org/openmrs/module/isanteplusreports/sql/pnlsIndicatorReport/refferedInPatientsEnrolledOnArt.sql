SELECT p.patient_id
FROM isanteplus.patient p
        WHERE p.transferred_in = 1
        AND (p.birthdate <>'' AND p.birthdate is not null)
        AND p.date_started_arv IS NOT NULL
		AND p.date_started_arv BETWEEN :startDate AND :endDate 
		AND p.voided = 0;