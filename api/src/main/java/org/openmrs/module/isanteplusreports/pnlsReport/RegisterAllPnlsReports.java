package org.openmrs.module.isanteplusreports.pnlsReport;

import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.reportDefinition;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.library.dimension.CommonDimension;
import org.openmrs.module.isanteplusreports.pnlsReport.utils.pnlsReportUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.report.definition.ReportDefinition;

public class RegisterAllPnlsReports {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private final static Parameter START_DATE = new Parameter("startDate", "isanteplusreports.parameters.startdate",
	        Date.class);
	
	private final static Parameter END_DATE = new Parameter("endDate", "isanteplusreports.parameters.enddate", Date.class);
	
	public static void registerAll() {
		newlyEnrolledPatientsOnArt();
		referredInPatientsOnArt();
	}
	
	private static void newlyEnrolledPatientsOnArt() {	
		registerPnlsReportWithStartAndEndDateParams(PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_SQL,
		    PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_MESSAGE,
		    PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_UUID);
		
	}
	
	private static void referredInPatientsOnArt() {	
		registerPnlsReportWithStartAndEndDateParams(PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_SQL,
		    PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_MESSAGE,
		    PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_UUID);		
	}
	
	private static void registerPnlsReportWithStartAndEndDateParams(String sql, String messageProperties, String uuid) {
		
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName(messageProperties);
		dsd.addParameter(START_DATE);
		dsd.addParameter(END_DATE);
		
		dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
		dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));
		
		CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());
		
		String params = "startDate=${startDate},endDate=${endDate}";
		pnlsReportUtils.addAgeandGenderColumns(dsd, CohortIndicator, "M");
		pnlsReportUtils.addAgeandGenderColumns(dsd, CohortIndicator, "F");
		pnlsReportUtils.addTotalColumns(dsd, CohortIndicator);
		
		Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(START_DATE);
		repDefinition.addParameter(END_DATE);
		repDefinition.addDataSetDefinition(dsd, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
	}
	
	
	public static List<Parameter> getParameters() {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class),
		    new Parameter("endDate", "End Date", Date.class));
	}
	
}
