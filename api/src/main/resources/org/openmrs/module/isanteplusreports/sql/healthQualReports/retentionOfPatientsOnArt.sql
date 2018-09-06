SELECT
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'F'
            -- drug order form completed on date X. The date of the drug order form should not surpass the reporting period by more than 90 days.
      			AND p.patient_id IN ( SELECT pnv.patient_id FROM isanteplus.patient_visit pnv
      			WHERE pnv.patient_id = p.patient_id
                  AND pnv.encounter_type IN (5, 11) -- Ord. Médicale OR Ord. médicale Pédiatrique
      			AND TIMESTAMPDIFF(DAY, :currentDate, pnv.visit_date) <= 90)
        ) THEN p.patient_id ELSE null END
    ) AS 'femaleNumerator',
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'M'
            -- drug order form completed on date X. The date of the drug order form should not surpass the reporting period by more than 90 days.
            AND p.patient_id IN ( SELECT pnv.patient_id FROM isanteplus.patient_visit pnv
            WHERE pnv.patient_id = p.patient_id
                  AND pnv.encounter_type IN (5, 11) -- Ord. Médicale OR Ord. médicale Pédiatrique
            AND TIMESTAMPDIFF(DAY, :currentDate, pnv.visit_date) <= 90)
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
    AND p.patient_id NOT IN (   -- Exclude discontinued (1667), transfer (159492)
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (1667, 159492)
    ) AND p.patient_id IN (
        SELECT patient_id
        FROM isanteplus.patient_status_arv
        WHERE start_date BETWEEN SUBDATE(:currentDate, INTERVAL :period MONTH) AND :currentDate
    ) AND TIMESTAMPDIFF(YEAR, p.birthdate, :currentDate) > 14; -- adult
