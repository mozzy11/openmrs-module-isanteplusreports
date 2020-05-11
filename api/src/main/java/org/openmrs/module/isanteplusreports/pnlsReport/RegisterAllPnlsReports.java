package org.openmrs.module.isanteplusreports.pnlsReport;

import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.reportDefinition;
import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.sqlDataSetDefinitionWithResourcePath;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;

public class RegisterAllPnlsReports {
		
	private final static Parameter START_DATE = new Parameter("startDate", "isanteplusreports.parameters.startdate", Date.class);

    private final static Parameter END_DATE = new Parameter("endDate", "isanteplusreports.parameters.enddate", Date.class);


    public static void registerAll() {	
    	 newlyEnrolledPatientsOnArt();
    }
    
    private static void newlyEnrolledPatientsOnArt() {
        registerPnlsReportWithStartAndEndDateParams(PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_SQL, "Mozzy message",
        	PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_UUID);
    }
    
    private static void registerPnlsReportWithStartAndEndDateParams(String sql, String messageProperties, String uuid) {
        SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messageProperties, messageProperties,PnlsReportConstants.PNLS_REPORTS_RESOURCE_PATH);
        sqlData.addParameter(START_DATE);
        sqlData.addParameter(END_DATE);
        Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(sqlData, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }
}
