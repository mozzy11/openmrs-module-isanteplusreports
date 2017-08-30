SELECT
    COUNT(DISTINCT CASE WHEN (p.gender = 'M') THEN p.patient_id else null END) AS 'maleNumerator',
    COUNT(DISTINCT CASE WHEN (p.gender = 'F') THEN p.patient_id else null END) AS 'femaleNumerator',
    COUNT(DISTINCT CASE WHEN (p.gender = 'M') THEN p.patient_id else null END) AS 'maleDenominator',
    COUNT(DISTINCT CASE WHEN (p.gender = 'F') THEN p.patient_id else null END) AS 'femaleDenominator'
FROM isanteplus.patient p,isanteplus.patient_visit pv
WHERE p.patient_id=pv.patient_id;