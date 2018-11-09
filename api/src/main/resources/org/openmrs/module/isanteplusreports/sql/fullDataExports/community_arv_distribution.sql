select  Communautaire,Institution,CONCAT(ROUND(((Communautaire /(Institution+Communautaire)) * 100),2)," %") as pourcentage, (Institution+Communautaire) as 'Patient unique'  from(
select 
COUNT(DISTINCT CASE WHEN (pdisp.dispensation_location=1755) THEN pdisp.patient_id else null END) AS Communautaire,
COUNT(DISTINCT CASE WHEN (pdisp.dispensation_location=0) THEN pdisp.patient_id else null END) AS Institution
FROM isanteplus.patient_dispensing pdisp,(select patient_id,max(dispensation_date) as dispensation_date from isanteplus.patient_dispensing where dispensation_date BETWEEN :startDate AND :endDate group by 1)B
WHERE pdisp.dispensation_location IN(1755,0)
AND B.patient_id=pdisp.patient_id 
AND pdisp.dispensation_date=B.dispensation_date
AND pdisp.arv_drug = 1065
AND pdisp.dispensation_date BETWEEN :startDate AND :endDate) A;