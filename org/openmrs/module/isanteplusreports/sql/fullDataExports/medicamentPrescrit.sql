select cn.name as 'Nom du médicament', COUNT(pp.drug_id) as Quantité FROM 
openmrs.concept_name cn,isanteplus.patient_prescription pp
WHERE cn.concept_id=pp.drug_id
AND cn.locale='fr'
GROUP BY pp.drug_id;