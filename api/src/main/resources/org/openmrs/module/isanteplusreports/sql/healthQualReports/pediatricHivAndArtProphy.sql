SELECT
	COUNT(
		DISTINCT CASE WHEN (
			p.gender = 'F'
			AND p.patient_id
      AND pp.rx_or_prophy = 163768
      AND pp.drug_id IN (SELECT arvd.drug_id FROM isanteplus.arv_drugs arvd)
      AND (pp.visit_date BETWEEN :startDate AND :endDate)
		) THEN p.patient_id else null END
	) AS 'femaleNumerator',
    COUNT(
		DISTINCT CASE WHEN (
			p.gender = 'M'
      AND p.patient_id
      AND pp.rx_or_prophy = 163768
      AND pp.drug_id IN (SELECT arvd.drug_id FROM isanteplus.arv_drugs arvd)
      AND (pp.visit_date BETWEEN :startDate AND :endDate)
		) THEN p.patient_id else null END
	) AS 'maleNumerator',
	COUNT(
		DISTINCT CASE WHEN (
			p.gender = 'F'
		) THEN p.patient_id else null END
	) AS 'femaleDenominator',
    COUNT(
		DISTINCT CASE WHEN (
			p.gender = 'M'
		) THEN p.patient_id else null END
	) AS 'maleDenominator'
FROM
	isanteplus.patient p
	LEFT JOIN isanteplus.patient_prescription pp
	ON p.patient_id = pp.patient_id
WHERE
  p.patient_id IN (
    SELECT phv.patient_id
    FROM isanteplus.health_qual_patient_visit phv
    WHERE (
        DATE(phv.visit_date) BETWEEN :startDate AND :endDate
        OR (
          DATE(pp.visit_date) BETWEEN :startDate AND :endDate
          AND pp.rx_or_prophy = 138405
        )
      )
      AND phv.age_in_years <= 14
  )
	AND p.patient_id NOT IN (
		SELECT discon.patient_id
      FROM isanteplus.discontinuation_reason discon
      WHERE discon.reason IN (159,1667,159492)
	);