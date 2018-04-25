 select  moins_de_mille AS 'Patient avec résultat de charge virale < 1000 copies/ml', plus_de_mille AS 'Patient avec résultat de charge virale > 1000 copies/ml', Patient_unique as 'Patient unique'  from(
select 
COUNT(DISTINCT CASE WHEN ((pl.test_id = 856 AND pl.test_done = 1 AND pl.test_result < 1000) OR (pl.test_id = 1305 AND pl.test_done = 1 AND pl.test_result = 1306)) THEN pl.patient_id else null END) AS moins_de_mille,
COUNT(DISTINCT CASE WHEN (pl.test_id = 856 AND pl.test_done = 1 AND pl.test_result >= 1000) THEN pl.patient_id else null END) AS plus_de_mille,
count(distinct pl.patient_id) as Patient_unique
FROM isanteplus.patient_laboratory pl,(select plab.patient_id,max(plab.visit_date) as test_date from isanteplus.patient_laboratory plab 
WHERE plab.test_id IN(856,1305) AND plab.test_done = 1 group by 1)B
WHERE pl.test_id IN(856,1305)
AND B.patient_id = pl.patient_id 
AND pl.visit_date = B.test_date
AND pl.test_done = 1
AND pl.visit_date BETWEEN :startDate AND :endDate) A;