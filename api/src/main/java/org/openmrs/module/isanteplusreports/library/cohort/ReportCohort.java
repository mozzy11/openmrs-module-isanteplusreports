package org.openmrs.module.isanteplusreports.library.cohort;

import java.util.Date;

import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;

public class ReportCohort {
	
	public static CohortDefinition vlCohort(String sql) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("VIRAL LOAD");
		cd.setDescription("VIRAL LOAD");
		cd.addParameter(new Parameter("startDate", "Data Inicial", Date.class));
		cd.addParameter(new Parameter("endDate", "Data Final", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.setQuery(sql);
		
		return cd;
	}
}
