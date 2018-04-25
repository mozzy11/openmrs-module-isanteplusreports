select DISTINCT pat.st_id as 'NO. de patient attribué par le site', pat.national_id as 'Numéro d\'identité national',
pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,
TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Age, pat.last_address as Adresse, pat.telephone as Téléphone,
pat.mother_name as Contact FROM isanteplus.patient pat, isanteplus.exposed_infants exp_inf
	WHERE pat.patient_id = exp_inf.patient_id
    AND exp_inf.condition_exposee = 3
    AND (TIMESTAMPDIFF(DAY, pat.birthdate,DATE(now())) BETWEEN 28 AND 30)
    AND pat.patient_id NOT IN (select ppcr.patient_id FROM isanteplus.patient_pcr ppcr)
    
 UNION
 
  select DISTINCT pat.st_id as 'NO. de patient attribué par le site', pat.national_id as 'Numéro d\'identité national',
pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,
TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Age, pat.last_address as Adresse, pat.telephone as Téléphone,
pat.mother_name as Contact FROM isanteplus.patient pat,isanteplus.serological_tests stests
	WHERE pat.patient_id = stests.patient_id
	AND stests.test_id = 162087
	AND stests.answer_concept_id IN(163722,1042)
    AND stests.test_result = 703
    AND (TIMESTAMPDIFF(MONTH, pat.birthdate,DATE(now())) BETWEEN 12 AND 18)
    AND pat.patient_id NOT IN (select ppcr.patient_id FROM isanteplus.patient_pcr ppcr);