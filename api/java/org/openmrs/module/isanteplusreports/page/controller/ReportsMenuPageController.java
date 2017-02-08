package org.openmrs.module.isanteplusreports.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.page.PageModel;

public class ReportsMenuPageController {
	
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(PageModel model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}
}
