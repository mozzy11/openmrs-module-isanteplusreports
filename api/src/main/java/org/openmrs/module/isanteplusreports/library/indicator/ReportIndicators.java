package org.openmrs.module.isanteplusreports.library.indicator;

import static org.openmrs.module.isanteplusreports.reporting.utils.EmrReportingUtils.cohortIndicator;
import static org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils.map;

import org.openmrs.module.isanteplusreports.library.cohort.ReportCohort;
import org.openmrs.module.reporting.indicator.CohortIndicator;

public class ReportIndicators {

	public static CohortIndicator vlIndicator(String sql) {
		return cohortIndicator("# VL Patients",
				map(ReportCohort.vlCohort(sql), "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
}
