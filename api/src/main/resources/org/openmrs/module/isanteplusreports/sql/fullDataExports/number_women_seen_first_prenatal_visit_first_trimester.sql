SELECT COUNT(DISTINCT ppr.patient_id) as Total 
FROM isanteplus.patient_pregnancy ppr
INNER JOIN isanteplus.patient_menstruation pm
ON pm.patient_id=ppr.patient_id
INNER JOIN isanteplus.visit_type vt
ON vt.patient_id=pm.patient_id
INNER JOIN (SELECT vtype.patient_id, MIN(vtype.encounter_date) as encounter_date FROM isanteplus.visit_type vtype
                          WHERE vtype.concept_id=160288 AND vtype.v_type=1622
                          AND vtype.encounter_date BETWEEN :startDate AND :endDate) B
ON vt.patient_id = B.patient_id
WHERE vt.encounter_date = B.encounter_date
AND ((ppr.start_date BETWEEN :startDate AND :endDate) OR (ppr.end_date BETWEEN :startDate AND :endDate))
AND pm.encounter_date BETWEEN :startDate AND :endDate
AND TIMESTAMPDIFF(MONTH, B.encounter_date,pm.ddr)>=3;