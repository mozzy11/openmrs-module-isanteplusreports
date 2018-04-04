SELECT COUNT(DISTINCT ppr.patient_id) as Total 
FROM isanteplus.patient_pregnancy ppr
INNER JOIN isanteplus.patient_menstruation pm
ON pm.patient_id=ppr.patient_id
INNER JOIN isanteplus.visit_type vt
ON vt.patient_id=pm.patient_id
WHERE vt.encounter_date=(SELECT MIN(vtype.encounter_date) FROM isanteplus.visit_type vtype
                          WHERE vt.patient_id=vtype.patient_id
                          AND vtype.concept_id=160288 AND vtype.v_type=1622
                          AND vtype.encounter_date BETWEEN :startDate AND :endDate)
AND ppr.start_date BETWEEN :startDate AND :endDate
AND pm.encounter_date BETWEEN :startDate AND :endDate
AND TIMESTAMPDIFF(MONTH, vt.encounter_date,pm.ddr)>=3;