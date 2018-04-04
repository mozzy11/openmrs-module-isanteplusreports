select count(CASE WHEN p.gender='F' THEN p.gender
			END) as Femme,
	count(CASE WHEN p.gender='M' THEN p.gender
			END) as Homme, 
    count(p.gender) as total
            FROM isanteplus.patient p
            WHERE p.date_created between :startDate AND :endDate;