package org.openmrs.module.isanteplusreports.healthqual;

import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class HealthQualManager {
	
	private static final String[] adultIndicatorsUuid = {};
	
	private static final String[] pediatricIndicatorsUuid = {};
	
	@Autowired
	private ReportDefinitionService reportDefinitionService;
	
	public List<ReportDefinition> getAdultIndicators() {
		return uuidToReportDefinition(Arrays.asList(adultIndicatorsUuid));
	}
	
	public List<ReportDefinition> getPediatricIndicators() {
		return uuidToReportDefinition(Arrays.asList(pediatricIndicatorsUuid));
	}
	
	private List<ReportDefinition> uuidToReportDefinition(List<String> uuids) {
		List<ReportDefinition> indicators = new ArrayList<ReportDefinition>();
		for (String uuid : uuids) {
			indicators.add(reportDefinitionService.getDefinitionByUuid(uuid));
		}
		return indicators;
	}
}
