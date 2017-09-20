package org.openmrs.module.isanteplusreports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.isanteplusreports.util.RegisterAllReports;

public class RegisterReports {
	
	private static Log log = LogFactory.getLog(RegisterReportsTask.class);
	
	/**
	 * Does the actual data aggregation
	 */
	//protected void onExecute() {
	public void registerReports() {
		try {
			
			String version = ModuleFactory.getModuleById("isanteplusreports").getVersion();
			String oldversion = Context.getAdministrationService().getGlobalProperty("reports.moduleVersion");

			RegisterAllReports register = new RegisterAllReports();
			register.cleanTables();
			register.cleanReportsRequest();
			register.nextVisitSevenDays();
			register.nextVisitFourteenDays();
			register.patientAgeGroup();
			register.firstVisitAge();
			register.patientsCrachatAnormalWithoutTbDiagnostic();
			register.patientsWithTbDiagnosticsWithoutTreatment();
			register.patientsWithCompletedTbTreatment();
			register.patientsWithTbSymptomsignWithoutCrachat();
			register.consultationByDay();
			register.numberVisitsByMonth();
			register.numberPatientBySex();
			register.dispensingMedications();
			register.patientsStatusList();
			register.patientsReceivingARVByPeriod();
			register.drugsPrescription();
			register.labPrescription();
			register.labPerfomed();
			register.patientArvThirtyDay();
			register.patientStartingArv();
			register.patientNextArvArrives();
			register.institutionFrequentingByUser();
			register.institutionFrequentingByUserAndDate();
			register.institutionFrequenting();
			register.institutionFrequentingByDate();
			register.saveAlertReport();
			register.patientWithOnlyRegisterForm();
			register.hivPatientWithoutFirstVisit();
			register.hivPatientWithActivityAfterDisc();
			register.numberPregnancyWomenHivTested();
			register.numberPregnancyWomenHivPositive();
			register.communityArvDistribution();
			register.pregnancyWomenOnHaart();
			register.pregnancyWomenTestedForSyphilis();
			register.pregnancyWomenDiagnosedWithSyphilis();
			register.numberPrenatalVisitBySite();
			register.numberPregnancyWomenHadFirstPrenatalCons();
			register.numberHivPregnancyWomenGaveBirthAtHospital();
			register.exposedInfantsWithMotherInPtmeProgram();
			register.numberInfantsFromMotherOnProphylaxis();
			register.frequencyPrenatalVisitsPerPatient();
			register.numberExposedInfantsTestedByPcr();
			register.numberExposedInfantsConfirmedHiv();
			register.numberWomenFirstPrenatalVisitFirstTrimester();
			register.numberPatientsByArvStatus();
			register.hivTransmissionRisksFactor();
			register.numberVisitsByPregnantWomenToClinic();
			register.ListVisitsByPregnantWomenToClinic();
			Context.getAdministrationService().saveGlobalProperty(new GlobalProperty("reports.moduleVersion", version));

			register.healthQualRetentionOfPatientsOnArt();
			register.healthQualCd4AssessmentAtEnrolment();
			register.healthQualArvEnrollment();
			register.healthQualAdultHivAndCortimixazoleProphy();
			register.healthQualAdultPlhivAndInh();
			register.healthQualAdultHivOnArtAdherentToTreatment();
			register.healthQualAdultHivOnArtWithAdherentEvaluation();
			register.healthQualProportionOfHIVPregnantWithHAART();
			register.healthQualProportionOfHIVPatientsWithNutritionalAssessment();
			register.healthQualProportionOfUndernourishedHIVPatients();
			register.healthQualProportionOfHivWomenUsingFamilyPlanning();
			register.healthQualProportionOfPregnantWithPrenataleCareOrLD();
			register.healthQualProportionOfHivOnArtViralLoadTest6Months();
			register.healthQualProportionOfHivOnArtViralLoadTest18Months();
			register.healthQualProportionOfHivOnArtUndetectableViralLoadTest6Months();
			register.healthQualProportionOfPLHIVTestedForTB();

			register.healthQualChildrenRegularlyFollowedOnArt();
			register.healthQualChildrenHivAndPlacedOnArt();
			register.healthQualPediatricHivAndReceivedCotrimoxazoleProphylaxis();
			register.healthQualProportionOfHIVChildrenTestedForTB();
			register.healthQualChildrenOnArtWhoAreConsideredAdherent();
			register.healthQualPediatricHivAndArtProphy();
			register.healthQualPediatricReceivedPcrTest();
			register.healthQualPediatricNegativePcrTest();
			register.healthQualPediatricBenefitedFromAnAdherence();
			register.healthQualProportionOfChildrenWithNutritionalAssessment();
			register.healthQualProportionOfHIVChildrenOlderThanOneYearOfAgeReceivedINH();
			register.healthQualProportionOfHIVChildrenYoungerThanOneYearOfAgeReceivedINH();
		}
		catch (Exception ex) {
			log.error("One of reports has an error which blocks it and other reports to be registered");
			ex.printStackTrace();
		}
	}
}
