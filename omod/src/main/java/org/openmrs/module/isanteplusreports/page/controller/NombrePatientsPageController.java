package org.openmrs.module.isanteplusreports.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.ui.framework.page.PageModel;

public class NombrePatientsPageController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(PageModel model){
		DataSet dataset = Context.getService(IsantePlusReportsService.class).numberPatient();
		model.addAttribute("columns", dataset.getMetaData().getColumns());
		model.addAttribute("columnsvalues", dataset.iterator());
	}
}
