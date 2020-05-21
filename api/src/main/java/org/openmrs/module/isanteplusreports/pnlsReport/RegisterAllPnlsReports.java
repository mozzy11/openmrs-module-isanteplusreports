package org.openmrs.module.isanteplusreports.pnlsReport;

import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.reportDefinition;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.library.dimension.CommonDimension;
import org.openmrs.module.isanteplusreports.pnlsReport.utils.pnlsReportUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.report.definition.ReportDefinition;

public class RegisterAllPnlsReports {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private final static Parameter START_DATE = new Parameter("startDate", "isanteplusreports.parameters.startdate",
	        Date.class);
	
	private final static Parameter END_DATE = new Parameter("endDate", "isanteplusreports.parameters.enddate", Date.class);
	
	public static void registerAll() {
		newlyEnrolledPatientsOnArt();
		referredInPatientsOnArt();
		newlyEnrolledBreastFeedingOnArt();
		newlyRefferedInBreastFeedingOnArt();
		notEnrolledOnArtByReason() ;
		patientsBykeyPopulations();
		patientsUnderCtx();
		patientsEnrolledOnArtUnderTbTreatMent();
		patientsEnrolledOnArtUnderTbTreatMentFor6months();
		patientsEnrolledOnArtCompletedTbTreatMentFor6months();
		patientsEnrolledOnArtTbPostiveScrenneesFor6months();
		patientsEnrolledOnArtTbNegativeScrenneesFor6months();
		totalPatientsUnderPreventionCtx();
		totalPatientsUnderPreventionTbTreatment();
		totalPatientsUnderPreventionTbTreatmentForSixMonths();
		totalPatientsCompletedPreventionTbTreatment();
		totalPatientsWhoAreTbScrenees();
		totalPatientsWithBacteriologySpecimentSampleCollection();
		BreastFeedingNotYetOnArt();
		ArvPatientsWithSamplesSentForTbDiagnosisTest();
		totalPatientsWithPostiveResultsFromTbDiagnosis();
		totalArvPatientsNewlyPlacedUnderTbTreatment();
		patientsEnrolledOnArtAndTbTreatment();
		tbHivPatientsOnTbTreatment();
		tbHivPatientsOnTbTreamentDintergratedByNelyAndActivelyEnrolledOnArt();
		activeHivPatients();
		activeArvPatientsBykeyPopulations();
		activeArvPatientsOverMonths();
		activeArvPatientsByRegimeLines();
		totalArvPatientsInactiveAtTheEnd();
		inactiveArvPatientsDead();
		inactiveArvPatientsMedicalOrVountary();
		inactiveArvPatientsLostToForAMonth();
		inactiveArvPatientsLostToForMigrated();
		inactiveArvPatientsTransferred();
		totalArvPatientsLostToFollow();
		lostPatientsDied();
		lostPatientsAfterLessThan3MonthsTreatment();
		lostPatientsAfterMoreThan3MonthsTreatment();
		lostPatientsTransferred();
		lostPatientsStopped();
		deadArvPatientsByTuberclosis();
		deadArvPatientsByOtherInfectiousDiseases();
		deadArvPatientsByCancer();
		deadArvPatientsByHivIllnesses();
		deadArvPatientsByNaturalCauses();
		deadArvPatientsByUnNaturalCauses();
		deadArvPatientsByUnKnownCauses();
		lostArvPatientsResumedTreatment();
		lostArvPatientsResumedTreatmentBykeyPopulations();
		activeArvPatientsWithViralLoadResult();
		pregnantWomenOnArvWithViralLoadTest();
		breastFeedingWomenOnArvWithViralLoadTest();
		activeArvPatientsWithViralLoadBykeyPopulations();
		activeArvPatientsWithViralLoadResultLessThan1000Copies();
		pregnantWomenOnArvWithViralLoadResultsLessThan100Copies();
		breastFeedingWomenOnArvWithViralLoadResultsLessThan1000Copies();
		activeArvPatientsWithViralLoadLess100CopiesBykeyPopulations();
		activeArvPatientsWithViralLoadResultTargeted();
		pregnantWomenOnArvWithViralLoadTargeted();
		breastFeedingWomenOnArvWithViralLoadTargeted();
		activeArvPatientsWithViralLoadBykeyPopulationsTargeted();
		activeArvPatientsWithViralLoadResultTargetedLess1000Copies();
		pregnantWomenOnArvWithViralLoadTargetedLessThan1000Copies();
		breastFeedingWomenOnArvWithViralLoadTargetedLesstHan1000Copies();
		activeArvPatientsWithViralLoadLessThan1000CopiesBykeyPopulationsTargeted();
		activeArvPatientsFor12Months();
		activeArvPatientsFor12MonthsInSight();
		activeArvPatientsFor12MonthsTransferred();
		activeArvPatientsFor12MonthsAlive();
		womenOnArvScreenedCervicalCancer();
		womenScreenedForCervicalCancerFirstTime();
		womenScreenedForCervicalCancerAfterTestNegative();
		womenScreenedForCervicalCancerAfterTreatment();
		womenScreenedForCervicalCancePostive();
		totalArvPatientsRecomendedPf();
		hivPatientsAcceptingFamilyPlanning();
		hivPatientsUsingFamilyPlanning();
	}
	
