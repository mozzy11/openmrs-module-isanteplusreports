SELECT
    COUNT(DISTINCT CASE WHEN (
            p.gender = 'F'
            AND p.patient_id IN (SELECT patient_id FROM isanteplus.patient_on_arv)
            AND p.patient_id IN (SELECT pv.patient_id FROM isanteplus.patient_visit pv
				WHERE pv.adherence_evaluation IN (90, 100))
        ) THEN p.patient_id ELSE null END
    ) AS 'femaleNumerator',
    COUNT(DISTINCT CASE WHEN (
            p.gender = 'M'
            AND p.patient_id IN (SELECT patient_id FROM isanteplus.patient_on_arv)
            AND p.patient_id IN (SELECT pv.patient_id FROM isanteplus.patient_visit pv
				 WHERE pv.adherence_evaluation IN (90, 100))
        ) THEN p.patient_id ELSE null END
    ) AS 'maleNumerator',
    COUNT(DISTINCT CASE WHEN (
            p.gender = 'F'
            AND p.patient_id IN (SELECT patient_id FROM isanteplus.patient_on_arv)
            AND p.patient_id IN (SELECT pv.patient_id FROM isanteplus.patient_visit pv
				 WHERE pv.adherence_evaluation IS NOT NULL)
	) THEN p.patient_id ELSE null END
    ) AS 'femaleDenominator',
    COUNT(DISTINCT CASE WHEN (
            p.gender = 'M'
            AND p.patient_id IN (SELECT patient_id FROM isanteplus.patient_on_arv)
            AND p.patient_id IN (SELECT pv.patient_id FROM isanteplus.patient_visit pv
				 WHERE pv.adherence_evaluation IS NOT NULL)
	) THEN p.patient_id ELSE null END
	) AS 'maleDenominator'
FROM isanteplus.patient p
	INNER JOIN isanteplus.patient_visit pv
	ON p.patient_id = pv.patient_id
WHERE p.vih_status = '1'
    AND pv.visit_date BETWEEN SUBDATE(:currentDate, INTERVAL 3 MONTH) AND :currentDate
	AND p.patient_id NOT IN (
		SELECT discon.patient_id
                FROM isanteplus.discontinuation_reason discon
                WHERE discon.reason IN ('159', '1667', '159492'));
