SELECT COUNT(DISTINCT ppr.patient_id) as Total 
	FROM isanteplus.patient_pregnancy ppr
	LEFT OUTER JOIN  isanteplus.patient_laboratory plab
	ON ppr.patient_id=plab.patient_id
    LEFT OUTER JOIN isanteplus.patient_diagnosis pdiag
    ON ppr.patient_id=pdiag.patient_id
	WHERE ((plab.test_id=163626
	AND plab.test_done=1 AND plab.test_result=703
    AND plab.visit_date BETWEEN :startDate AND :endDate
    )
    OR (pdiag.concept_id=1284 AND pdiag.answer_concept_id=112493
    AND pdiag.encounter_date BETWEEN :startDate AND :endDate
    ))
	AND ppr.start_date BETWEEN :startDate AND :endDate