	private static void newlyEnrolledPatientsOnArt() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_SQL,
		    PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_MESSAGE,
		    PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_UUID);
		
	}
	
	private static void referredInPatientsOnArt() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_SQL,
		    PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_MESSAGE,
		    PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_UUID);		
	}
	
	private static void tbHivPatientsOnTbTreatment() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.TB_HIV_PATIENTS_ON_TB_TREATMENT_SQL,
		    PnlsReportConstants.TB_HIV_PATIENTS_ON_TB_TREATMENT_MESSAGE,
		    PnlsReportConstants.TB_HIV_PATIENTS_ON_TB_TREATMENT_UUID);		
	}
	
	private static void inactiveArvPatientsDead() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.INACTIVE_ARV_PATIENTS_DEAD_SQL,
		    PnlsReportConstants.INACTIVE_ARV_PATIENTS_DEAD_MESSAGE,
		    PnlsReportConstants.INACTIVE_ARV_PATIENTS_DEAD_UUID);		
	}
	
	private static void inactiveArvPatientsMedicalOrVountary() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.INACTIVE_ARV_PATIENTS_MEDICAL_OR_VOLUNTARY_SQL ,
		    PnlsReportConstants.INACTIVE_ARV_PATIENTS_MEDICAL_OR_VOLUNTARY_MESSAGE ,
		    PnlsReportConstants.INACTIVE_ARV_PATIENTS_MEDICAL_OR_VOLUNTARY_UUID );		
	}
	
	
	private static void inactiveArvPatientsLostToForAMonth() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.INACTIVE_ARV_PATIENTS_LOST_TO_FOR_A_MONTH_SQL ,
		    PnlsReportConstants.INACTIVE_ARV_PATIENTS_LOST_TO_FOR_A_MONTH_MESSAGE,
		    PnlsReportConstants.INACTIVE_ARV_PATIENTS_LOST_TO_FOR_A_MONTH_UUID );		
	}
	
	private static void inactiveArvPatientsLostToForMigrated() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.INACTIVE_ARV_PATIENTS_MIGRATED_SQL ,
		    PnlsReportConstants.INACTIVE_ARV_PATIENTS_MIGRATED_MESSAGE,
		    PnlsReportConstants.INACTIVE_ARV_PATIENTS_MIGRATED_UUID );		
	}
	
	private static void inactiveArvPatientsTransferred() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.INACTIVE_ARV_PATIENTS_TRANSFERRED_SQL ,
		    PnlsReportConstants.INACTIVE_ARV_PATIENTS_TRANSFERRED_MESSAGE,
		    PnlsReportConstants.INACTIVE_ARV_PATIENTS_TRANSFERRED_UUID );		
	}
	
	private static void activeHivPatients() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_HIV_PATIENTS_SQL,
		    PnlsReportConstants.ACTIVE_HIV_PATIENTS_MESSAGE,
		    PnlsReportConstants.ACTIVE_HIV_PATIENTS_UUID);		
	}
	
	private static void lostPatientsDied() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.LOST_ARV_PATIENTS_DIED_SQL ,
		    PnlsReportConstants.LOST_ARV_PATIENTS_DIED_MESSAGE ,
		    PnlsReportConstants.LOST_ARV_PATIENTS_DIED_UUID);		
	}
	
	private static void lostPatientsAfterLessThan3MonthsTreatment() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_LESS_3MONTHS_SQL,
		    PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_LESS_3MONTHS_MESSAGE,
		    PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_LESS_3MONTHS_UUID);		
	}
	
	private static void lostPatientsAfterMoreThan3MonthsTreatment() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_MORE_3MONTHS_SQL ,
		    PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_MORE_3MONTHS_MESSAGE ,
		    PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_MORE_3MONTHS_UUID);		
	}
	
	private static void lostPatientsTransferred() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.LOST_ARV_PATIENTS_TRANSFERRED_SQL,
		    PnlsReportConstants.LOST_ARV_PATIENTS_TRANSFERRED_MESSAGE,
		    PnlsReportConstants.LOST_ARV_PATIENTS_TRANSFERRED_UUID);		
	}
	
	private static void lostPatientsStopped() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.LOST_ARV_PATIENTS_STOPPED_SQL,
		    PnlsReportConstants.LOST_ARV_PATIENTS_STOPPED_MESSAGE,
		    PnlsReportConstants.LOST_ARV_PATIENTS_STOPPED_UUID);		
	}
	
	private static void deadArvPatientsByTuberclosis() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.DEAD_ARV_PATIENTS_BY_TUBERCLOSIS_SQL,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_TUBERCLOSIS_MESSAGE,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_TUBERCLOSIS_UUID);		
	}
	
	private static void deadArvPatientsByOtherInfectiousDiseases() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.DEAD_ARV_PATIENTS_BY_OTHER_INFECTIOUS_DISEASES_SQL,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_OTHER_INFECTIOUS_DISEASES_MESSAGE,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_OTHER_INFECTIOUS_DISEASES_UUID);		
	}
	
	private static void deadArvPatientsByCancer() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.DEAD_ARV_PATIENTS_BY_CANCER_SQL,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_CANCER_MESSAGE ,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_CANCER_UUID);		
	}
	
	private static void deadArvPatientsByHivIllnesses() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.DEAD_ARV_PATIENTS_BY_HIV_ILLNESSES_SQL,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_HIV_ILLNESSES_MESSAGE ,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_HIV_ILLNESSES_UUID);		
	}
	
	private static void deadArvPatientsByNaturalCauses() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.DEAD_ARV_PATIENTS_BY_NATURAL_CAUSES_SQL,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_NATURAL_CAUSES_MESSAGE ,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_NATURAL_CAUSES_UUID);		
	}
	
	private static void deadArvPatientsByUnNaturalCauses() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNNATURAL_CAUSES_SQL,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNNATURAL_CAUSES_MESSAGE ,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNNATURAL_CAUSES_UUID);		
	}
	
	private static void deadArvPatientsByUnKnownCauses() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNKNOWN_CAUSES_SQL,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNKNOWN_CAUSES_MESSAGE ,
		    PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNKNOWN_CAUSES_UUID);		
	}
	
	private static void lostArvPatientsResumedTreatment() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_SQL,
		    PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_MESSAGE ,
		    PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_UUID);		
	}
	
	private static void activeArvPatientsWithViralLoadResult() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SQL,
		    PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_MESSAGE ,
		    PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_UUID);		
	}
	
	private static void activeArvPatientsWithViralLoadResultLessThan1000Copies() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_SQL,
		    PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_MESSAGE ,
		    PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_UUID);		
	}
	
	private static void activeArvPatientsWithViralLoadResultTargeted() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_SQL,
		    PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_MESSAGE  ,
		    PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_UUID);		
	}
	
	private static void activeArvPatientsWithViralLoadResultTargetedLess1000Copies() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_SQL,
		    PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_MESSAGE ,
		    PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_UUID);		
	}
	
	private static void activeArvPatientsFor12Months() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_SQL,
		    PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_MESSAGE ,
		    PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_UUID);		
	}
	
	private static void activeArvPatientsFor12MonthsInSight() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_IN_SIGHT_SQL,
		    PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_IN_SIGHT_MESSAGE ,
		    PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_IN_SIGHT_UUID);		
	}
	
	private static void activeArvPatientsFor12MonthsTransferred() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_TRANSFERRED_SQL,
		    PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_TRANSFERRED_MESSAGE,
		    PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_TRANSFERRED_UUID);		
	}
	

	private static void activeArvPatientsFor12MonthsAlive() {	
		registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
			PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_ALIVE_SQL,
		    PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_ALIVE_MESSAGE,
		    PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_ALIVE_UUID);		
	}
		
	private static void newlyEnrolledBreastFeedingOnArt() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.NEW_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_SQL,
			PnlsReportConstants.NEW_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_MESSAGE,
			PnlsReportConstants.NEW_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_UUID
			);
	}
	
	private static void newlyRefferedInBreastFeedingOnArt() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.NEW_REFERRED_IN_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_SQL,
			PnlsReportConstants.NEW_REFERRED_IN_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_MESSAGE,
			PnlsReportConstants.NEW_REFERRED_IN_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_UUID
			);
	}
	
	private static void BreastFeedingNotYetOnArt() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.BREAST_FEEDING_WOMEN_NOT_YET_ENROLED_ON_ART_SQL,
			PnlsReportConstants.BREAST_FEEDING_WOMEN_NOT_YET_ENROLED_ON_ART_MESSAGE,
			PnlsReportConstants.BREAST_FEEDING_WOMEN_NOT_YET_ENROLED_ON_ART_UUID
			);
	}
	
	private static void totalPatientsUnderPreventionCtx() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_CTX_SQL,
			PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_CTX_MESSAGE,
			PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_CTX_UUID
			);
	}
	
	private static void totalPatientsUnderPreventionTbTreatment() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_SQL,
			PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_MESSAGE,
			PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_UUID
			);
	}
	
	private static void totalPatientsUnderPreventionTbTreatmentForSixMonths() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_FOR_SIX_MONTHS_SQL,
			PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_FOR_SIX_MONTHS_MESSAGE,
			PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_FOR_SIX_MONTHS_UUID
			);
	}
	
	private static void totalPatientsCompletedPreventionTbTreatment() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.TOTAL_HIV_PATIENTS_COMPLETED_PREVENTION_TB_TREATMENT_SQL,
			PnlsReportConstants.TOTAL_HIV_PATIENTS_COMPLETED_PREVENTION_TB_TREATMENT_MESSAGE,
			PnlsReportConstants.TOTAL_HIV_PATIENTS_COMPLETED_PREVENTION_TB_TREATMENT_UUID
			);
	}
	
	
	private static void totalPatientsWhoAreTbScrenees() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.TOTAL_HIV_PATIENTS_TB_SCRENEES_SQL,
			PnlsReportConstants.TOTAL_HIV_PATIENTS_TB_SCRENEES_MESSAGE,
			PnlsReportConstants.TOTAL_HIV_PATIENTS_TB_SCRENEES_UUID
			);
	}
	
	private static void totalPatientsWithBacteriologySpecimentSampleCollection() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.TOTAL_HIV_PATIENTS_WITH_A_BACTERIOLOGY_SPECIMEN_COLLECTION_SQL,
			PnlsReportConstants.TOTAL_HIV_PATIENTS_WITH_A_BACTERIOLOGY_SPECIMEN_COLLECTION_MESSAGE,
			PnlsReportConstants.TOTAL_HIV_PATIENTS_WITH_A_BACTERIOLOGY_SPECIMEN_COLLECTION_UUID
			);
	}
	
	private static void totalPatientsWithPostiveResultsFromTbDiagnosis() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.TOTAL_ARV_PATIENTS_WITH_POSTIVE_RESULT_FROM_TB_DIAGNOSIS_SQL,
			PnlsReportConstants.TOTAL_ARV_PATIENTS_WITH_POSTIVE_RESULT_FROM_TB_DIAGNOSIS_MESSAGE,
			PnlsReportConstants.TOTAL_ARV_PATIENTS_WITH_POSTIVE_RESULT_FROM_TB_DIAGNOSIS_UUID
			);
	}
	
	private static void totalArvPatientsNewlyPlacedUnderTbTreatment() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.TOTAL_ARV_PATIENTS_NEWLY_PLACED_ON_TB_TREATMENT_SQL,
			PnlsReportConstants.TOTAL_ARV_PATIENTS_NEWLY_PLACED_ON_TB_TREATMENT_MESSAGE,
			PnlsReportConstants.TOTAL_ARV_PATIENTS_NEWLY_PLACED_ON_TB_TREATMENT_UUID
			);
	}
	
	private static void totalArvPatientsInactiveAtTheEnd() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AT_THE_END_SQL,
			PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AT_THE_END_MESSAGE,
			PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AT_THE_END_UUID
			);
	}
	
	private static void totalArvPatientsLostToFollow() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.TOTAL_ARV_PATIENTS_LOST_TO_FOLLOW_SQL,
			PnlsReportConstants.TOTAL_ARV_PATIENTS_LOST_TO_FOLLOW_MESSAGE,
			PnlsReportConstants.TOTAL_ARV_PATIENTS_LOST_TO_FOLLOW_UUID
			);
	}
	
	private static void totalArvPatientsRecomendedPf() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.TOTAL_ARV_PATIENTS_RECOMENDED_PF_SQL,
			PnlsReportConstants.TOTAL_ARV_PATIENTS_RECOMENDED_PF_MESSAGE,
			PnlsReportConstants.TOTAL_ARV_PATIENTS_RECOMENDED_PF_UUID
			);
	}
	
	private static void pregnantWomenOnArvWithViralLoadTest() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_SQL,
			PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_MESSAGE,
			PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_UUID
			);
	}
	
	private static void breastFeedingWomenOnArvWithViralLoadTest() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_SQL,
			PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_MESSAGE,
			PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_UUID
			);
	}
	
	private static void pregnantWomenOnArvWithViralLoadResultsLessThan100Copies() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_SQL,
			PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_MESSAGE,
			PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_UUID
			);
	}
	
	private static void breastFeedingWomenOnArvWithViralLoadResultsLessThan1000Copies() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_SQL,
			PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_MESSAGE,
			PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_UUID
			);
	}
	
	private static void pregnantWomenOnArvWithViralLoadTargeted() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_SQL,
			PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_MESSAGE,
			PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_UUID
			);
	}
	
	private static void breastFeedingWomenOnArvWithViralLoadTargeted() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_SQL,
			PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_MESSAGE,
			PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_UUID
			);
	}
	
	private static void pregnantWomenOnArvWithViralLoadTargetedLessThan1000Copies() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_SQL,
			PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_MESSAGE,
			PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_UUID
			);
	}
	
	private static void breastFeedingWomenOnArvWithViralLoadTargetedLesstHan1000Copies() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_SQL,
			PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_MESSAGE ,
			PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_UUID
			);
	}
	
	
	private static void womenOnArvScreenedCervicalCancer() {
		registerPnlsSingleColumnReportWithStartAndEndDateParams(
			PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_SQL ,
			PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_MESSAGE ,
			PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_UUID
			);
	}
	
	private static void notEnrolledOnArtByReason() {
		patientsNotEnrolledOnArtByReason(
			PnlsReportConstants.NOT_ENROLED_ON_ART_COHORT_INDICATOR_SQL,
			PnlsReportConstants.NOT_YET_ENROLED_ON_ART_BY_REASON_MESSAGE,
			PnlsReportConstants.NOT_YET_ENROLED_ON_ART_BY_REASON_UUID
			);
	}
	
	private static void patientsBykeyPopulations() {
		registerKeyPopulationReportWithStartAndEndDateParams(
			PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_SQL,
			PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_SQL,
			PnlsReportConstants.DISINTERGRATION_BY_KEY_POPULATION_MESSAGE,
			PnlsReportConstants.DISINTERGRATION_BY_KEY_POPULATION_UUID
		);
		
	}
	
	private static void activeArvPatientsBykeyPopulations() {
		registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_HIV_PATIENTS_SQL,
			PnlsReportConstants.ACTIVE_HIV_PATIENTS_BY_KEY_POPN_MESSAGE,
			PnlsReportConstants.ACTIVE_HIV_PATIENTS_BY_KEY_POPN_UUID
		);
		
	}
	
	private static void lostArvPatientsResumedTreatmentBykeyPopulations() {
		registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(
			PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_SQL,
			PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_KEY_POPULATION_MESSAGE,
			PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_KEY_POPULATION_UUID
		);
		
	}
	
	private static void activeArvPatientsWithViralLoadBykeyPopulations() {
		registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SQL,
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_KEY_POPULATION_MESSAGE,
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_KEY_POPULATION_UUID
		);
		
	}
	
	private static void activeArvPatientsWithViralLoadLess100CopiesBykeyPopulations() {
		registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_SQL,
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_BY_KEY_POPULATION_MESSAGE,
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_BY_KEY_POPULATION_UUID
		);
		
	}
	
	private static void activeArvPatientsWithViralLoadBykeyPopulationsTargeted() {
		registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_SQL,
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_BY_KEY_POPULATION_MESSAGE,
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_BY_KEY_POPULATION_UUID
		);
		
	}
	
	private static void activeArvPatientsWithViralLoadLessThan1000CopiesBykeyPopulationsTargeted() {
		registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_SQL ,
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_KEY_POPULATION_MESSAGE ,
			PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_KEY_POPULATION_UUID
		);
		
	}
	
	private static void patientsUnderCtx() {
	  registerPatientsUnderCtxWithStartAndEndDateParams(
			PnlsReportConstants.NEWLY_POWERED_CTX_COHORT_INDICATOR_SQL,
			PnlsReportConstants.ACTIVE_CTX_COHORT_INDICATOR_SQL,
			PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_CTX_MESSAGE,
			PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_CTX_UUID
		);		
	}
	
	private static void patientsEnrolledOnArtUnderTbTreatMent() {
		registerArvPatientsByAge15TreatmentWithStartAndEndDateParams(
				PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_STARTED_WITH_TB_TREATMENT_SQL,
				PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_STARTED_WITH_TB_TREATMENT_SQL,
				PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_MESSAGE,
				PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_UUID
			);		
		}
	
	private static void patientsEnrolledOnArtUnderTbTreatMentFor6months() {
		registerArvPatientsByAge15TreatmentWithStartAndEndDateParams(
				PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_STARTED_WITH_TB_TREATMENT_SQL,
				PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_STARTED_WITH_TB_TREATMENT_SQL,
				PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_6MONTHS_MESSAGE,
				PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_6MONTHS_UUID
			);		
		}
	
	
	private static void patientsEnrolledOnArtCompletedTbTreatMentFor6months() {
		registerArvPatientsByAge15TreatmentWithStartAndEndDateParams(
				PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_COMPLETED_TB_TREATMENT_SQL,
				PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_COMPLETED_TB_TREATMENT_SQL,
				PnlsReportConstants.PATIENTS_WHO_COMPLETED_TB_TREATMENT__MESSAGE,
				PnlsReportConstants.PATIENTS_WHO_COMPLETED_TB_TREATMENT_UUID
			);		
		}
	
	private static void patientsEnrolledOnArtTbPostiveScrenneesFor6months() {
		registerArvPatientsByAge15TreatmentWithStartAndEndDateParams(
				PnlsReportConstants.COHORT_NEWLY_ENROLLED_PATIENTS_ON_ART_SCRENEES_POSTIVE_SQL,
				PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_SCRENEES_POSTIVE_SQL,
				PnlsReportConstants.PATIENTS_ON_ART_SCRENEES_POSTIVE_MESSAGE,
				PnlsReportConstants.PATIENTS_ON_ART_SCRENEES_POSTIVE_UUID
			);		
		}
	
	private static void patientsEnrolledOnArtTbNegativeScrenneesFor6months() {
		registerArvPatientsByAge15TreatmentWithStartAndEndDateParams(
				PnlsReportConstants.COHORT_NEWLY_ENROLLED_PATIENTS_ON_ART_SCRENEES_NEGATIVE_SQL,
				PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_SCRENEES_NEGATIVE_SQL,
				PnlsReportConstants.PATIENTS_ON_ART_SCRENEES_NEGATIVE_MESSAGE,
				PnlsReportConstants.PATIENTS_ON_ART_SCRENEES_NEGATIVE_UUID
			);		
		}
	
	private static void patientsEnrolledOnArtAndTbTreatment() {
		registerArvPatientsByAge15TreatmentWithStartAndEndDateParams(
				PnlsReportConstants.NEWLYLY_ENROLLED_ARV_PATIENTS_AND_NEWLY_PLACED_ON_TB_TREATMENT_SQL,
				PnlsReportConstants.PATIENTS_ALREADY_ON_ARVS_AND_ALREADY_PLACED_ON_TB_TREATMENT_SQL,
				PnlsReportConstants.ARV_PATIENTS_NEWLY_PLACED_ON_TB_TREATMENT_MESSAGE,
				PnlsReportConstants.ARV_PATIENTS_NEWLY_PLACED_ON_TB_TREATMENT_UUID
			);		
		}
	
	private static void ArvPatientsWithSamplesSentForTbDiagnosisTest() {
		registerArvPatientsWithDiagnosisTestsWithStartAndEndDateParams(
				PnlsReportConstants.GENEEXPERT_MTB_DIAGNOSIS_TEST_SQL,
				PnlsReportConstants.OTHER_DIAGNOSIS_TEST_SQL,
				PnlsReportConstants.ARV_PATIENTS_WITH_SAMPLES_SENT_TO_DIAGNOSTIC_TEST_MESSAGE,
				PnlsReportConstants.ARV_PATIENTS_WITH_SAMPLES_SENT_TO_DIAGNOSTIC_TEST_UUID
			);		
		}
	
	private static void tbHivPatientsOnTbTreamentDintergratedByNelyAndActivelyEnrolledOnArt() {
		registerPnlsReportWith15By4ColumnsAndWithStartAndEndDateParams(
				PnlsReportConstants.TB_HIV_PATIENTS_NEWLY_ENROLLED_ON_ART_ON_TB_TREATMENT_SQL,
				PnlsReportConstants.TB_HIV_PATIENTS_ALREADY_ON_ART_ON_TB_TREATMENT_SQL,
				PnlsReportConstants.TB_HIV_PATIENTS_ON_TB_TREATMENT_DISTENGRATED_BY_NEW_AND_ALREADY_ON_ART_MESSAGE,
				PnlsReportConstants.TB_HIV_PATIENTS_ON_TB_TREATMENT_DISTENGRATED_BY_NEW_AND_ALREADY_ON_ART_UUID
			);		
		}
	
	private static void activeArvPatientsOverMonths() {
		registerArvActivePatientsBy15OverMonthsWithStartAndEndDateParams(
				PnlsReportConstants.ACTIVE_ARV_PATIENTS_LESS_3MONTHS_SQL,
				PnlsReportConstants.ACTIVE_ARV_PATIENTS_BTN_3_5MONTHS_SQL,
				PnlsReportConstants.ACTIVE_ARV_PATIENTS_OVER_5MONTHS_SQL,
				PnlsReportConstants.ACTIVE_ARV_PATIENTS_OVER_MONTHS_MESSAGE,
				PnlsReportConstants.ACTIVE_ARV_PATIENTS_OVER_MONTHS_UUID
			);		
		}
	
	private static void activeArvPatientsByRegimeLines() {
		registerActivePatientsWithRegimeLinesStartAndEndDateParams(
				PnlsReportConstants.ACTIVE_ARV_FIRST_LINE_REGIMEN_SQL,
				PnlsReportConstants.ACTIVE_ARV_SECOND_LINE_REGIMEN_SQL,
				PnlsReportConstants.ACTIVE_ARV_THIRD_LINE_REGIMEN_SQL,
				PnlsReportConstants.ACTIVE_ARV_PATIENTS_REGIME_LINES_MESSAGE,
				PnlsReportConstants.ACTIVE_ARV_PATIENTS_REGIME_LINES_UUID
			);		
		}
	
	private static void womenScreenedForCervicalCancerFirstTime() {	
		 registerWomenWithCervicalCancerStatusStartAndEndDateParams(
			PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_FIRST_TIME_SQL,
		    PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_FIRST_TIME_MESSAGE,
		    PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_FIRST_TIME_UUID);
		
	}
	
	private static void womenScreenedForCervicalCancerAfterTestNegative() {	
		 registerWomenWithCervicalCancerStatusStartAndEndDateParams(
			PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_FIRST_NEGATIVE_SQL,
		    PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_FIRST_NEGATIVE_MESSAGE,
		    PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_FIRST_NEGATIVE_UUID);
		
	}
	
	private static void womenScreenedForCervicalCancerAfterTreatment() {	
		 registerWomenWithCervicalCancerStatusStartAndEndDateParams(
			PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_TREATMENT_SQL,
		    PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_TREATMENT_MESSAGE,
		    PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_TREATMENT_UUID);
		
	}
	
	private static void womenScreenedForCervicalCancePostive() {	
		registerWomenWithCervicalCancerByTreatmentStartAndEndDateParams(
			PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_RECIEVED_CRYO_SQL,
			PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_RECIEVED_THERMO_SQL,
			PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_RECIEVED_LEEP_SQL,
		    PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_TESTED_POSTIVE_MESSAGE,
		    PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_TESTED_POSTIVE_UUID);
		
	}
	
	private static void hivPatientsAcceptingFamilyPlanning() {	
		   registerReportByFamilyPlaningMethodAndWithStartAndEndDateParams(
			PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_PILLS_SQL,
			PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_INJECT_SQL,
			PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_IMPLANTS_SQL,
		    PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_VAGTABS_SQL,
		    PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_LAM_SQL,
		    PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_NECKLACE_SQL,
			PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_CONDOM_SQL,
			PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_CCV_SQL,
		    PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_MESSAGE,
		    PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_UUID);
		
	}
	
	private static void hivPatientsUsingFamilyPlanning() {	
		   registerReportByFamilyPlaningMethodAndWithStartAndEndDateParams(
			PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_PILLS_SQL,
			PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_INJECT_SQL,
			PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_IMPLANTS_SQL,
		    PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_VAGTABS_SQL,
		    PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_LAM_SQL,
		    PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_NECKLACE_SQL,
			PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_CONDOM_SQL,
			PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_CCV_SQL,
		    PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_MESSAGE,
		    PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_UUID);
		
	}
	
	private static void registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(String sql, String messageProperties, String uuid) {
		
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName(messageProperties);
		dsd.addParameter(START_DATE);
		dsd.addParameter(END_DATE);
		
		dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
		dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));
		
		CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());
		
		pnlsReportUtils.addAgeAndGenderColumns(dsd, CohortIndicator, "M");
		pnlsReportUtils.addAgeAndGenderColumns(dsd, CohortIndicator, "F");
		pnlsReportUtils.addTotalColumns(dsd, CohortIndicator);
		
		Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_14BY3, uuid);
		repDefinition.addParameter(START_DATE);
		repDefinition.addParameter(END_DATE);
		repDefinition.addDataSetDefinition(dsd, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
	}
	
	private static void registerPnlsSingleColumnReportWithStartAndEndDateParams(String sql, String messageProperties, String uuid) {

		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName(messageProperties);
		dsd.addParameter(START_DATE);
		dsd.addParameter(END_DATE);

		CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());
		dsd.addColumn("BF","Single Column", Mapped.mapStraightThrough(CohortIndicator), "");

		Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_1BY1, uuid);
		repDefinition.addParameter(START_DATE);
		repDefinition.addParameter(END_DATE);
		repDefinition.addDataSetDefinition(dsd, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
	}
	

	private static void patientsNotEnrolledOnArtByReason(String sql, String messageProperties, String uuid) {

		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName(messageProperties);
		dsd.addParameter(START_DATE);
		dsd.addParameter(END_DATE);
 
		dsd.addDimension("enrolRsn", ReportUtils.map(new CommonDimension().notEnrollReason(), ""));
		dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
		dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));
		
		CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());
		pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "M","A", "DIED");
		pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "F","A","DIED");
		pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "M","B" ,"VOL");
		pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "F","B" ,"VOL");
		pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "M","C" ,"DENIAL");
		pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "F","C" ,"DENIAL");
		pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "M","D" ,"MED");
		pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "F","D" ,"MED");
		pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "M","E" ,"REF");
		pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "F","E" ,"REF");
		pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "M","F" ,"OTHER");
		pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "F","F" ,"OTHER");
		pnlsReportUtils.addTotalColumns(dsd, CohortIndicator);

		Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_14BY14 , uuid);
		repDefinition.addParameter(START_DATE);
		repDefinition.addParameter(END_DATE);
		repDefinition.addDataSetDefinition(dsd, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
	}
	
	
