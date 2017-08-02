select  Communautaire,Institution,Communautaire /(Institution+Communautaire) as pourcentage, Patient_unique as 'Patient unique'  from(
select 
COUNT(DISTINCT CASE WHEN (pdisp.dispensation_location=1755) THEN pdisp.patient_id else null END) AS Communautaire,
COUNT(DISTINCT CASE WHEN (pdisp.dispensation_location=0) THEN pdisp.patient_id else null END) AS Institution,
count(distinct pdisp.patient_id) as Patient_unique
FROM isanteplus.patient_dispensing pdisp
WHERE pdisp.dispensation_date BETWEEN :startDate AND :endDate) A;