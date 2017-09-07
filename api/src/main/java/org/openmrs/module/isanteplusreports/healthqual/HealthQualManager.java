package org.openmrs.module.isanteplusreports.healthqual;

import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.isanteplusreports.healthqual.model.HealthQualIndicator;
import org.openmrs.module.isanteplusreports.healthqual.model.HealthQualIndicatorOption;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
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
	
	private static final String[] adultIndicatorsUuid = {
			"UUID_indicatorTest",
			IsantePlusReportsProperties.HEALTH_QUAL_RETENTION_OF_PATIENTS_ON_ART,
	};
	
	private static final String[] pediatricIndicatorsUuid = {
			IsantePlusReportsProperties.HEALTH_QUAL_CHILDREN_REGULARLY_FOLLOWED_ON_ART,
			IsantePlusReportsProperties.HEALTH_QUAL_CHILDREN_HIV_AND_PLACED_ON_ART,
			IsantePlusReportsProperties.HEALTH_QUAL_PEDIATRIC_HIV_AND_COTRIMOXAZOLE_PROPHY
	};
	
	private Map<String, HealthQualIndicatorOption> options = new HashMap<String, HealthQualIndicatorOption>();
	
	@Autowired
	private ReportDefinitionService reportDefinitionService;
	
	public HealthQualManager() {
		createIndicatorOptions();
	}
	
	public List<HealthQualIndicator> getAdultIndicators() {
		return uuidToReportDefinition(Arrays.asList(adultIndicatorsUuid));
	}
	
	public List<HealthQualIndicator> getPediatricIndicators() {
		return uuidToReportDefinition(Arrays.asList(pediatricIndicatorsUuid));
	}
	
	private List<HealthQualIndicator> uuidToReportDefinition(List<String> uuids) {
		List<HealthQualIndicator> indicators = new ArrayList<HealthQualIndicator>();
		for (String uuid : uuids) {
			ReportDefinition reportDefinition = reportDefinitionService.getDefinitionByUuid(uuid);
			HealthQualIndicatorOption option = options.get(uuid);
			if (reportDefinition != null) {
				indicators.add(new HealthQualIndicator(reportDefinition, option));
			}
		}
		return indicators;
	}
	
	private void createIndicatorOptions() {
		String[] periodMonthsValues = { "6", "12", "24", "48", "60" };
		HealthQualIndicatorOption period = new HealthQualIndicatorOption("periodMonths", "period", periodMonthsValues);

		// adult options
		options.put(IsantePlusReportsProperties.HEALTH_QUAL_RETENTION_OF_PATIENTS_ON_ART, period);
		// pediatric options
		options.put(IsantePlusReportsProperties.HEALTH_QUAL_CHILDREN_REGULARLY_FOLLOWED_ON_ART, period);
	}
}
