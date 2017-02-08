package org.openmrs.module.isanteplusreports.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.evaluation.Definition;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.ReportRequest.Status;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.renderer.ReportRenderer;
import org.openmrs.module.reporting.report.renderer.SimpleHtmlReportRenderer;
import org.openmrs.module.reporting.report.service.ReportService;



public class RegisterAllReports {
	private static Log log = LogFactory.getLog(RegisterAllReports.class);
	
	IsantePlusReportsProperties props = new IsantePlusReportsProperties();
	
	public void cleanTables() throws Exception {				
	
		ReportService rs=Context.getService(ReportService.class);
		List<ReportDesign> rDes=rs.getAllReportDesigns(true);		
		for (ReportDesign reportDesign : rDes) {
	        rs.purgeReportDesign(reportDesign);
        }
		
		ReportDefinitionService rds=Context.getService(ReportDefinitionService.class);
		List<ReportDefinition> rDefs=rds.getAllDefinitions(true);		
		for (ReportDefinition reportDefinition : rDefs) {		
			rds.purgeDefinition(reportDefinition);
        }
		for (ReportRequest request : rs.getReportRequests(null, null, null, Status.COMPLETED,Status.FAILED)) {
			try {
				rs.purgeReportRequest(request);
			}
			catch (Exception e) {
				log.warn("Unable to delete old report request: " + request, e);
			}
		}	
    }
	
	 @DocumentedDefinition("fullDataExports")
	public void nextVisitSevenDays() throws Exception{
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("visitNextSevenDays.sql","PatientNextVisitNextSevenDays","Reports for the next seven days");
	    Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		
		ReportDefinition repDefinition= reportDefinition("Patient avec visit dans les 7 prochains jours","Patient avec visit dans les 7 prochains jours",props.SEVEN_DAYS_REPORT_DEFINITION_UUID);
		repDefinition.addDataSetDefinition(sqlData, null);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design 7 days",repDefinition,SimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		
	}
	 @DocumentedDefinition("fullDataExports")
		public void nextVisitFourteenDays() throws Exception{
			SqlDataSetDefinition sqlData = sqlDataSetDefinition("visitNextFourteenDays.sql","PatientNextVisitNextFourteenDays","Reports for the next fourteen days");
		    Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
			Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
			
			ReportDefinition repDefinition= reportDefinition("Patient avec visit dans les 14 prochains jours","Patient avec visit dans les 14 prochains jours",props.FOURTEEN_DAYS_REPORT_DEFINITION_UUID);
			repDefinition.addDataSetDefinition(sqlData, null);
			Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
			
			ReportService rs = Context.getService(ReportService.class);
			ReportDesign rDesign = reportDesign("Html design 14 days",repDefinition,SimpleHtmlReportRenderer.class);
			rs.saveReportDesign(rDesign);
			
		}
	 @DocumentedDefinition("fullDataExports")
		public void patientAgeGroup() throws Exception{
			SqlDataSetDefinition sqlData = sqlDataSetDefinition("patientAgeGroup.sql","Groupe patient par age","Groupe patient par age");
		    Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
			Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
			
			ReportDefinition repDefinition= reportDefinition("Patient par groupe d'age","Patient par group d'age",props.PATIENT_AGE_GROUP_REPORT_DEFINITION_UUID);
			repDefinition.addDataSetDefinition(sqlData, null);
			Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
			
			ReportService rs = Context.getService(ReportService.class);
			ReportDesign rDesign = reportDesign("Html design Age group",repDefinition,SimpleHtmlReportRenderer.class);
			rs.saveReportDesign(rDesign);
			
		}
	/*private SqlDataSetDefinition sqlDataSetDefinition(String resourceName, Replacements replacements) {
	        String sql = IsantePlusReportsUtil.getStringFromResource("org/openmrs/module/isanteplusreports/sql/fullDataExports/" + resourceName);
	        if (replacements != null) {
	            for (Map.Entry<String, String> entry : replacements.entrySet()) {
	                sql = sql.replaceAll(":" + entry.getKey(), entry.getValue());
	               
	             }
	        }

	        SqlDataSetDefinition definition = new SqlDataSetDefinition();
	        definition.setSqlQuery(sql);
	        return definition;
	    }
	*/
	 
	 private SqlDataSetDefinition sqlDataSetDefinition(String resourceName, String name, String description ) {
	        String sql = IsantePlusReportsUtil.getStringFromResource("org/openmrs/module/isanteplusreports/sql/fullDataExports/" + resourceName);
	        SqlDataSetDefinition definition = new SqlDataSetDefinition();
	        definition.setSqlQuery(sql);
	        definition.setName(name);
	        definition.setDescription(description);
	        return definition;
	    }
	 private ReportDefinition reportDefinition(String name, String description, String uuid ) {
		 ReportDefinition rDefinition = new ReportDefinition();
		 rDefinition.setName(name);
		 rDefinition.setDescription(description);
		 rDefinition.setUuid(uuid);
	        return rDefinition;
	    }
	 private ReportDesign reportDesign(String name, ReportDefinition rDefinition, Class<? extends ReportRenderer > rendererType) {
		 ReportDesign rDesign = new ReportDesign();
		 rDesign.setName(name);
		 rDesign.setReportDefinition(rDefinition);
		 rDesign.setRendererType(rendererType);
	        return rDesign;
	    }
	 

}
