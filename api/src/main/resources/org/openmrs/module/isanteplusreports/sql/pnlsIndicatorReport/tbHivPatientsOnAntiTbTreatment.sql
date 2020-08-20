    SELECT pa.patient_id
   FROM isanteplus.patient_on_art pa	   
	     WHERE  pa.date_started_anti_tb_treatment BETWEEN :startDate AND :endDate 
		  AND pa.tb_status = "POSTIVE"
		  AND pa.started_anti_tb_treatment = 1 ;