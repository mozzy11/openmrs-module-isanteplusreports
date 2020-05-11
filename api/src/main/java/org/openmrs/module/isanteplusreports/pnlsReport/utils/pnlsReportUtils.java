package org.openmrs.module.isanteplusreports.pnlsReport.utils;

import java.util.Date;
import java.util.Map;

import org.openmrs.module.isanteplusreports.healthqual.util.HealthQualUtils;
import org.openmrs.module.reporting.report.ReportData;

public class pnlsReportUtils {
	
	
	public static ReportData getReportData(String reportUuid, Date startDate, Date endDate, Map<String, Object> additionalOptions) {
		return HealthQualUtils.getReportData(reportUuid, startDate, endDate, additionalOptions);	
	}
	
	public static String replaceNonBreakingSpaces(String stringToClean) {
		return HealthQualUtils.replaceNonBreakingSpaces(stringToClean);
	}
}
