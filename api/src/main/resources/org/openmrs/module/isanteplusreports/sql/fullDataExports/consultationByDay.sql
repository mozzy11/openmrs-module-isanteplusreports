SELECT patvi.visit_date as VisitDate, loc.name as Clinic, CONCAT(pat.given_name,' ',pat.family_name) as Patient,
CASE WHEN pat.vih_status=1 THEN 'Patient VIH'
     WHEN pat.vih_status=0 THEN 'Patient non VIH'
END as PatientType, enct.name as Form
FROM isanteplus.patient pat, isanteplus.patient_visit patvi, openmrs.location loc, openmrs.encounter enc,
 openmrs.encounter_type enct
 WHERE pat.patient_id=patvi.patient_id
 AND pat.location_id=loc.location_id
 AND patvi.encounter_id=enc.encounter_id
 AND enc.encounter_type=enct.encounter_type_id
 AND patvi.visit_date BETWEEN :startDate AND :endDate
 AND pat.location_id = :location
 GROUP BY patvi.visit_date DESC;