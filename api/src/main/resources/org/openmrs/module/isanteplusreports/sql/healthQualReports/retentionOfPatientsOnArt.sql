SELECT
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'F'
			AND pd.next_dispensation_date >= :currentDate -- still on ART
        ) THEN p.patient_id ELSE null END
    ) AS 'femaleNumerator',
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'M'
            AND pd.next_dispensation_date >= :currentDate -- still on ART
        ) THEN p.patient_id ELSE null END
    ) AS 'maleNumerator',
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'F'
        ) THEN p.patient_id ELSE null END
    ) AS 'femaleDenominator',
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'M'
        ) THEN p.patient_id ELSE null END
	) AS 'maleDenominator'
FROM isanteplus.patient p
    LEFT JOIN isanteplus.patient_dispensing pd ON p.patient_id = pd.patient_id
WHERE pd.drug_id IN (SELECT drug_id FROM isanteplus.arv_drugs)  -- ART treatment
	AND p.patient_id NOT IN (   -- Exclude deceased (159), transfer (159492)
		SELECT discon.patient_id
		FROM isanteplus.discontinuation_reason discon
		WHERE discon.reason IN (159, 159492)
	) AND p.patient_id IN (
		SELECT patient_id
        FROM isanteplus.patient_status_ARV
        WHERE start_date BETWEEN SUBDATE(:currentDate, INTERVAL :period MONTH) AND :currentDate
	) AND TIMESTAMPDIFF(YEAR, p.birthdate, :currentDate) > 14; -- adult