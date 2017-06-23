select distinct p.identifier as Identifiant, p.st_id as 'No. de patient attribué par le site', p.national_id as 'No. d\'identité nationale',
p.family_name as Nom,p.given_name as Prénom, MAX(DATE(en.encounter_datetime)) as 'Date de discontinuation', 
MAX(DATE(enc.encounter_datetime)) as 'Dernière date', entype.name
from isanteplus.patient p,
 openmrs.encounter enc, openmrs.encounter_type entype, openmrs.encounter en
WHERE p.patient_id=enc.patient_id
AND enc.encounter_type=entype.encounter_type_id
AND enc.patient_id=en.patient_id
AND p.vih_status=1
AND en.encounter_type=(select enct.encounter_type_id FROM openmrs.encounter_type enct 
WHERE uuid='9d0113c6-f23a-4461-8428-7e9a7344f2ba')
AND DATE(enc.encounter_datetime) > DATE(en.encounter_datetime)
GROUP BY p.patient_id;