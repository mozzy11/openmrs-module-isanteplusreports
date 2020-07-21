SELECT pat.patient_id 
    FROM isanteplus.patient_on_art pat
    WHERE pat.lost = 1
    AND pat.resumed_arv_after_lost = 1 ;