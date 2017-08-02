select count(vtype.v_type) as Total FROM isanteplus.visit_type vtype, isanteplus.patient_pregnancy pp
WHERE vtype.patient_id=pp.patient_id
AND vtype.concept_id=160288 AND v_type=1622
AND vtype.encounter_date BETWEEN :startDate AND :endDate
AND vtype.encounter_date BETWEEN pp.start_date AND pp.end_date
AND vtype.encounter_date=(select MIN(vt.encounter_date) FROM isanteplus.visit_type vt WHERE vt.patient_id=pp.patient_id);