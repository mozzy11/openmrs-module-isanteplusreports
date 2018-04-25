select cn.name as 'Nom du test', count(lab.test_id) as Quantité 
FROM openmrs.concept_name cn, isanteplus.patient_laboratory lab
WHERE cn.concept_id=lab.test_id AND locale='fr'
AND lab.test_done=1
GROUP BY lab.test_id;