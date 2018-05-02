select DISTINCT p.st_id as 'Code ST',
p.given_name as Prénom, p.family_name as Nom, p.telephone as Téléphone, pl.date_test_done AS 'Date du test',
pl.test_result AS Résultat
FROM isanteplus.patient p, isanteplus.patient_laboratory pl, 
(SELECT plab.patient_id, plab.date_test_done AS date_test FROM isanteplus.patient_laboratory plab
  WHERE plab.test_id = 856) B
WHERE p.patient_id = pl.patient_id
AND pl.patient_id = B.patient_id
AND pl.date_test_done = B.date_test
AND pl.test_id = 856 
AND pl.test_result < 1000
AND (TIMESTAMPDIFF(MONTH,pl.date_test_done,DATE(now())) > 12)
AND p.patient_id NOT IN(SELECT dreason.patient_id FROM isanteplus.discontinuation_reason dreason WHERE dreason.reason IN(159,1667,159492))
AND p.patient_id NOT IN (SELECT ei.patient_id FROM isanteplus.exposed_infants ei)

UNION

select DISTINCT p.st_id as 'Code ST',
p.given_name as Prénom, p.family_name as Nom, p.telephone as Téléphone, pl.date_test_done AS 'Date du test',
pl.test_result AS Résultat
FROM isanteplus.patient p, isanteplus.patient_laboratory pl, 
(SELECT plab.patient_id, plab.date_test_done AS date_test FROM isanteplus.patient_laboratory plab
  WHERE plab.test_id = 856) B
WHERE p.patient_id = pl.patient_id
AND pl.patient_id = B.patient_id
AND pl.date_test_done = B.date_test
AND pl.test_id = 856 
AND pl.test_result >= 1000
AND (TIMESTAMPDIFF(MONTH,pl.date_test_done,DATE(now())) BETWEEN 6 AND 12)
AND p.patient_id NOT IN(SELECT dreason.patient_id FROM isanteplus.discontinuation_reason dreason WHERE dreason.reason IN(159,1667,159492))
AND p.patient_id NOT IN (SELECT ei.patient_id FROM isanteplus.exposed_infants ei);