select  Communautaire,Institution,((Communautaire /(Institution+Communautaire)) * 100) as pourcentage, Patient_unique as 'Patient unique'  from(
select 
COUNT(DISTINCT CASE WHEN (pdisp.dispensation_location=1755) THEN pdisp.patient_id else null END) AS Communautaire,
COUNT(DISTINCT CASE WHEN (pdisp.dispensation_location=0) THEN pdisp.patient_id else null END) AS Institution,
count(distinct pdisp.patient_id) as Patient_unique
FROM isanteplus.patient_dispensing pdisp,(select patient_id,max(dispensation_date) as dispensation_date from isanteplus.patient_dispensing group by 1)B
WHERE pdisp.dispensation_location IN(1755,0)
AND B.patient_id=pdisp.patient_id 
AND pdisp.dispensation_date=B.dispensation_date
AND pdisp.patient_id IN(SELECT parv.patient_id FROM isanteplus.patient_on_arv parv)
AND pdisp.dispensation_date BETWEEN :startDate AND :endDate) A;