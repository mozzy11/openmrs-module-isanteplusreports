SELECT p.patient_id
      FROM isanteplus.patient_on_arv p , isanteplus.patient_dispensing pd ,isanteplus.patient_status_arv ps ,(SELECT pd.patient_id,MAX(pd.visit_date) AS max_vist_date FROM isanteplus.patient_dispensing pd WHERE  pd.drug_id = 105281  GROUP BY 1) B
      WHERE p.patient_id = pd.patient_id
      AND p.patient_id = ps.patient_id
      AND p.patient_id = B.patient_id
	  AND ps.id_status = 6 
	  AND ps.date_started_status BETWEEN :startDate AND :endDate 
	  AND pd.drug_id = 105281 
	   AND pd.visit_date = B.max_vist_date
      AND pd.visit_date BETWEEN :startDate AND :endDate 
	  AND p.voided = 0 ;