SELECT p.patient_id
FROM isanteplus.patient_status_arv psa  , isanteplus.patient p
WHERE p.patient_id = psa.patient_id
AND p.transferred_in =0
AND TIMESTAMPDIFF(MONTH, p.date_started_arv ,:endDate) >= 12 
AND p.patient_id NOT IN (SELECT psa.patient_id FROM isanteplus.patient_status_arv psa , isanteplus.patient p WHERE psa.patient_id = p.patient_id AND psa.id_status =2 AND p.date_started_arv <= :endDate);