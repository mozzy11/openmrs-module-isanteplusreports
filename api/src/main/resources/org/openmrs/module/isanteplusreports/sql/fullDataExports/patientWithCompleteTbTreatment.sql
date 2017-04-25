select distinct p.st_id,p.national_id as 'numéro identité national', p.given_name as prénom, p.family_name as nom,
p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Age,
stat.name_fr as 'Status de patient', MAX(parv.start_date) as 'Dernière date' 
from isanteplus.patient p,isanteplus.patient_tb_diagnosis ptbd, isanteplus.patient_status_ARV parv,
isanteplus.ARV_status_loockup stat
WHERE p.patient_id=ptbd.patient_id
AND ptbd.patient_id=parv.patient_id
AND parv.id_status=stat.id
AND ptbd.status_tb_treatment=2
AND ptbd.visit_date between :startDate AND :endDate
AND parv.start_date between :startDate AND :endDate;