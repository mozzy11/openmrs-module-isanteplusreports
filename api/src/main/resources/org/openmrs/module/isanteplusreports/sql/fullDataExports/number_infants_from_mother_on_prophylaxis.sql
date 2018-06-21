/* Nombre d'enfants n&eacute;s de m&egrave;re VIH (+) plac&eacute;s sous ARV comme prophylaxie dans les 72 heures apr&egrave;s la naissance 
 * / Number of infants born to HIV+ mothers who received ARV prophylaxis within 72 hours of birth*/
SELECT COUNT(DISTINCT pv.patient_id) AS Total FROM isanteplus.pediatric_hiv_visit pv
WHERE pv.prophylaxie72h=1065
AND pv.patient_id IN (SELECT ei.patient_id FROM isanteplus.exposed_infants ei)
AND pv.encounter_date BETWEEN :startDate AND :endDate;