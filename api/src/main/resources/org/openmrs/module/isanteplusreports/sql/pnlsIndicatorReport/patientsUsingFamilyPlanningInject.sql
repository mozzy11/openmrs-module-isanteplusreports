SELECT pat.patient_id
   FROM isanteplus.patient_on_art pat
   WHERE pat.using_family_planning_method = "INJECT" ;