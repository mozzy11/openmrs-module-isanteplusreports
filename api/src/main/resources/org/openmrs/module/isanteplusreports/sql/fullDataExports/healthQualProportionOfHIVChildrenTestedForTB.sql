SELECT
  COUNT(
    DISTINCT CASE WHEN (
      p.gender = 'F'
      AND p.patient_id
      AND (pp.visit_date BETWEEN :startDate AND :endDate)
      AND ptd.status_tb_treatment = 1
		) THEN p.patient_id
		ELSE null
		END
	) AS 'femaleNumerator',

  COUNT(
    DISTINCT CASE WHEN (
      p.gender = 'M'
      AND p.patient_id
      AND (pp.visit_date BETWEEN :startDate AND :endDate)
      AND ptd.status_tb_treatment = 1
		) THEN p.patient_id
		ELSE null
		END
	) AS 'maleNumerator',

	COUNT(
	  DISTINCT CASE WHEN (
	    p.gender = 'F'
	  ) THEN p.patient_id
	  ELSE null
	  END
	) AS 'femaleDenominator',

  COUNT(
    DISTINCT CASE WHEN (
			p.gender = 'M'
		) THEN p.patient_id
		ELSE null
		END
	) AS 'maleDenominator'
FROM isanteplus.patient p
LEFT JOIN isanteplus.patient_prescription pp ON p.patient_id = pp.patient_id
LEFT JOIN isanteplus.patient_tb_diagnosis ptd ON p.patient_id = ptd.patient_id
WHERE
    p.patient_id IN (
      SELECT phv.patient_id
		  FROM isanteplus.pediatric_hiv_visit phv
      WHERE DATE(phv.encounter_date) BETWEEN :startDate AND :endDate
    )
	  AND p.patient_id NOT IN (
	    SELECT discon.patient_id
	    FROM isanteplus.discontinuation_reason discon
	    WHERE discon.reason IN (159, 1667, 159492)
	  )
	  AND p.patient_id NOT IN (
	    SELECT plab.patient_id
	    FROM isanteplus.patient_laboratory plab
	    WHERE
	      plab.test_done = 1 AND
	      plab.test_id = 844 AND
	      plab.test_result = 1302
	  )
	  AND TIMESTAMPDIFF(MONTH, p.birthdate, :endDate) >= 6;