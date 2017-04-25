select cn.name as MedicationName, COUNT(pdis.drug_id) as Count
FROM openmrs.concept_name cn, isanteplus.patient_dispensing pdis
WHERE cn.concept_id=pdis.drug_id
AND cn.locale='fr'
GROUP BY cn.name;