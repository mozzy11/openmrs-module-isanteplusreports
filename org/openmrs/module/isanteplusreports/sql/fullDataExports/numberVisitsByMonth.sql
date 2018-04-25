select distinct loc.name as Site,MONTH(v.date_started) as Visit_MM,
YEAR(v.date_started) as AAAA,count(distinct v.visit_id) as Count
FROM openmrs.visit v, openmrs.encounter en, openmrs.location loc
WHERE v.visit_id=en.visit_id AND en.location_id=loc.location_id
GROUP BY MONTH(v.date_started);