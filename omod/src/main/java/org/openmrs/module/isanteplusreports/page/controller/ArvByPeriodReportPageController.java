package org.openmrs.module.isanteplusreports.page.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.coreapps.CoreAppsProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 */
public class ArvByPeriodReportPageController {
	
	public void get(@SpringBean ReportDefinitionService reportDefinitionService,
	/* @SpringBean CoreAppsProperties coreAppsProperties,*/
	@RequestParam("reportDefinition") String reportDefinitionUuid, PageModel model) throws Exception {
		
		if (reportDefinitionUuid.startsWith("@")) {
			// References a public static field on MirebalaisProperties, e.g. @DAILY_REGISTRATIONS_REPORT_DEFINITION_UUID
			reportDefinitionUuid = (String) IsantePlusReportsProperties.class.getField(reportDefinitionUuid.substring(1))
			        .get(null);
		}
		ReportDefinition reportDefinition = reportDefinitionService.getDefinitionByUuid(reportDefinitionUuid);
		if (reportDefinition == null) {
			throw new IllegalArgumentException("No reportDefinition with the given uuid");
		}
		
		model.addAttribute("reportDefinition", reportDefinition);
		/*model.addAttribute("dashboardUrlWithoutQueryParams", coreAppsProperties.getDashboardUrlWithoutQueryParams());*/
		model.addAttribute("privilegePatientDashboard", IsantePlusReportsProperties.PRIVILEGE_PATIENT_DASHBOARD);
		model.addAttribute("hideCounts",
		    Context.getAdministrationService().getGlobalProperty(IsantePlusReportsProperties.DAILY_CHECKINS_HIDE_COUNTS));
		
	}
	
}
