package org.openmrs.module.isanteplusreports.healthqual;

import org.openmrs.module.isanteplusreports.healthqual.model.HealthQualIndicator;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HealthQualManager {
	
	private static final String[] adultIndicatorsUuid = { "UUID_indicatorTest" };
	
	private static final String[] pediatricIndicatorsUuid = {};
	
	private Map<String, List<String>> options;
	
	public HealthQualManager() {
		options = new HashMap<String, List<String>>();
		String[] period = { "period", "6", "12", "24", "48" };
		options.put("UUID_indicatorTest", Arrays.asList(period));
	}
	
	@Autowired
	private ReportDefinitionService reportDefinitionService;
	
	public List<HealthQualIndicator> getAdultIndicators() {
		return uuidToReportDefinition(Arrays.asList(adultIndicatorsUuid));
	}
	
	public List<HealthQualIndicator> getPediatricIndicators() {
		return uuidToReportDefinition(Arrays.asList(pediatricIndicatorsUuid));
	}
	
	private List<HealthQualIndicator> uuidToReportDefinition(List<String> uuids) {
		List<HealthQualIndicator> indicators = new ArrayList<HealthQualIndicator>();
		for (String uuid : uuids) {
			List<String> option = options.get(uuid);
			indicators.add(new HealthQualIndicator(reportDefinitionService.getDefinitionByUuid(uuid),
			        new HealthQualIndicatorOption(option)));
		}
		return indicators;
	}
}
