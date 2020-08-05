SELECT pat.patient_id 
    FROM isanteplus.patient_on_art pat
    WHERE pat.deceased = 1
    AND pat.cause_of_death_for_lost = "HIV" ;