select distinct parv.visit_date as 'Date Visite',p.st_id as 'NO. de patient attribué par le site',
p.national_id as 'Numéro d\'identité national',p.given_name as Nom,
p.family_name as Prénom, p.birthdate as 'Date de naissance',pdisp.next_dispensation_date as 'Date de dispensation'
FROM isanteplus.patient p, isanteplus.patient_dispensing pdisp, isanteplus.patient_on_arv parv
WHERE p.patient_id=pdisp.patient_id
AND p.patient_id NOT IN(SELECT dreason.patient_id FROM isanteplus.discontinuation_reason dreason WHERE dreason.reason IN(159,1667,159492))
AND pdisp.visit_id=parv.visit_id
AND DATEDIFF(pdisp.next_dispensation_date,now()) between 1 and 30;