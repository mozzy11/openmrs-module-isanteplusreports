SELECT p.patient_id
      FROM isanteplus.patient_on_arv p , isanteplus.patient_dispensing pd ,(SELECT pd.patient_id,MIN(pd.visit_date) AS min_vist_date FROM isanteplus.patient_dispensing pd WHERE  pd.drug_id = 105281  GROUP BY 1) B ,(SELECT pd.patient_id,MAX(pd.visit_date) AS max_vist_date FROM isanteplus.patient_dispensing pd WHERE  pd.drug_id = 105281  GROUP BY 1) C
      WHERE p.patient_id = pd.patient_id
      AND (p.patient_id = B.patient_id OR p.patient_id = C.patient_id )
	   AND pd.drug_id = 105281 
	   AND (pd.visit_date = B.min_vist_date OR pd.visit_date = C.max_vist_date)
      AND pd.visit_date BETWEEN  :startDate AND :endDate 
	   AND p.voided = 0 ;