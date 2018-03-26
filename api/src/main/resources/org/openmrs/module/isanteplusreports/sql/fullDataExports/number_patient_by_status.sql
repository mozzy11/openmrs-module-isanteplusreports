/*Number of patients par ARV status*/
select asl.name_fr as 'Statut ARV', 
CASE WHEN (MAX(psa.start_date) BETWEEN :startDate AND :endDate)
THEN COUNT(distinct psa.patient_id) ELSE 0 END as Total
FROM isanteplus.arv_status_loockup asl 
LEFT OUTER JOIN isanteplus.patient_status_arv psa
ON psa.id_status=asl.id 
WHERE asl.id <> 12
GROUP BY asl.name_fr;