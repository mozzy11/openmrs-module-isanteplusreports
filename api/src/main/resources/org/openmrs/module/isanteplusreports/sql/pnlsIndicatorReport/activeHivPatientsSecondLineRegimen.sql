SELECT pa.patient_id
   FROM isanteplus.patient_on_art pa	   
	     WHERE  pa.inactive = 0
	     AND pa.date_started_arv BETWEEN :startDate AND :endDate 
	     AND pa.receive_arv =1
	     AND pa.treatment_regime_lines = "SECOND LINE" ;