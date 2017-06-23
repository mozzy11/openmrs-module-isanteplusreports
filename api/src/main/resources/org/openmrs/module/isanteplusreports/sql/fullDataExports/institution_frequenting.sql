select
 COUNT(CASE WHEN (entype.uuid='17536ba6-dd7c-4f58-8014-08c7cb798ac7'
                           OR entype.uuid='349ae0b4-65c1-4122-aa06-480f186c8350'
                           ) 
						THEN enc.encounter_id else null END) AS 'Nouveau Patient VIH',
                        
 COUNT(CASE WHEN (entype.uuid='5c312603-25c1-4dbe-be18-1a167eb85f97'
                           OR entype.uuid='12f4d7c3-e047-4455-a607-47a40fe32460'
                           OR entype.uuid='709610ff-5e39-4a47-9c27-a60e740b0944'
                           ) 
						THEN enc.encounter_id else null END) AS 'Nouveau Patient Non VIH',
 COUNT(CASE WHEN (entype.uuid='17536ba6-dd7c-4f58-8014-08c7cb798ac7'
                           OR entype.uuid='349ae0b4-65c1-4122-aa06-480f186c8350'
						   OR entype.uuid='5c312603-25c1-4dbe-be18-1a167eb85f97'
                           OR entype.uuid='12f4d7c3-e047-4455-a607-47a40fe32460'
                           OR entype.uuid='709610ff-5e39-4a47-9c27-a60e740b0944'
                           ) 
						THEN enc.encounter_id else null END) AS 'Nouveau Patient',
COUNT(CASE WHEN (entype.uuid='204ad066-c5c2-4229-9a62-644bc5617ca2'
                           OR entype.uuid='33491314-c352-42d0-bd5d-a9d0bffc9bf1'
                           ) 
						THEN enc.encounter_id else null END) AS 'Patient VIH',
COUNT(CASE WHEN (entype.uuid='49592bec-dd22-4b6c-a97f-4dd2af6f2171'
                           OR entype.uuid='a5600919-4dde-4eb8-a45b-05c204af8284'
						   OR entype.uuid='fdb5b14f-555f-4282-b4c1-9286addf0aae'
                           ) 
						THEN enc.encounter_id else null END) AS 'Patient Non VIH',
COUNT(CASE WHEN (entype.uuid='204ad066-c5c2-4229-9a62-644bc5617ca2'
                           OR entype.uuid='33491314-c352-42d0-bd5d-a9d0bffc9bf1'
						   OR entype.uuid='49592bec-dd22-4b6c-a97f-4dd2af6f2171'
                           OR entype.uuid='a5600919-4dde-4eb8-a45b-05c204af8284'
						   OR entype.uuid='fdb5b14f-555f-4282-b4c1-9286addf0aae'
                           ) 
						THEN enc.encounter_id else null END) AS 'Total Patient'
 FROM
 openmrs.encounter enc, openmrs.encounter_type entype
 WHERE
 enc.encounter_type=entype.encounter_type_id
 AND DATE(enc.encounter_datetime) between :startDate AND :endDate
 AND enc.location_id = :location
 AND(entype.uuid='17536ba6-dd7c-4f58-8014-08c7cb798ac7'
                           OR entype.uuid='349ae0b4-65c1-4122-aa06-480f186c8350'
						   OR entype.uuid='5c312603-25c1-4dbe-be18-1a167eb85f97'
                           OR entype.uuid='12f4d7c3-e047-4455-a607-47a40fe32460'
                           OR entype.uuid='709610ff-5e39-4a47-9c27-a60e740b0944'
                           OR entype.uuid='204ad066-c5c2-4229-9a62-644bc5617ca2'
                           OR entype.uuid='33491314-c352-42d0-bd5d-a9d0bffc9bf1'
						   OR entype.uuid='49592bec-dd22-4b6c-a97f-4dd2af6f2171'
                           OR entype.uuid='a5600919-4dde-4eb8-a45b-05c204af8284'
						   OR entype.uuid='fdb5b14f-555f-4282-b4c1-9286addf0aae');