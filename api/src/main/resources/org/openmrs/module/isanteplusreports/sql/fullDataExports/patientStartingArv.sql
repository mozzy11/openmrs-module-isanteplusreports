select distinct MIN(DATE(pdis.visit_date)) as 'Date Visite',p.st_id as 'NO. de patient attribué par le site',
p.national_id as 'Numéro d\'identité national',p.given_name as Nom,
p.family_name as Prénom, p.birthdate as 'Date de naissance'
FROM isanteplus.patient p,isanteplus.patient_dispensing pdis
WHERE p.patient_id=pdis.patient_id
AND pdis.drug_id IN (select arvd.drug_id from isanteplus.arv_drugs arvd)
AND pdis.visit_date=(SELECT MIN(pdp.visit_date) FROM isanteplus.patient_dispensing pdp WHERE pdp.patient_id=p.patient_id)
AND p.patient_id NOT IN (SELECT ei.patient_id FROM isanteplus.exposed_infants ei)
AND pdis.visit_date between :startDate AND :endDate
GROUP BY p.patient_id;