private static void registerKeyPopulationReportWithStartAndEndDateParams(String sqlNew,String sqlReferenced, String messageProperties, String uuid) {
		
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName(messageProperties);
		dsd.addParameter(START_DATE);
		dsd.addParameter(END_DATE);
		
		dsd.addDimension("keyPopn", ReportUtils.map(new CommonDimension().keyPopulationDimension(), ""));
		
		CohortIndicator CohortIndicatorNewlyEnrolled = pnlsReportUtils.cohortIndicatorFromSqlResource(sqlNew, "name", getParameters());
		CohortIndicator CohortIndicatorReferenced = pnlsReportUtils.cohortIndicatorFromSqlResource(sqlReferenced, "name", getParameters());
		
		pnlsReportUtils.addKeyPopulationColums(dsd,CohortIndicatorNewlyEnrolled ,"N");
		pnlsReportUtils.addKeyPopulationColums(dsd ,CohortIndicatorReferenced ,"R");
		
		Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_6BY3 , uuid);
		repDefinition.addParameter(START_DATE);
		repDefinition.addParameter(END_DATE);
		repDefinition.addDataSetDefinition(dsd, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
	}

private static void registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(String sql, String messageProperties, String uuid) {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName(messageProperties);
	dsd.addParameter(START_DATE);
	dsd.addParameter(END_DATE);
	
	dsd.addDimension("keyPopn", ReportUtils.map(new CommonDimension().keyPopulationDimension(), ""));
	
	CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());	
	pnlsReportUtils.addKeyPopulationSingleRowColums(dsd,CohortIndicator);
	
	Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
	Map<String, Object> mappings = new HashMap<String, Object>();
	mappings.put("startDate", "${startDate}");
	mappings.put("endDate", "${endDate}");
	ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_6BY2 , uuid);
	repDefinition.addParameter(START_DATE);
	repDefinition.addParameter(END_DATE);
	repDefinition.addDataSetDefinition(dsd, mappings);
	Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
}

