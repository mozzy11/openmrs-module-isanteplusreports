/*Nombre d'enfants exposés confirmés VIH (+) */
SELECT COUNT(DISTINCT phv.patient_id) as Total 
FROM isanteplus.pediatric_hiv_visit phv
LEFT OUTER JOIN  isanteplus.patient_laboratory plab
ON plab.patient_id=phv.patient_id
LEFT OUTER JOIN isanteplus.virological_tests vt
ON vt.patient_id=phv.patient_id
WHERE (
		(vt.concept_group=1361 AND vt.test_id=162087 
		AND vt.answer_concept_id=1030 AND vt.test_result=703
		AND vt.encounter_date BETWEEN :startDate AND :endDate)
		OR(plab.test_id=844 AND plab.test_result=1301 AND plab.date_test_done BETWEEN :startDate AND :endDate)
	OR 
		((plab.test_id=1042 OR plab.test_id=1040)
		AND plab.test_result=703 AND plab.date_test_done BETWEEN :startDate AND :endDate))
AND phv.actual_vih_status=1405
AND phv.encounter_date BETWEEN :startDate AND :endDate;