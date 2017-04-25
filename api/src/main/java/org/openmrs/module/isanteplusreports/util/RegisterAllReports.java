package org.openmrs.module.isanteplusreports.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.LocationService;
import org.openmrs.api.ProviderService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.context.SessionContext;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.appui.rest.SessionController;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.report.renderer.IsantePlusOtherHtmlReportRenderer;
import org.openmrs.module.isanteplusreports.report.renderer.IsantePlusSimpleHtmlReportRenderer;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.evaluation.Definition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.ReportRequest.Status;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.manager.ReportManager;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.openmrs.module.reporting.report.renderer.ReportRenderer;
import org.openmrs.module.reporting.report.service.ReportService;
import org.openmrs.ui.framework.WebConstants;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.action.FragmentActionResult;
import org.openmrs.ui.framework.fragment.action.ObjectResult;
/*import org.openmrs.ui.framework.session.SessionFactory;*/
import org.openmrs.util.OpenmrsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

public class RegisterAllReports extends SessionContext {
	
	private static Log log = LogFactory.getLog(RegisterAllReports.class);
	
	private Integer locationId;
	
	private SessionFactory sessionFactory;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private User authenticatedUser;
	
	private Location currentLocation;
	
	private UiSessionContext sessionContext;
	
	protected Location sessionLocation;
	
	protected HttpSession session;
	
	public final static String LOCATION_SESSION_ATTRIBUTE = "emrContext.sessionLocationId";
	
	IsantePlusReportsProperties props = new IsantePlusReportsProperties();
	
	Parameter startDate = new Parameter("startDate", "isanteplusreports.parameters.startdate", Date.class);
	
	Parameter endDate = new Parameter("endDate", "isanteplusreports.parameters.enddate", Date.class);
	
	Parameter location = new Parameter("location", "isanteplusreports.parameters.location", Location.class);
	
	/*private SessionFactory sessionFactory;
	private HttpSession httpSession;
	public HttpSession getHttpSession()
	{
		return httpSession;
	}
	public void setHttpSession(HttpSession httpSession)
	{
		httpSession=this.httpSession;
	}*/
	
	/**
	 * @param locationId locationId to set
	 * @since 1.10
	 */
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private ProviderService providerService;
	
	public Location getSessionLocation() {
		return sessionLocation;
	}
	
	public void setSessionLocation(Location sessionLocation) {
		if (session != null) {
			session.setAttribute(LOCATION_SESSION_ATTRIBUTE, sessionLocation.getId());
		}
		this.sessionLocation = sessionLocation;
		this.sessionLocationId = sessionLocation.getId();
	}
	
	public void cleanTables() throws Exception {
		List<DataSetDefinition> defService = Context.getService(DataSetDefinitionService.class).getAllDefinitions(true);
		for (DataSetDefinition dataSetDef : defService) {
			Context.getService(DataSetDefinitionService.class).purgeDefinition(dataSetDef);
			
		}
		ReportService rs = Context.getService(ReportService.class);
		List<ReportDesign> rDes = rs.getAllReportDesigns(true);
		for (ReportDesign reportDesign : rDes) {
			rs.purgeReportDesign(reportDesign);
		}
		
		ReportDefinitionService rds = Context.getService(ReportDefinitionService.class);
		List<ReportDefinition> rDefs = rds.getAllDefinitions(true);
		for (ReportDefinition reportDefinition : rDefs) {
			rds.purgeDefinition(reportDefinition);
		}
		for (ReportRequest request : rs.getReportRequests(null, null, null, Status.COMPLETED, Status.FAILED)) {
			try {
				rs.purgeReportRequest(request);
			}
			catch (Exception e) {
				log.warn("Unable to delete old report request: " + request, e);
			}
		}
	}
	