private static void registerPatientsUnderCtxWithStartAndEndDateParams(String newlyPoweredCtxSql ,String activeOnCtxSql, String messageProperties, String uuid) {
	
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName(messageProperties);
	dsd.addParameter(START_DATE);
	dsd.addParameter(END_DATE);
	
	dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
	dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));
	
	CohortIndicator CohortIndicatorNewlyCtx = pnlsReportUtils.cohortIndicatorFromSqlResource(newlyPoweredCtxSql, "name", getParameters());
	CohortIndicator CohortIndicatorActiveCtx = pnlsReportUtils.cohortIndicatorFromSqlResource(activeOnCtxSql, "name", getParameters());
	
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorNewlyCtx ,"M" ,"N");
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorNewlyCtx ,"F", "N");
	pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorNewlyCtx, "N");
	
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorActiveCtx ,"M" ,"A");
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorActiveCtx ,"F","A");
	pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorActiveCtx, "A");
		
	Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
	Map<String, Object> mappings = new HashMap<String, Object>();
	mappings.put("startDate", "${startDate}");
	mappings.put("endDate", "${endDate}");
	ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_14BY6 , uuid);
	repDefinition.addParameter(START_DATE);
	repDefinition.addParameter(END_DATE);
	repDefinition.addDataSetDefinition(dsd, mappings);
	Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
}

