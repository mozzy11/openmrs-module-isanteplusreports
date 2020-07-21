SELECT pat.patient_id
   FROM isanteplus.patient_on_art pat
   WHERE pat.gender = 'F'
   AND pat.cervical_cancer_screenee =1 ;