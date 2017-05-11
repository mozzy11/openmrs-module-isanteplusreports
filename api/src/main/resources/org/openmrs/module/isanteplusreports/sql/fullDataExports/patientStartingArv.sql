select distinct MIN(parv.visit_date) as 'Date Visite',p.national_id,p.given_name as Nom,
p.family_name as Prénom, p.birthdate as 'Date de naissance'
FROM isanteplus.patient p,isanteplus.patient_on_arv parv
WHERE p.patient_id=parv.patient_id
AND parv.visit_date between :startDate AND :endDate
GROUP BY p.patient_id;