private static void registerActivePatientsWithRegimeLinesStartAndEndDateParams(String regime1Sql ,String regime2Sql,String regime3Sql, String messageProperties, String uuid) {
	
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName(messageProperties);
	dsd.addParameter(START_DATE);
	dsd.addParameter(END_DATE);
	
	dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
	dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));
	
	CohortIndicator CohortIndicatorRegime1 = pnlsReportUtils.cohortIndicatorFromSqlResource(regime1Sql, "name", getParameters());
	CohortIndicator CohortIndicatorRegime2 = pnlsReportUtils.cohortIndicatorFromSqlResource(regime2Sql, "name", getParameters());
	CohortIndicator CohortIndicatorRegime3 = pnlsReportUtils.cohortIndicatorFromSqlResource(regime3Sql, "name", getParameters());
	
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorRegime1 ,"M" ,"1");
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorRegime1 ,"F", "1");
	pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorRegime1, "1");
	
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorRegime2 ,"M" ,"2");
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorRegime2,"F","2");
	pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorRegime2, "2");
	
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorRegime3 ,"M" ,"3");
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorRegime3,"F","3");
	pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorRegime3, "3");
		
		
	Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
	Map<String, Object> mappings = new HashMap<String, Object>();
	mappings.put("startDate", "${startDate}");
	mappings.put("endDate", "${endDate}");
	ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_14BY9, uuid);
	repDefinition.addParameter(START_DATE);
	repDefinition.addParameter(END_DATE);
	repDefinition.addDataSetDefinition(dsd, mappings);
	Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
}

