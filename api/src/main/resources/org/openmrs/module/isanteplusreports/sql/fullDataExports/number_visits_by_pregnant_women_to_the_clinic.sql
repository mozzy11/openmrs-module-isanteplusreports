/*Nombre de visites de femmes enceintes reçues en clinique / Number of visits by pregnant women to the clinic*/
SELECT DISTINCT pp.patient_id 
FROM isanteplus.patient p, isanteplus.patient_pregnancy pp
WHERE p.patient_id=pp.patient_id
AND p.gender = 'F'
AND pp.voided <> 1
AND pp.start_date BETWEEN :startDate AND :endDate;