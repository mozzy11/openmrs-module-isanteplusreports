SELECT p.patient_id
   FROM isanteplus.patient p, isanteplus.patient_on_art pa , isanteplus.patient_status_arv psa
       WHERE p.patient_id =pa.patient_id 
       AND p.patient_id = psa.patient_id
       AND psa.id_status IN (6 ,8) 
       AND pa.receive_clinical_followup =1 
       AND DATEDIFF(p.first_visit_date, :endDate) BETWEEN 180 AND 360;