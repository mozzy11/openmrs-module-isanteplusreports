SELECT 
	p.patient_id
FROM isanteplus.patient p
    LEFT JOIN isanteplus.patient_dispensing pd ON p.patient_id = pd.patient_id
    INNER JOIN isanteplus.patient_status_arv arv ON p.patient_id = arv.patient_id
WHERE pd.drug_id IN (SELECT drug_id FROM isanteplus.arv_drugs)  -- ART treatment
    AND p.vih_status = '1' -- HIV+ patient
    AND p.patient_id NOT IN (   -- Exclude discontinued (1667), transfer (159492)
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (1667, 159492)
    ) AND TIMESTAMPDIFF(YEAR, p.birthdate, :currentDate) > 14; -- adult
