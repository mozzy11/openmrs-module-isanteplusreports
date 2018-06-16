select distinct DATE(pdis.visit_date) as 'Date Visite',p.st_id as 'NO. de patient attribué par le site',
p.national_id as 'Numéro d\'identité national',p.given_name as Nom,
p.family_name as Prénom, p.birthdate as 'Date de naissance'
FROM isanteplus.patient p,isanteplus.patient_dispensing pdis, (SELECT pdp.patient_id,MIN(pdp.visit_date) as visit_date FROM isanteplus.patient_dispensing pdp WHERE pdp.drug_id IN (select arvd.drug_id from isanteplus.arv_drugs arvd) GROUP BY 1) B
WHERE p.patient_id=pdis.patient_id
AND pdis.drug_id IN (select arvd.drug_id from isanteplus.arv_drugs arvd)
AND B.patient_id = pdis.patient_id
AND B.visit_date = pdis.visit_date
AND p.patient_id NOT IN (SELECT ei.patient_id FROM isanteplus.exposed_infants ei)
AND pdis.visit_date between :startDate AND :endDate;