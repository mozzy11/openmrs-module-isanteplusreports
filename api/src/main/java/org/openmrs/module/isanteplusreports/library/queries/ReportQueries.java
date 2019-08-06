package org.openmrs.module.isanteplusreports.library.queries;

public class ReportQueries {
	
	public static final String VL_LESS_1000 = "select patient_id\n" + 
			"from(\n" + 
			"	select patient_id, max(date_test_done)\n" + 
			"	from isanteplus.patient_laboratory pl\n" + 
			"	where pl.test_id=856\n" + 
			"	and pl.test_done=1\n" + 
			"	and pl.voided<>1\n" + 
			"	and pl.date_test_done between '2018-01-01' AND '2019-07-11'\n" + 
			"	and pl.test_result is not null \n" + 
			"	and pl.test_result>0\n" + 
			"	and pl.test_result < 1000\n" + 
			"	group by patient_id\n" + 
			") lab";
	
	public static final String VL_MORE_1000 = "select patient_id\n" + 
			"from(\n" + 
			"	select patient_id, max(date_test_done)\n" + 
			"	from isanteplus.patient_laboratory pl\n" + 
			"	where pl.test_id=856\n" + 
			"	and pl.test_done=1\n" + 
			"	and pl.voided<>1\n" + 
			"	and pl.date_test_done between '2018-01-01' AND '2019-07-11'\n" + 
			"	and pl.test_result is not null \n" + 
			"	and pl.test_result>0\n" + 
			"	and pl.test_result >= 1000\n" + 
			"	group by patient_id\n" + 
			") lab "
	/*+ "and art_status.location = :location"*/;
}