private static void registerPnlsReportWith15By4ColumnsAndWithStartAndEndDateParams(String newlyEnrolledSql ,String activeOnArvSql, String messageProperties, String uuid) {
	
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName(messageProperties);
	dsd.addParameter(START_DATE);
	dsd.addParameter(END_DATE);
	
	dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
	dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));
	
	CohortIndicator CohortIndicatorNewlyEnrolled = pnlsReportUtils.cohortIndicatorFromSqlResource(newlyEnrolledSql, "name", getParameters());
	CohortIndicator CohortIndicatorActiveOnArv = pnlsReportUtils.cohortIndicatorFromSqlResource(activeOnArvSql, "name", getParameters());
	
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorNewlyEnrolled ,"M" ,"N");
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorNewlyEnrolled ,"F", "N");
	
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorActiveOnArv ,"M" ,"A");
	pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd,CohortIndicatorActiveOnArv ,"F","A");
		
	Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
	Map<String, Object> mappings = new HashMap<String, Object>();
	mappings.put("startDate", "${startDate}");
	mappings.put("endDate", "${endDate}");
	ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_14BY4 , uuid);
	repDefinition.addParameter(START_DATE);
	repDefinition.addParameter(END_DATE);
	repDefinition.addDataSetDefinition(dsd, mappings);
	Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
}


