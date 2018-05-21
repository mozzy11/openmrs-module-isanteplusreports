select DISTINCT pat.st_id as 'NO. de patient attribué par le site', pat.national_id as 'Numéro d\'identité national',
pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,
TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Age, pdisp.dispensation_date as 'Dernière date', pat.last_address as Adresse, pat.telephone as Téléphone,
pat.mother_name as Contact
FROM isanteplus.patient pat, isanteplus.patient_dispensing pdisp,
    (select pdi.patient_id,max(pdi.dispensation_date) as dispensation_date from isanteplus.patient_dispensing pdi WHERE pdi.dispensation_date BETWEEN :startDate AND :endDate group by 1)B
WHERE pat.patient_id=pdisp.patient_id
AND pdisp.dispensation_location=0
AND pdisp.patient_id = B.patient_id
AND pdisp.dispensation_date = B.dispensation_date
AND pdisp.patient_id IN(SELECT parv.patient_id FROM isanteplus.patient_on_arv parv)
AND pdisp.dispensation_date BETWEEN :startDate AND :endDate;