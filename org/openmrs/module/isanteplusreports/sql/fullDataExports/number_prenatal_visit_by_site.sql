select count(vtype.v_type) as Total FROM isanteplus.visit_type vtype
WHERE vtype.concept_id=160288 AND v_type=1622
AND vtype.encounter_date BETWEEN :startDate AND :endDate;