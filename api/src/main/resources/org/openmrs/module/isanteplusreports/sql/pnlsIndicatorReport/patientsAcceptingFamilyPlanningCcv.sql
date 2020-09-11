SELECT pat.patient_id   
FROM isanteplus.patient_on_art pat
WHERE pat.accepted_family_planning_method = "CCV"
AND pat.date_accepted_family_planning_method BETWEEN :startDate AND :endDate ;  