private static void registerArvPatientsByAge15TreatmentWithStartAndEndDateParams(String NewlyEnrolledSql ,String AlreadyOnArtSql, String messageProperties, String uuid) {
	
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName(messageProperties);
	dsd.addParameter(START_DATE);
	dsd.addParameter(END_DATE);
	
	dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
	dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZoneBy15(), "effectiveDate=${endDate}"));
	
	CohortIndicator CohortIndicatorNewlyEnrolled = pnlsReportUtils.cohortIndicatorFromSqlResource(NewlyEnrolledSql, "name", getParameters());
	CohortIndicator CohortIndicatorAlreadyOnArt = pnlsReportUtils.cohortIndicatorFromSqlResource(AlreadyOnArtSql, "name", getParameters());
	
	pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, CohortIndicatorNewlyEnrolled, "M", "A");
	pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, CohortIndicatorNewlyEnrolled, "F", "A");
	pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, CohortIndicatorAlreadyOnArt, "M", "B");
	pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, CohortIndicatorAlreadyOnArt, "F", "B");
		
	Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
	Map<String, Object> mappings = new HashMap<String, Object>();
	mappings.put("startDate", "${startDate}");
	mappings.put("endDate", "${endDate}");
	ReportDefinition repDefinition = reportDefinition(messageProperties,PnlsReportConstants.REPORT_DESCRIPTION_4BY5 , uuid);
	repDefinition.addParameter(START_DATE);
	repDefinition.addParameter(END_DATE);
	repDefinition.addDataSetDefinition(dsd, mappings);
	Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
}

private static void registerArvActivePatientsBy15OverMonthsWithStartAndEndDateParams(String activeLess3MonthsSql ,String activeBtn3_5MonthsSql, String activeOver5MonthsSql, String messageProperties, String uuid) {
	
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName(messageProperties);
	dsd.addParameter(START_DATE);
	dsd.addParameter(END_DATE);
	
	dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
	dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZoneBy15(), "effectiveDate=${endDate}"));
	
	CohortIndicator activeLess3Months = pnlsReportUtils.cohortIndicatorFromSqlResource(activeLess3MonthsSql, "name", getParameters());
	CohortIndicator activeBtn3_5Months = pnlsReportUtils.cohortIndicatorFromSqlResource(activeBtn3_5MonthsSql, "name", getParameters());
	CohortIndicator activeOver5Months = pnlsReportUtils.cohortIndicatorFromSqlResource(activeOver5MonthsSql, "name", getParameters());
	
	pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, activeLess3Months, "M", "A");
	pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, activeLess3Months, "F", "A");
	pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, activeBtn3_5Months, "M", "B");
	pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, activeBtn3_5Months, "F", "B");
	pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, activeOver5Months, "M", "C");
	pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, activeOver5Months, "F", "C");
		
	Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
	Map<String, Object> mappings = new HashMap<String, Object>();
	mappings.put("startDate", "${startDate}");
	mappings.put("endDate", "${endDate}");
	ReportDefinition repDefinition = reportDefinition(messageProperties,PnlsReportConstants.REPORT_DESCRIPTION_4BY7 , uuid);
	repDefinition.addParameter(START_DATE);
	repDefinition.addParameter(END_DATE);
	repDefinition.addDataSetDefinition(dsd, mappings);
	Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
}

private static void registerArvPatientsWithDiagnosisTestsWithStartAndEndDateParams(String genexpertMtbSql ,String otherTbDiagnosisTestsSql, String messageProperties, String uuid) {
	
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName(messageProperties);
	dsd.addParameter(START_DATE);
	dsd.addParameter(END_DATE);
	
	
	CohortIndicator CohortIndicatorGeneExpertTest = pnlsReportUtils.cohortIndicatorFromSqlResource( genexpertMtbSql, "name", getParameters());
	CohortIndicator CohortIndicatorOtherTest = pnlsReportUtils.cohortIndicatorFromSqlResource(otherTbDiagnosisTestsSql, "name", getParameters());
	
	dsd.addColumn("GE", "GeneExpert MTB Diagnosis Tests", Mapped.mapStraightThrough(CohortIndicatorGeneExpertTest), "");
	dsd.addColumn("OT", "Other Diagnosis Tests", Mapped.mapStraightThrough(CohortIndicatorOtherTest), "");
		
	Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
	Map<String, Object> mappings = new HashMap<String, Object>();
	mappings.put("startDate", "${startDate}");
	mappings.put("endDate", "${endDate}");
	ReportDefinition repDefinition = reportDefinition(messageProperties,PnlsReportConstants.REPORT_DESCRIPTION_3BY1 , uuid);
	repDefinition.addParameter(START_DATE);
	repDefinition.addParameter(END_DATE);
	repDefinition.addDataSetDefinition(dsd, mappings);
	Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
}

