package org.openmrs.module.isanteplusreports.pnlsReport.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.healthqual.util.HealthQualUtils;
import org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants;
import org.openmrs.module.isanteplusreports.reporting.utils.EmrReportingUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.report.ReportData;

public class pnlsReportUtils {
	
	public static ReportData getReportData(String reportUuid, Date startDate, Date endDate,
	        Map<String, Object> additionalOptions) {
		return HealthQualUtils.getReportData(reportUuid, startDate, endDate, additionalOptions);
	}
	
	public static String replaceNonBreakingSpaces(String stringToClean) {
		return HealthQualUtils.replaceNonBreakingSpaces(stringToClean);
	}
	
	public static CohortIndicator cohortIndicatorFromSqlResource(String sql, String name, List<Parameter> parameters) {
		return EmrReportingUtils.cohortIndicator(name, parameters,
		    Mapped.mapStraightThrough(cohortFromSqlResource(sql, name, parameters)));
	}
	
	public static CohortDefinition cohortFromSqlResource(String sqlResourceName, String name, List<Parameter> parameters) {
		String sql = IsantePlusReportsUtil
		        .getStringFromResource(PnlsReportConstants.PNLS_REPORTS_RESOURCE_PATH + sqlResourceName);
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName(name);
		cd.setDescription(MessageUtil.translate(name));
		for (Parameter parameter : parameters) {
			cd.addParameter(parameter);
		}
		
		cd.setQuery(sql);
		return cd;
	}
	
	public static void addAgeandGenderColumns(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator,
	        String g) {
		
		String genderDimension = "";
		if (StringUtils.equals(g, "M")) {
			genderDimension = "|gender=M";
		} else if (StringUtils.equals(g, "F")) {
			genderDimension = "|gender=F";
		}
		constructColumn("0-1" + g, "0-1 Years", CohortIndicator, "age=0_1" + genderDimension, dsd);
		constructColumn("1-4" + g, "1-4 Years", CohortIndicator, "age=1_4" + genderDimension, dsd);
		constructColumn("5-9" + g, "5-9 Years", CohortIndicator, "age=5_9" + genderDimension, dsd);
		constructColumn("10-14" + g, "10-14 Years", CohortIndicator, "age=10_14" + genderDimension, dsd);
		constructColumn("15-19" + g, "15-19 Years", CohortIndicator, "age=15_19" + genderDimension, dsd);
		constructColumn("20-24" + g, "20-24 Years", CohortIndicator, "age=20_24" + genderDimension, dsd);
		constructColumn("25-29" + g, "25-29 Years", CohortIndicator, "age=25_29" + genderDimension, dsd);
		constructColumn("30-34" + g, "30-34 Years", CohortIndicator, "age=30_34" + genderDimension, dsd);
		constructColumn("35-39" + g, "35-39 Years", CohortIndicator, "age=35_39" + genderDimension, dsd);
		constructColumn("40-44" + g, "40-44 Years", CohortIndicator, "age=40_44" + genderDimension, dsd);
		constructColumn("45-49" + g, "45-49 Years", CohortIndicator, "age=45_49" + genderDimension, dsd);
		constructColumn("50" + g, "50+ Years", CohortIndicator, "age=50" + genderDimension, dsd);
		constructColumn("UnKnown" + g, " unknown Years", CohortIndicator, "age=unknown" + genderDimension, dsd);
		constructColumn("Total" + g, "Total Patients", CohortIndicator, "gender=" + g, dsd);
	}
	
	public static void addTotalColumns(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator) {
		
		constructColumn("0-1T", "0-1 Years", CohortIndicator, "age=0_1", dsd);
		constructColumn("1-4T", "1-4 Years", CohortIndicator, "age=1_4", dsd);
		constructColumn("5-9T", "5-9 Years", CohortIndicator, "age=5_9", dsd);
		constructColumn("10-14T", "10-14 Years", CohortIndicator, "age=10_14", dsd);
		constructColumn("15-19T", "15-19 Years", CohortIndicator, "age=15_19", dsd);
		constructColumn("20-24T", "20-24 Years", CohortIndicator, "age=20_24", dsd);
		constructColumn("25-29T", "25-29 Years", CohortIndicator, "age=25_29", dsd);
		constructColumn("30-34T", "30-34 Years", CohortIndicator, "age=30_34", dsd);
		constructColumn("35-39T", "35-39 Years", CohortIndicator, "age=35_39", dsd);
		constructColumn("40-44T", "40-44 Years", CohortIndicator, "age=40_44", dsd);
		constructColumn("45-49T", "45-49 Years", CohortIndicator, "age=45_49", dsd);
		constructColumn("50T", "50+ Years", CohortIndicator, "age=50", dsd);
		constructColumn("UnKnownT", " unknown Years", CohortIndicator, "age=unknown", dsd);
		constructColumn("TotalT", "Total Patients", CohortIndicator, "", dsd);
	}
	
	public static void constructColumn(String columnName, String ColumnDescription, CohortIndicator CohortIndicator,
	        String dimension, CohortIndicatorDataSetDefinition dsd) {
		dsd.addColumn(columnName, ColumnDescription, Mapped.mapStraightThrough(CohortIndicator), dimension);
	}
	
}
