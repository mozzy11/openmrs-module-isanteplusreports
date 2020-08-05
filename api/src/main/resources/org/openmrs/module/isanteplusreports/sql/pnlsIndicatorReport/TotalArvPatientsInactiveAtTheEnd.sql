SELECT pa.patient_id
   FROM isanteplus.patient_on_art pa	   
	     WHERE  pa.inactive = 1
	     AND pa.date_inactive BETWEEN :startDate AND :endDate  ;