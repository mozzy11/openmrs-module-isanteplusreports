FROM isanteplus.patient_status_arv ps  ,isanteplus.patient_on_art pat
    WHERE ps.patient_id = pat.patient_id
	AND ps.id_status =9
    AND ps.date_started_status BETWEEN :startDate AND :endDate
	AND pat.migrated = 1 ;