	@DocumentedDefinition("fullDataExports")
	public void nextVisitSevenDays() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("visitNextSevenDays.sql", "PatientNextVisitNextSevenDays",
		    "Reports for the next seven days");
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.septJoursLibelle", "",
		    props.SEVEN_DAYS_REPORT_DEFINITION_UUID);
		repDefinition.addDataSetDefinition(sqlData, null);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design 7 days", repDefinition, IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
	}
	
	@DocumentedDefinition("fullDataExports")
	public void nextVisitFourteenDays() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("visitNextFourteenDays.sql",
		    "Patients avec visite dans les 14 prochain jours", "Patients avec visite dans les 14 prochain jours");
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.quatorzeJoursLibelle", "",
		    props.FOURTEEN_DAYS_REPORT_DEFINITION_UUID);
		repDefinition.addDataSetDefinition(sqlData, null);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design 14 days", repDefinition, IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		
	}
	
	@DocumentedDefinition("fullDataExports")
	public void patientAgeGroup() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("patientAgeGroup.sql", "Groupe patient par age",
		    "Groupe patient par age");
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.patByAge", "",
		    props.PATIENT_AGE_GROUP_REPORT_DEFINITION_UUID);
		repDefinition.addDataSetDefinition(sqlData, null);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design Age group", repDefinition, IsantePlusOtherHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		
	}
	
	@DocumentedDefinition("fullDataExports")
	public void firstVisitAge() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("firstVisitAge.sql", "Âge à la première visite",
		    "Âge à la première visite");
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		ds.addParameter(startDate);
		ds.addParameter(endDate);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.firstVisitAge", "", props.FIRSTVISITAGE);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design first visit age", repDefinition,
		    IsantePlusOtherHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		
	}
	
	@DocumentedDefinition("fullDataExports")
	public void patientsCrachatAnormalWithoutTbDiagnostic() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("patientsCrachatAnormalWithoutTbDiagnostic.sql",
		    "Patients avec analyses de crachats ou radiographie pulmonaire anormal mais, sans aucun diagnostic TB",
		    "Patients avec analyses de crachats ou radiographie pulmonaire anormal mais, sans aucun diagnostic TB");
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		ds.addParameter(startDate);
		ds.addParameter(endDate);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.patientsCrachatWithoutTbDiagnostic", "",
		    props.PATIENTCRACHATANORMALWITHOUTTBDIAGNOSTIC);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design analyses de crachats", repDefinition,
		    IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		
	}
	
	@DocumentedDefinition("fullDataExports")
	public void patientsWithCompletedTbTreatment() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("patientWithCompleteTbTreatment.sql",
		    "Patients avec traitement contre la TB complété", "Patients avec traitement TB complété");
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		ds.addParameter(startDate);
		ds.addParameter(endDate);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.patientsWithCompletedTbTreatment", "",
		    props.PATIENTSWITHTBDIAGNOSTICWITHOUTTREATMENT);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design tb complete", repDefinition,
		    IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		
	}
	
	@DocumentedDefinition("fullDataExports")
	public void patientsWithTbDiagnosticsWithoutTreatment() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("patientsWithTbDiagnosticWithoutTreatment.sql",
		    "Patients avec des diagnostics de TB, mais sans traitement",
		    "Patients avec des diagnostics de TB, mais sans traitement");
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		ds.addParameter(startDate);
		ds.addParameter(endDate);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.patientsWithTbDiagnosticWithoutTreatment", "",
		    props.PATIENTWITHCOMPLETEDTBTREATMENT);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design tb diagnostics", repDefinition,
		    IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		
	}
	
	@DocumentedDefinition("fullDataExports")
	public void patientsWithTbSymptomWithoutCrachat() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("patientsWithTbSymptomWithoutCrachat.sql",
		    "Patients avec des diagnostics de TB, mais sans traitement",
		    "Patients avec des diagnostics de TB, mais sans traitement");
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		ds.addParameter(startDate);
		ds.addParameter(endDate);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.patientsWithTbDiagnosticWithoutTreatment", "",
		    props.PATIENTWITHCOMPLETEDTBTREATMENT);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design tb diagnostics", repDefinition,
		    IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		
	}
	
	@DocumentedDefinition("fullDataExports")
	public void patientsWithTbSymptomsignWithoutCrachat() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition(
		    "patientsWithTbSymptomWithoutCrachat.sql",
		    "Patients avec des signes et symptômes suggérant une tuberculose, sans analyse de crachats ou radiographie pulmonaire",
		    "Patients avec des signes et symptômes suggérant une tuberculose, sans analyse de crachats ou radiographie pulmonaire");
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		ds.addParameter(startDate);
		ds.addParameter(endDate);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.patientsWithTbDiagnosticWithoutCrachat", "",
		    props.PATIENTSWITHTBDIAGNOSTICWITHOUTCRACHAT);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design tb sign", repDefinition, IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		
	}
	
	@DocumentedDefinition("fullDataExports")
	public void consultationByDay() throws Exception {
		authenticatedUser = Context.getAuthenticatedUser();
		currentLocation = Context.getLocationService().getLocation(OpenmrsConstants.USER_PROPERTY_DEFAULT_LOCATION);
		location.setDefaultValue("${ sessionContext.sessionLocation }");
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("consultationByDay.sql", "Consultation par jour",
		    "Consultation par jour");
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		sqlData.addParameter(location);
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		ds.addParameter(startDate);
		ds.addParameter(endDate);
		ds.addParameter(location);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		mappings.put("location", "${location}");
		
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.consultationByDay", "", props.CONSULTATIONBYDAY);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addParameter(location);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design consultation by day", repDefinition,
		    IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		locationService = Context.getLocationService();
		providerService = Context.getProviderService();
		
		//rm.
		// List<ReportRequest> scheduledRequests = rm.constructScheduledRequests(repDefinition);
		//request = Context.
		// request= new HttpServletRequest();
		// UiSessionContext uiSessionContext = new UiSessionContext(locationService, providerService, null);
		
	}
	
	@DocumentedDefinition("fullDataExports")
	public void numberVisitsByMonth() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("numberVisitsByMonth.sql", "Nombre de visites par mois",
		    "Nombre de visites par mois");
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.numberVisitByMonth", "",
		    props.NUMBERVISITSBYMONTH);
		repDefinition.addDataSetDefinition(sqlData, null);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design visits by month", repDefinition,
		    IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		
	}
	
	@DocumentedDefinition("fullDataExports")
	public void numberPatientBySex() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("numberPatientsBySex.sql", "Nombre de patients par sexe",
		    "Nombre de patients par sexe");
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		ds.addParameter(startDate);
		ds.addParameter(endDate);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.numberPatientBySex", "",
		    props.NUMBERPATIENTBYSEX);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design patient by sex", repDefinition,
		    IsantePlusOtherHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		
	}
	
	@DocumentedDefinition("fullDataExports")
	public void dispensingMedications() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("dispensingMedications.sql", "Médicaments Dispensés",
		    "Médicaments Dispensés");
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.dispensingMedications", "",
		    props.DISPENSINGMEDICATIONS);
		repDefinition.addDataSetDefinition(sqlData, null);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design dispensingMedications", repDefinition,
		    IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
	}
	
	@DocumentedDefinition("fullDataExports")
	public void patientsStatusList() throws Exception {
		SqlDataSetDefinition sqlData = sqlDataSetDefinition("patient_status.sql", "Status des patients VIH",
		    "Status des patients VIH");
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		Definition ds = Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		ds.addParameter(startDate);
		ds.addParameter(endDate);
		Context.getService(SerializedDefinitionService.class).saveDefinition(ds);
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition("isanteplusreports.patientStatus", "", props.PATIENTSTATUS);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html design status", repDefinition, IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		
	}
	
	/*private SqlDataSetDefinition sqlDataSetDefinition1(String resourceName, Replacements replacements) {
	        String sql = IsantePlusReportsUtil.getStringFromResource("org/openmrs/module/isanteplusreports/sql/fullDataExports/" + resourceName);
	        if (replacements != null) {
	            for (Map.Entry<String, String> entry : replacements.entrySet()) {
	                sql = sql.replaceAll(":" + entry.getKey(), entry.getValue());
	                replacements.entrySet();
	             }
	        }

	        SqlDataSetDefinition definition = new SqlDataSetDefinition();
	        definition.setSqlQuery(sql);
	        return definition;
	    }
	*/
	
	private SqlDataSetDefinition sqlDataSetDefinition(String resourceName, String name, String description) {
		String sql = IsantePlusReportsUtil.getStringFromResource("org/openmrs/module/isanteplusreports/sql/fullDataExports/"
		        + resourceName);
		SqlDataSetDefinition definition = new SqlDataSetDefinition();
		definition.setSqlQuery(sql);
		definition.setName(name);
		definition.setDescription(description);
		return definition;
	}
	
	private ReportDefinition reportDefinition(String name, String description, String uuid) {
		ReportDefinition rDefinition = new ReportDefinition();
		rDefinition.setName(name);
		rDefinition.setDescription(description);
		rDefinition.setUuid(uuid);
		return rDefinition;
	}
	
	private ReportDesign reportDesign(String name, ReportDefinition rDefinition, Class<? extends ReportRenderer> rendererType) {
		ReportDesign rDesign = new ReportDesign();
		rDesign.setName(name);
		rDesign.setReportDefinition(rDefinition);
		rDesign.setRendererType(rendererType);
		return rDesign;
	}
	
}
