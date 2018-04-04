select 
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now()))<15 THEN p.patient_id else null END) AS '0-14',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 15 AND 20 THEN p.patient_id else null END) AS '15-20',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 21 AND 30 THEN p.patient_id else null END) AS '21-30',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 31 AND 40 THEN p.patient_id else null END) AS '31-40',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 41 AND 50 THEN p.patient_id else null END) AS '41-50',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 51 AND 60 THEN p.patient_id else null END) AS '51-60',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 61 AND 70 THEN p.patient_id else null END) AS '61-70',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 71 AND 80 THEN p.patient_id else null END) AS '71-80',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 81 AND 90 THEN p.patient_id else null END) AS '81-90',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 91 AND 100 THEN p.patient_id else null END) AS '91-100',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 101 AND 110 THEN p.patient_id else null END) AS '101-110',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 111 AND 120 THEN p.patient_id else null END) AS '111-120',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 121 AND 130 THEN p.patient_id else null END) AS '121-130',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) > 130 THEN p.patient_id else null END) AS '>130',
                COUNT(DISTINCT p.patient_id) as 'Total'
        FROM isanteplus.patient p,isanteplus.patient_visit pv
        WHERE p.patient_id=pv.patient_id
        AND pv.visit_date BETWEEN :startDate AND :endDate
        AND pv.visit_date=(SELECT MIN(pv.visit_date) FROM isanteplus.patient pa,isanteplus.patient_visit pvi
        WHERE pa.patient_id=pvi.patient_id GROUP BY pv.visit_date)
        AND p.patient_id NOT IN (SELECT pv.patient_id FROM isanteplus.patient_visit pv WHERE pv.visit_date < :startDate);