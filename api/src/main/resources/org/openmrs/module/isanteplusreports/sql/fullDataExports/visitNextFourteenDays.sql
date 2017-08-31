select pa.national_id as identifier, pa.identifier as patient_id,pa.given_name as Prenom,
pa.family_name as Nom, pa.gender as Sexe,pa.birthdate as 'Date de naissance',
pa.telephone as Telephone,f.name as 'fiches',pv.next_visit_date as 'Prochaine visite' 
from isanteplus.patient pa, isanteplus.patient_visit pv, openmrs.form f 
where pa.patient_id=pv.patient_id AND pv.form_id=f.form_id 
and pv.next_visit_date between date(now()) and date_add(date(now()),interval 14 day)

UNION ALL

select DISTINCT pa.national_id as identifier, pa.identifier as patient_id,pa.given_name as Prenom,
pa.family_name as Nom, pa.gender as Sexe,pa.birthdate as 'Date de naissance',
pa.telephone as Telephone,f.name as 'fiches',pd.next_dispensation_date as 'Prochaine visite' 
from isanteplus.patient pa, isanteplus.patient_dispensing pd, openmrs.encounter enc, openmrs.form f 
where pa.patient_id=pd.patient_id 
AND pd.encounter_id=enc.encounter_id
AND enc.form_id=f.form_id 
and pd.next_dispensation_date between date(now()) and date_add(date(now()),interval 14 day)