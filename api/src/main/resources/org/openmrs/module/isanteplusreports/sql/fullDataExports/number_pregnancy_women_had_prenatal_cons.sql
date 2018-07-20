select count(distinct vtype.patient_id) as Total FROM isanteplus.visit_type vtype, isanteplus.patient_pregnancy pp,
(select vt.patient_id, MIN(vt.encounter_date) as encounter_date 
FROM isanteplus.visit_type vt WHERE vt.concept_id=160288 AND vt.v_type=1622 GROUP BY 1) B
WHERE vtype.patient_id=pp.patient_id
AND vtype.concept_id=160288 AND vtype.v_type=1622
AND vtype.patient_id = B.patient_id
AND vtype.encounter_date = B.encounter_date
AND vtype.encounter_date BETWEEN :startDate AND :endDate
AND pp.start_date BETWEEN :startDate AND :endDate;