SELECT ps.patient_id
FROM isanteplus.patient_status_arv ps
    WHERE ps.id_status IN (6,8)
    AND ps.date_started_status BETWEEN :startDate AND :endDate;