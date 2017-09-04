SELECT
    COUNT(DISTINCT CASE WHEN (p.gender = 'F' AND (phv.encounter_date BETWEEN :startDate AND date_add(:startDate, INTERVAL :period MONTH)
							OR (pp.visit_date BETWEEN :startDate AND DATE_ADD(:startDate, INTERVAL :period MONTH) AND pp.rx_or_prophy = 138405))
							AND (discon.reason != 1667 OR discon.reason IS NULL )) THEN p.patient_id else null END) AS 'femaleNumerator',
    COUNT(DISTINCT CASE WHEN (p.gender = 'M' AND (phv.encounter_date BETWEEN :startDate AND date_add(:startDate, INTERVAL :period MONTH)
							OR (pp.visit_date BETWEEN :startDate AND DATE_ADD(:startDate, INTERVAL :period MONTH) AND pp.rx_or_prophy = 138405))
							AND (discon.reason != 1667 OR discon.reason IS NULL )) THEN p.patient_id else null END) AS 'maleNumerator',
    COUNT(DISTINCT CASE WHEN (p.gender = 'F' AND (discon.reason != 159492 OR discon.reason IS NULL)) THEN p.patient_id else null END) AS 'femaleDenominator',
    COUNT(DISTINCT CASE WHEN (p.gender = 'M' AND (discon.reason != 159492 OR discon.reason IS NULL)) THEN p.patient_id else null END) AS 'maleDenominator'
FROM
	isanteplus.patient_on_arv poa
	INNER JOIN isanteplus.patient p
    ON p.patient_id = poa.patient_id
    INNER JOIN isanteplus.pediatric_hiv_visit phv
    ON phv.patient_id = poa.patient_id
    LEFT JOIN isanteplus.discontinuation_reason discon
    ON poa.patient_id = discon.patient_id
    LEFT JOIN isanteplus.patient_prescription pp
    ON poa.patient_id = pp.patient_id
    WHERE (discon.reason != 159 OR discon.reason IS NULL)
	AND poa.patient_id NOT IN (SELECT plab.patient_id FROM isanteplus.patient_laboratory plab WHERE plab.test_done = 1 AND plab.test_id = 844 AND plab.test_result = 1302);
