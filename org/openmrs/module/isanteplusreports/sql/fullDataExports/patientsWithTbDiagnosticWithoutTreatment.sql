/*Patients avec des diagnostics de TB, mais sans traitement
 Patients with TB diagnosis, but no treatment */
select distinct p.st_id,p.national_id as 'numéro identité national', p.given_name as prénom,
p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Age,
stat.name_fr as 'Status de patient', p.last_visit_date as 'Dernière date'
from isanteplus.patient p
INNER JOIN isanteplus.patient_tb_diagnosis pdiag
ON pdiag.patient_id=p.patient_id
LEFT OUTER JOIN isanteplus.ARV_status_loockup stat
ON stat.id = p.arv_status
WHERE pdiag.visit_date between :startDate AND :endDate
AND pdiag.patient_id NOT IN (SELECT patdisp.patient_id FROM isanteplus.patient_dispensing patdisp
WHERE patdisp.drug_id IN (75948,78280,82900,767,84360) AND patdisp.dispensation_date between :startDate AND :endDate);