package org.openmrs.module.isanteplusreports.page.controller;


import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.ui.framework.page.PageModel;

public class PatientByAgeGroupPageController {
	
	
	protected final Log log = LogFactory.getLog(getClass());
	 
	public void controller(PageModel model, HttpSession session) {
	
	DataSet dataset = Context.getService(IsantePlusReportsService.class).patientTrancheAge();
	model.addAttribute("columns", dataset.getMetaData().getColumns());
	model.addAttribute("columnsvalues", dataset.iterator());
		
	}

}