private static void registerWomenWithCervicalCancerStatusStartAndEndDateParams(String sql, String messageProperties, String uuid) {
	
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName(messageProperties);
	dsd.addParameter(START_DATE);
	dsd.addParameter(END_DATE);
	
	dsd.addDimension("cervStat", ReportUtils.map(new CommonDimension().CervicalCancerStatusDimension(), DATE_PARAMS));
	dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));
	
	CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());
	
	pnlsReportUtils.addCervicalCancerStatusColumns(dsd, CohortIndicator, "NEG");
	pnlsReportUtils.addCervicalCancerStatusColumns(dsd, CohortIndicator, "POS");
	pnlsReportUtils.addCervicalCancerStatusColumns(dsd, CohortIndicator, "SUS");
	pnlsReportUtils.addCervicalTotalCancerStatusColumns(dsd, CohortIndicator);
	
	Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
	Map<String, Object> mappings = new HashMap<String, Object>();
	mappings.put("startDate", "${startDate}");
	mappings.put("endDate", "${endDate}");
	ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_10BY4, uuid);
	repDefinition.addParameter(START_DATE);
	repDefinition.addParameter(END_DATE);
	repDefinition.addDataSetDefinition(dsd, mappings);
	Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
}

private static void registerWomenWithCervicalCancerByTreatmentStartAndEndDateParams(String treatment1sql, String treatment2sql ,String treatment3sql , String messageProperties, String uuid) {
	
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName(messageProperties);
	dsd.addParameter(START_DATE);
	dsd.addParameter(END_DATE);
	
	dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));
	
	CohortIndicator CohortIndicator1 = pnlsReportUtils.cohortIndicatorFromSqlResource(treatment1sql, "name", getParameters());
	CohortIndicator CohortIndicator2 = pnlsReportUtils.cohortIndicatorFromSqlResource(treatment2sql, "name", getParameters());
	CohortIndicator CohortIndicator3 = pnlsReportUtils.cohortIndicatorFromSqlResource(treatment3sql, "name", getParameters());
	
	pnlsReportUtils.addCervicalCancerbyAgeColumns(dsd, CohortIndicator1, "1");
	pnlsReportUtils.addCervicalCancerbyAgeColumns(dsd, CohortIndicator2, "2");
	pnlsReportUtils.addCervicalCancerbyAgeColumns(dsd, CohortIndicator3, "3");

	
	Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
	Map<String, Object> mappings = new HashMap<String, Object>();
	mappings.put("startDate", "${startDate}");
	mappings.put("endDate", "${endDate}");
	ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_10BY4_II, uuid);
	repDefinition.addParameter(START_DATE);
	repDefinition.addParameter(END_DATE);
	repDefinition.addDataSetDefinition(dsd, mappings);
	Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
} 
  
private static void registerReportByFamilyPlaningMethodAndWithStartAndEndDateParams(
		                                                              String pillssSql ,
		                                                              String injectSql,
		                                                              String implantSql,
		                                                              String vagTabsSql,
		                                                              String lamSql,
		                                                              String necklaceSql,
		                                                              String condomSql,
		                                                              String ccvSql,
		                                                              String messageProperties,
		                                                              String uuid) {
	
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName(messageProperties);
	dsd.addParameter(START_DATE);
	dsd.addParameter(END_DATE);
	
	dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
	dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));
	
	CohortIndicator CohortIndicatorPills = pnlsReportUtils.cohortIndicatorFromSqlResource(pillssSql, "name", getParameters());
	CohortIndicator CohortIndicatorInject = pnlsReportUtils.cohortIndicatorFromSqlResource( injectSql, "name", getParameters());
	CohortIndicator CohortIndicatorImplant = pnlsReportUtils.cohortIndicatorFromSqlResource(implantSql, "name", getParameters());
	CohortIndicator CohortIndicatorVagtabs = pnlsReportUtils.cohortIndicatorFromSqlResource(vagTabsSql, "name", getParameters());
	CohortIndicator CohortIndicatorLam = pnlsReportUtils.cohortIndicatorFromSqlResource(lamSql, "name", getParameters());
	CohortIndicator CohortIndicatorNecklace = pnlsReportUtils.cohortIndicatorFromSqlResource(necklaceSql, "name", getParameters());
	CohortIndicator CohortIndicatorCondom = pnlsReportUtils.cohortIndicatorFromSqlResource(condomSql, "name", getParameters());
	CohortIndicator CohortIndicatorCcv = pnlsReportUtils.cohortIndicatorFromSqlResource(ccvSql, "name", getParameters());
	
	pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd,CohortIndicatorPills,"F" ,"A");
	pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd,CohortIndicatorInject,"F" ,"B");
	pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd,CohortIndicatorImplant,"F" ,"C");
	pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd,CohortIndicatorVagtabs,"F" ,"D");
	pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd,CohortIndicatorLam,"F" ,"E");
	pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd,CohortIndicatorNecklace,"F" ,"F");
	pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd,CohortIndicatorCondom,"M" ,"G");
	pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd,CohortIndicatorCondom,"F" ,"H");
	pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd,CohortIndicatorCcv,"M" ,"I");
	pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd,CohortIndicatorCcv,"F" ,"J");

		
	Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
	Map<String, Object> mappings = new HashMap<String, Object>();
	mappings.put("startDate", "${startDate}");
	mappings.put("endDate", "${endDate}");
	ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_10BY11 , uuid);
	repDefinition.addParameter(START_DATE);
	repDefinition.addParameter(END_DATE);
	repDefinition.addDataSetDefinition(dsd, mappings);
	Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);		
}
	
	public static List<Parameter> getParameters() {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class),
		    new Parameter("endDate", "End Date", Date.class));
	}
	
	private static final String DATE_PARAMS = "startDate=${startDate},endDate=${endDate}";
	
}
