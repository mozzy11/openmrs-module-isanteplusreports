package org.openmrs.module.isanteplusreports.pnlsReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.module.isanteplusreports.healthqual.model.HealthQualIndicator;
import org.openmrs.module.isanteplusreports.healthqual.model.HealthQualIndicatorOption;
import org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants;
import org.openmrs.module.isanteplusreports.pnlsReport.model.PnlsIndicatorOption;
import org.openmrs.module.isanteplusreports.pnlsReport.model.PnlsReportIndicator;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PnlsReportManager {
	
	private static final String[] indicatorUuids = { PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_UUID,
			                                         PnlsReportConstants.NEW_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_UUID,
			                                         PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_UUID,	
			                                         PnlsReportConstants.NEW_REFERRED_IN_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_UUID,
			                                         PnlsReportConstants.DISINTERGRATION_BY_KEY_POPULATION_UUID,
			                                         PnlsReportConstants.NOT_YET_ENROLED_ON_ART_BY_REASON_UUID,	
			                                         PnlsReportConstants.BREAST_FEEDING_WOMEN_NOT_YET_ENROLED_ON_ART_UUID,
			                                         PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_CTX_UUID,
			                                         PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_CTX_UUID,
			                                         PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_UUID,
			                                         PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_UUID,
			                                         PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_FOR_SIX_MONTHS_UUID,
			                                         PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_6MONTHS_UUID,
			                                         PnlsReportConstants.TOTAL_HIV_PATIENTS_COMPLETED_PREVENTION_TB_TREATMENT_UUID,
			                                         PnlsReportConstants.PATIENTS_WHO_COMPLETED_TB_TREATMENT_UUID,
			                                         PnlsReportConstants.TOTAL_HIV_PATIENTS_TB_SCRENEES_UUID,
			                                         PnlsReportConstants.PATIENTS_ON_ART_SCRENEES_POSTIVE_UUID,
			                                         PnlsReportConstants.PATIENTS_ON_ART_SCRENEES_NEGATIVE_UUID,
			                                         PnlsReportConstants.TOTAL_HIV_PATIENTS_WITH_A_BACTERIOLOGY_SPECIMEN_COLLECTION_UUID,
			                                         PnlsReportConstants.ARV_PATIENTS_WITH_SAMPLES_SENT_TO_DIAGNOSTIC_TEST_UUID,
			                                         PnlsReportConstants.TOTAL_ARV_PATIENTS_WITH_POSTIVE_RESULT_FROM_TB_DIAGNOSIS_UUID,
			                                         PnlsReportConstants.TOTAL_ARV_PATIENTS_NEWLY_PLACED_ON_TB_TREATMENT_UUID,
			                                         PnlsReportConstants.ARV_PATIENTS_NEWLY_PLACED_ON_TB_TREATMENT_UUID,
			                                         PnlsReportConstants.TB_HIV_PATIENTS_ON_TB_TREATMENT_UUID,
			                                         PnlsReportConstants.TB_HIV_PATIENTS_ON_TB_TREATMENT_DISTENGRATED_BY_NEW_AND_ALREADY_ON_ART_UUID,
			                                         PnlsReportConstants.ACTIVE_HIV_PATIENTS_UUID,
			                                         PnlsReportConstants.ACTIVE_HIV_PATIENTS_BY_KEY_POPN_UUID,
			                                         PnlsReportConstants.ACTIVE_ARV_PATIENTS_OVER_MONTHS_UUID,
			                                         PnlsReportConstants.ACTIVE_ARV_PATIENTS_REGIME_LINES_UUID,
			                                         PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AT_THE_END_UUID,
			                                         PnlsReportConstants.INACTIVE_ARV_PATIENTS_DEAD_UUID,
			                                         PnlsReportConstants.INACTIVE_ARV_PATIENTS_MEDICAL_OR_VOLUNTARY_UUID,
			                                         PnlsReportConstants.INACTIVE_ARV_PATIENTS_LOST_TO_FOR_A_MONTH_UUID,
			                                         PnlsReportConstants.INACTIVE_ARV_PATIENTS_MIGRATED_UUID ,
			                                         PnlsReportConstants.INACTIVE_ARV_PATIENTS_TRANSFERRED_UUID,
			                                         PnlsReportConstants.TOTAL_ARV_PATIENTS_LOST_TO_FOLLOW_UUID,
			                                         PnlsReportConstants.LOST_ARV_PATIENTS_DIED_UUID,
			                                         PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_LESS_3MONTHS_UUID,
			                                         PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_MORE_3MONTHS_UUID,
			                                         PnlsReportConstants.LOST_ARV_PATIENTS_TRANSFERRED_UUID,
			                                         PnlsReportConstants.LOST_ARV_PATIENTS_STOPPED_UUID,
			                                         PnlsReportConstants.DEAD_ARV_PATIENTS_BY_TUBERCLOSIS_UUID,
			                                         PnlsReportConstants.DEAD_ARV_PATIENTS_BY_OTHER_INFECTIOUS_DISEASES_UUID,
			                                         PnlsReportConstants.DEAD_ARV_PATIENTS_BY_CANCER_UUID,
			                                         PnlsReportConstants.DEAD_ARV_PATIENTS_BY_HIV_ILLNESSES_UUID,
			                                         PnlsReportConstants.DEAD_ARV_PATIENTS_BY_NATURAL_CAUSES_UUID,
			                                         PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNNATURAL_CAUSES_UUID,
			                                         PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNKNOWN_CAUSES_UUID,
			                                         PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_UUID,
			                                         PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_KEY_POPULATION_UUID,
			                                         PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_UUID,
			                                         PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_UUID,
			                                         PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_UUID,
			                                         PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_KEY_POPULATION_UUID,
			                                         PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_UUID,
			                                         PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_UUID,
			                                         PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_UUID,
			                                         PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_BY_KEY_POPULATION_UUID,
			                                         PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_UUID,
			                                         PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_UUID,
			                                         PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_UUID,
			                                         PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_BY_KEY_POPULATION_UUID,
			                                         PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_UUID,
			                                         PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_UUID,
			                                         PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_UUID,
			                                         PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_KEY_POPULATION_UUID,
			                                         PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_UUID,
			                                         PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_IN_SIGHT_UUID,
			                                         PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_TRANSFERRED_UUID,
			                                         PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_ALIVE_UUID,
			                                         PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_UUID,
			                                         PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_FIRST_TIME_UUID,
			                                         PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_FIRST_NEGATIVE_UUID,
			                                         PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_TREATMENT_UUID,
			                                         PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_TESTED_POSTIVE_UUID,
			                                         PnlsReportConstants.TOTAL_ARV_PATIENTS_RECOMENDED_PF_UUID ,
			                                         PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_UUID,
			                                         PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_UUID,
			                                         PnlsReportConstants.ACTIVE_PATIENTS_WITH_ATLEST_FOLLOW_UP_VIST_UUID ,
			                                         PnlsReportConstants.ACTIVE_PATIENTS_6_MONTHS_INH_UUID
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
