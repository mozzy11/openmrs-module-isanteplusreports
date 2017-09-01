package org.openmrs.module.isanteplusreports.page.controller;

import org.apache.commons.lang.time.DateUtils;
import org.openmrs.Location;
import org.openmrs.api.LocationService;
import org.openmrs.module.isanteplusreports.healthqual.HealthQualManager;
import org.openmrs.module.isanteplusreports.healthqual.builder.HealthQualReportBuilder;
import org.openmrs.module.isanteplusreports.healthqual.model.HealthQualSelectedIndicator;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualUtils.getReportData;

public class HealthQualReportPageController {

	public void get(@SpringBean HealthQualManager healthQualManager,
	        @RequestParam(required = false, value = "startDate") Date startDate,
	        @RequestParam(required = false, value = "endDate") Date endDate, PageModel model) throws IOException {
		
		if (startDate == null) {
			startDate = DateUtils.addDays(new Date(), -21);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		
		model.addAttribute("manager", healthQualManager);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("htmlResult", null);
		model.addAttribute("pdfResult", null);
	}
	
	public void post(@SpringBean HealthQualManager healthQualManager,
			@SpringBean("locationService") LocationService locationService,
	        @RequestParam(value = "indicatorList") List<HealthQualSelectedIndicator> indicators,
	        @RequestParam(required = false, value = "startDate") Date startDate,
	        @RequestParam(required = false, value = "endDate") Date endDate, PageModel model) throws IOException,
	        EvaluationException {
		
		if (startDate == null) {
			startDate = DateUtils.addDays(new Date(), -21);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		
		HealthQualReportBuilder builder = new HealthQualReportBuilder();
		Location location = locationService.getDefaultLocation();
		builder.setClinic(location.getDisplayString());
		builder.setClinicDepartment(location.getStateProvince());
		builder.setStartDate(startDate);
		builder.setEndDate(endDate);

		for (HealthQualSelectedIndicator indicator : indicators) {
			builder.addReportData(getReportData(indicator.getUuid(), startDate, endDate, indicator.getOptions()));
		}
		
		model.addAttribute("manager", healthQualManager);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("htmlResult", builder.buildHtmlTables());
		model.addAttribute("pdfResult", builder.buildPdf());
	}
}
