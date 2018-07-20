/*Rapport : Éventualité de duplication d'enregistrement de patients */
select distinct pat.st_id as 'Code ST', pat.national_id as 'Code national', p.given_name as Prénom, 
p.family_name as Nom, pat.gender as Sexe, p.birthdate as 'Date de naissance', 
pat.last_address as Adresse, p.mother_name as 'Nom de la mère', pat.telephone as Téléphone
FROM (select pa.patient_id, pa.st_id, pa.national_id, pa.given_name, 
pa.family_name, pa.gender, pa.birthdate, 
pa.telephone, pa.mother_name, count(distinct pa.patient_id) as dupCount  FROM  isanteplus.patient pa
GROUP BY pa.given_name, pa.family_name, pa.birthdate, pa.mother_name
having count(distinct pa.patient_id) > 1) p, isanteplus.patient pat
WHERE p.patient_id = pat.patient_id 
AND p.given_name = pat.given_name AND p.family_name = pat.family_name
AND p.birthdate = pat.birthdate AND (p.mother_name = pat.mother_name OR 
(p.mother_name is null AND pat.mother_name is null))
order by 3,4,6;