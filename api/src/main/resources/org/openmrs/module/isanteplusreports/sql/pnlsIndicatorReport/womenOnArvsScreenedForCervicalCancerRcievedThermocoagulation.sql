SELECT pat.patient_id
   FROM isanteplus.patient_on_art pat
   WHERE pat.gender = 'F'
   AND pat.cervical_cancer_screenee =1
   AND pat.cervical_cancer_status = 'POSTIVE'
   AND pat.cervical_cancer_treatment ='THERMOCOAGULATION' ;
