package org.openmrs.module.isanteplusreports.page.controller;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.isanteplusreports.definitions.ArvReportManager;
import org.openmrs.module.isanteplusreports.model.HealthQualIndicator;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class HealthQualReportPageController {
	
	private final Log log = LogFactory.getLog(getClass());
	
	public void get(@SpringBean ArvReportManager reportManager,
	        @RequestParam(required = false, value = "startDate") Date startDate,
	        @RequestParam(required = false, value = "endDate") Date endDate, PageModel model) throws EvaluationException,
	        IOException {
		
		if (startDate == null) {
			startDate = DateUtils.addDays(new Date(), -21);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		
		model.addAttribute("reportManager", reportManager);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
	}
	
	public void post(@SpringBean ArvReportManager reportManager,
	        @SpringBean ReportDefinitionService reportDefinitionService,
	        @RequestParam(value = "indicatorList") List<HealthQualIndicator> indicators,
	        @RequestParam(required = false, value = "startDate") Date startDate,
	        @RequestParam(required = false, value = "endDate") Date endDate, PageModel model) throws EvaluationException,
	        IOException {
		
		if (startDate == null) {
			startDate = DateUtils.addDays(new Date(), -21);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		
		model.addAttribute("reportManager", reportManager);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
	}
}
