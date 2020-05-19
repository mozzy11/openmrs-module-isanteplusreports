package org.openmrs.module.isanteplusreports.pnlsReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.module.isanteplusreports.healthqual.model.HealthQualIndicator;
import org.openmrs.module.isanteplusreports.healthqual.model.HealthQualIndicatorOption;
import org.openmrs.module.isanteplusreports.pnlsReport.model.PnlsIndicatorOption;
import org.openmrs.module.isanteplusreports.pnlsReport.model.PnlsReportIndicator;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PnlsReportManager {
	
	private static final String[] indicatorUuids = { PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_UUID,
			                                         PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_UUID			                                         
	                                                };
	
	private Map<String, PnlsIndicatorOption> options = new HashMap<>();
	
	@Autowired
	private ReportDefinitionService reportDefinitionService;
	
	public List<PnlsReportIndicator> getPnlsIndicators() {
		return uuidToReportDefinition(Arrays.asList(indicatorUuids));
	}
	
	private List<PnlsReportIndicator> uuidToReportDefinition(List<String> uuids) {
		List<PnlsReportIndicator> indicators = new ArrayList<PnlsReportIndicator>();
		for (String uuid : uuids) {
			ReportDefinition reportDefinition = reportDefinitionService.getDefinitionByUuid(uuid);
			PnlsIndicatorOption option = options.get(uuid);
			if (reportDefinition != null) {
				indicators.add(new PnlsReportIndicator(reportDefinition, option));
			}
		}
		return indicators;
	}
	
}
