SELECT
	COUNT(
		DISTINCT CASE WHEN (
			p.gender = 'F' AND
			p.patient_id IN ( -- HAART
				SELECT pd2.patient_id FROM isanteplus.patient_dispensing pd2
				GROUP BY pd2.patient_id
				HAVING count(*) >= 3
			)
		) THEN p.patient_id else null END
	) AS 'femaleNumerator',
    COUNT(
		DISTINCT CASE WHEN (
			p.gender = 'M'
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
WHERE
  p.vih_status = 1 -- HIV+
  AND p.patient_id IN ( -- Pregnant
      SELECT ppreg.patient_id
      FROM isanteplus.patient_pregnancy ppreg
      WHERE ppreg.start_date BETWEEN '2016-10-10' AND '2017-09-13'
  )
  AND p.patient_id IN ( -- Patient on ART
      SELECT poa.patient_id
      FROM isanteplus.patient_on_arv poa
  )
  AND p.patient_id NOT IN ( -- Exclude deceased (159), discontinuations (1667), transfer (159492)
		  SELECT discon.patient_id
      FROM isanteplus.discontinuation_reason discon
      WHERE discon.reason IN (159,1667,159492)
  );
