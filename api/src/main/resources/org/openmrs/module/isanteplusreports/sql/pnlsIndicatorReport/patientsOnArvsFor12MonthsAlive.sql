SELECT p.patient_id
FROM isanteplus.patient_on_art pat ,isanteplus.patient_status_arv psa  , isanteplus.patient p  ,(SELECT ps.patient_id ,MAX(ps.date_started_status) AS max_staus_date FROM isanteplus.patient_status_arv ps WHERE ps.date_started_status BETWEEN :startDate AND :endDate  GROUP BY 1) B
WHERE p.patient_id = pat.patient_id
AND p.patient_id = psa.patient_id
AND B.patient_id = p.patient_id
AND ( TIMESTAMPDIFF(MONTH, pat.date_started_arv_for_transfered ,:endDate) >= 12 OR TIMESTAMPDIFF(MONTH, p.date_started_arv ,:endDate) >= 12 )
AND p.patient_id NOT IN (SELECT psa.patient_id FROM isanteplus.patient_status_arv psa , isanteplus.patient p WHERE psa.patient_id = p.patient_id AND psa.id_status =2 AND p.date_started_arv <= :endDate)
AND B.max_staus_date = psa.date_started_status
AND psa.id_status <> 1 ;