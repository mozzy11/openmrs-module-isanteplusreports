select distinct parv.visit_date as 'Date Visite',p.national_id,p.given_name as Nom,
p.family_name as Prénom, p.birthdate as 'Date de naissance',pdisp.next_dispensation_date as 'Date de dispensation'
FROM isanteplus.patient p, isanteplus.patient_dispensing pdisp, isanteplus.patient_on_arv parv
WHERE p.patient_id=pdisp.patient_id
AND pdisp.visit_id=parv.visit_id
AND DATEDIFF(pdisp.next_dispensation_date,now()) between 1 and 30;