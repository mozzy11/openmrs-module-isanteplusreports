package org.openmrs.module.isanteplusreports.healthqual.model;

import org.openmrs.module.isanteplusreports.healthqual.HealthQualIndicatorOption;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.report.definition.ReportDefinition;

public class HealthQualIndicator {
	
	private String name;
	
	private String uuid;
	
	private HealthQualIndicatorOption option;
	
	public HealthQualIndicator(ReportDefinition report, HealthQualIndicatorOption option) {
		this.option = option;
		this.name = MessageUtil.translate(report.getName());
		this.uuid = report.getUuid();
	}
	
	public String getName() {
		return name;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public HealthQualIndicatorOption getOption() {
		return option;
	}
}
