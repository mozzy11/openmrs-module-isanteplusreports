SELECT
	COUNT(
		DISTINCT CASE WHEN (
			p.gender = 'F' AND
			p.patient_id IN (
				SELECT pl.patient_id FROM isanteplus.patient_laboratory pl
				WHERE pl.test_id IN (1042, 163722)
				AND test_done IN (1)
				AND pl.date_test_done BETWEEN :startDate AND :endDate
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
  p.patient_id IN ( -- Pregnant
      SELECT ppreg.patient_id
      FROM isanteplus.patient_pregnancy ppreg
      WHERE ppreg.end_date IS NULL OR (ppreg.end_date BETWEEN :startDate AND :endDate)
  ) AND TIMESTAMPDIFF(YEAR, p.birthdate, :endDate) > 14; -- adult