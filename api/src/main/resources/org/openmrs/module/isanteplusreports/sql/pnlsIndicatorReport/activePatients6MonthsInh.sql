SELECT p.patient_id
FROM isanteplus.patient p 
     WHERE p.date_started_arv BETWEEN :startDate AND :endDate 
     AND p.family_name = "ACTIVE" ;