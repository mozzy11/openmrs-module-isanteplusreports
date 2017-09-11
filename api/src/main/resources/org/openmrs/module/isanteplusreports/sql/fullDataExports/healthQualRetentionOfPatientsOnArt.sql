-- startDate -> SUBDATE(startDate, INTERVAL period MONTH);
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
FROM isanteplus.patient_status_ARV psa
	INNER JOIN isanteplus.patient p ON psa.patient_id = p.patient_id
    LEFT JOIN isanteplus.patient_dispensing pd ON psa.patient_id = pd.patient_id
WHERE psa.start_date BETWEEN SUBDATE(:currentDate, INTERVAL :period MONTH) AND :currentDate
	AND pd.drug_id IN (SELECT drug_id FROM isanteplus.arv_drugs)            -- ART treatment
	AND (psa.dis_reason IS NULL OR psa.dis_reason NOT IN ('159','159492'))  -- not deceased and transfered
