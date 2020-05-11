SELECT          
                p.gender,
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate))<1 THEN p.patient_id else null END) AS '0-1',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 1 AND 4 THEN p.patient_id else null END) AS '1-4',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 5 AND 9 THEN p.patient_id else null END) AS '5-9',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 10 AND 14 THEN p.patient_id else null END) AS '10-14',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 15 AND 19 THEN p.patient_id else null END) AS '15-19',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 20 AND 24 THEN p.patient_id else null END) AS '20-24',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 25 AND 29 THEN p.patient_id else null END) AS '25-29',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 30 AND 34 THEN p.patient_id else null END) AS '30-34',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 35 AND 39 THEN p.patient_id else null END) AS '35-39',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 40 AND 44 THEN p.patient_id else null END) AS '40-44',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 45 AND 49 THEN p.patient_id else null END) AS '45-49',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) > 50 THEN p.patient_id else null END) AS '>50',
        		COUNT(DISTINCT CASE WHEN (p.birthdate = '' OR p.birthdate is null) THEN p.patient_id else null END) AS 'unknown',
                COUNT(DISTINCT p.patient_id) as 'total number of patients'
        FROM isanteplus.patient p
        WHERE p.transferred_in = 0
        AND (p.birthdate <>'' AND p.birthdate is not null)
        AND p.date_started_arv IS NOT NULL
		AND p.date_started_arv BETWEEN :startDate AND :endDate 
		AND p.voided = 0
		GROUP BY p.gender 
		WITH ROLLUP ;

