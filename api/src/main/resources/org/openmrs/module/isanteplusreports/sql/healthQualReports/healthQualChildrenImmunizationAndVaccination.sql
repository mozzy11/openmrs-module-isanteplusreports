SELECT
	COUNT(DISTINCT CASE WHEN (
            p.gender = 'F'
            AND p.patient_id IN (
              SELECT v.patient_id
              FROM isanteplus.vaccination v
              WHERE v.age_range = :age
              AND v.vaccination_done IS TRUE
            )
        ) THEN p.patient_id ELSE null END
    ) AS 'femaleNumerator',
    COUNT(DISTINCT CASE WHEN (
            p.gender = 'M'
            AND p.patient_id IN (
              SELECT v.patient_id
              FROM isanteplus.vaccination v
              WHERE v.age_range = :age
              AND v.vaccination_done IS TRUE
            )
        ) THEN p.patient_id ELSE null END
    ) AS 'maleNumerator',
    COUNT(DISTINCT CASE WHEN (
            p.gender = 'F'
	) THEN p.patient_id ELSE null END
    ) AS 'femaleDenominator',
    COUNT(DISTINCT CASE WHEN (
            p.gender = 'M'
	) THEN p.patient_id ELSE null END
	) AS 'maleDenominator'
FROM
	isanteplus.patient p
WHERE
	p.patient_id IN (
	  SELECT phv.patient_id
    FROM isanteplus.pediatric_hiv_visit phv
	  LEFT JOIN isanteplus.patient_prescription pp
	  ON phv.patient_id = pp.patient_id
    WHERE
      DATE(phv.encounter_date) BETWEEN :startDate AND :endDate
      OR (
        DATE(pp.visit_date) BETWEEN :startDate AND :endDate
        AND pp.rx_or_prophy = 138405
      )
  )
	AND p.patient_id NOT IN ( -- excludes
		SELECT discon.patient_id
    FROM isanteplus.discontinuation_reason discon
    WHERE discon.reason IN (159,1667,159492)
	)
	AND p.patient_id NOT IN ( -- negative PCR result
		SELECT plab.patient_id
		FROM isanteplus.patient_laboratory plab
		WHERE
			plab.test_done = 1
			AND plab.test_id = 844
			AND plab.test_result = 1302
	)
	AND p.patient_id IN (
	  SELECT pv.patient_id
    FROM isanteplus.patient_visit pv
	  WHERE :age =
	  			CASE
				WHEN (
					TIMESTAMPDIFF(DAY, p.birthdate, pv.visit_date) BETWEEN 0 AND 45
				) THEN 45
				WHEN TIMESTAMPDIFF(DAY, p.birthdate, pv.visit_date) BETWEEN 46 AND 75
					THEN 75
				WHEN TIMESTAMPDIFF(DAY, p.birthdate, pv.visit_date) BETWEEN 76 AND 105
					THEN 105
				WHEN TIMESTAMPDIFF(DAY, p.birthdate, pv.visit_date) BETWEEN 106 AND 270
					THEN 270
				ELSE null
			END
	);