package org.openmrs.module.isanteplusreports.util;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.context.SessionContext;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.ReportRequest.Status;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.service.ReportService;

public class RegisterAllOtherReports extends SessionContext {
	
	private static Log log = LogFactory.getLog(RegisterAllOtherReports.class);
	
	public static void registerOtherReports(){
		cleanTables();
		cleanReportsRequest();
		nextVisitSevenDays();
		nextVisitFourteenDays();
		patientAgeGroup();
		firstVisitAge();
		patientsCrachatAnormalWithoutTbDiagnostic();
		patientsWithTbDiagnosticsWithoutTreatment();
		patientsWithCompletedTbTreatment();
		patientsWithTbSymptomsignWithoutCrachat();
		consultationByDay();
		numberVisitsByMonth();
		numberPatientBySex();
		dispensingMedications();
		patientsStatusList();
		patientsReceivingARVByPeriod();
		drugsPrescription();
		labPrescription();
		labPerfomed();
		patientArvThirtyDay();
		patientStartingArv();
		patientNextArvArrives();
		institutionFrequentingByUser();
		institutionFrequentingByUserAndDate();
		institutionFrequenting();
		institutionFrequentingByDate();
		saveAlertReport();
		patientWithOnlyRegisterForm();
		hivPatientWithoutFirstVisit();
		hivPatientWithActivityAfterDisc();
		numberPregnancyWomenHivTested();
		numberPregnancyWomenHivPositive();
		communityArvDistribution();
		pregnancyWomenOnHaart();
		pregnancyWomenTestedForSyphilis();
		pregnancyWomenDiagnosedWithSyphilis();
		numberPrenatalVisitBySite();
		numberPregnancyWomenHadFirstPrenatalCons();
		numberHivPregnancyWomenGaveBirthAtHospital();
		exposedInfantsWithMotherInPtmeProgram();
		numberInfantsFromMotherOnProphylaxis();
		frequencyPrenatalVisitsPerPatient();
		numberExposedInfantsTestedByPcr();
		numberExposedInfantsConfirmedHiv();
		numberWomenFirstPrenatalVisitFirstTrimester();
		hivTransmissionRisksFactor();
		numberVisitsByPregnantWomenToClinic();
		listVisitsByPregnantWomenToClinic();
		listPatientsReceivingArtInCommunity();
		listPatientsReceivingArtInHospital();
		listPatientsBeneficiePcr();
		numberPatientsBeneficiePcr();
		listEligibleChildrenPcr();
		numberEligibleChildrenPcr();
		numberChargeViraleByResultDate();
		listPatChargeViraleMoinsMilleByResultDate();
		listPatChargeViralePlusMilleByResultDate();
		numberChargeViraleByDemandDate();
		listPatChargeViraleMoinsMilleByDemandDate();
		listPatChargeViralePlusMilleByDemandDate();
		listOfLabOrders();
	}
	
	
	private static void cleanTables() {
		List<DataSetDefinition> defService = Context.getService(DataSetDefinitionService.class).getAllDefinitions(true);
		for (DataSetDefinition dataSetDef : defService) {
			//Context.getService(DataSetDefinitionService.class).purgeDefinition(dataSetDef);
			Context.getService(SerializedDefinitionService.class).purgeDefinition(dataSetDef);
			
		}
		ReportService rs = Context.getService(ReportService.class);
		List<ReportDesign> rDes = rs.getAllReportDesigns(true);
		for (ReportDesign reportDesign : rDes) {
			rs.purgeReportDesign(reportDesign);
			
		}
		
		ReportDefinitionService rds = Context.getService(ReportDefinitionService.class);
		List<ReportDefinition> rDefs = rds.getAllDefinitions(true);
		for (ReportDefinition reportDefinition : rDefs) {
			//rds.purgeDefinition(reportDefinition);
			Context.getService(SerializedDefinitionService.class).purgeDefinition(reportDefinition);
		}
		for (ReportRequest request : rs.getReportRequests(null, null, null, Status.COMPLETED, Status.FAILED)) {
			try {
				rs.purgeReportRequest(request);
			}
			catch (Exception e) {
				log.warn("Unable to delete old report request: " + request, e);
			}
		}
	}
	
	public static void cleanReportsRequest() {
		ReportService rs = Context.getService(ReportService.class);
		for (ReportRequest request : rs.getReportRequests(null, null, null, Status.COMPLETED, Status.FAILED)) {
			try {
				rs.purgeReportRequest(request);
			}
			catch (Exception e) {
				log.warn("Unable to delete old report request: " + request, e);
			}
		}
	}
	
	private static void nextVisitSevenDays(){
		IsantePlusReportsUtil.registerReportsWithoutParams("visitNextSevenDays.sql","isanteplusreports.septJoursLibelle",
		       "Patient avec Rendez-Vous programmé dans les 7 jours à venir", IsantePlusReportsProperties.SEVEN_DAYS_REPORT_DEFINITION_UUID);
	
	}
	private static void nextVisitFourteenDays(){
		IsantePlusReportsUtil.registerReportsWithoutParams("visitNextFourteenDays.sql","isanteplusreports.quatorzeJoursLibelle",
			       "Patient avec Rendez-Vous programmé dans les 14 jours à venir", IsantePlusReportsProperties.FOURTEEN_DAYS_REPORT_DEFINITION_UUID);
	}
	private static void patientAgeGroup(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParamsOtherRenderer("patientAgeGroup.sql","isanteplusreports.patByAge",
			       "Patient par groupe d'âge", IsantePlusReportsProperties.PATIENT_AGE_GROUP_REPORT_DEFINITION_UUID);
	}
	
	private static void firstVisitAge(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParamsOtherRenderer("firstVisitAge.sql","isanteplusreports.firstVisitAge",
			       "Âge à la première visite", IsantePlusReportsProperties.FIRSTVISITAGE);
	}
	
	private static void patientsCrachatAnormalWithoutTbDiagnostic(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patientsCrachatAnormalWithoutTbDiagnostic.sql","isanteplusreports.patientsCrachatWithoutTbDiagnostic",
		           "Patients avec analyses de crachats ou radiographie pulmonaire anormal mais, sans aucun diagnostic TB", IsantePlusReportsProperties.PATIENTCRACHATANORMALWITHOUTTBDIAGNOSTIC);
	}
	
	private static void patientsWithCompletedTbTreatment() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patientWithCompleteTbTreatment.sql","isanteplusreports.patientsWithCompletedTbTreatment",
		           "Patients avec traitement contre la TB complété", IsantePlusReportsProperties.PATIENTWITHCOMPLETEDTBTREATMENT);
	}
	
	private static void patientsWithTbDiagnosticsWithoutTreatment() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patientsWithTbDiagnosticWithoutTreatment.sql","isanteplusreports.patientsWithTbDiagnosticWithoutTreatment",
		           "Patients avec des diagnostics de TB, mais sans traitement", IsantePlusReportsProperties.PATIENTSWITHTBDIAGNOSTICWITHOUTTREATMENT);
	}
	
	private static void patientsWithTbSymptomsignWithoutCrachat(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patientsWithTbSymptomWithoutCrachat.sql","isanteplusreports.patientsWithTbDiagnosticWithoutCrachat",
		           "Patients avec signes et symptômes suggérant la TB, mais sans analyse des crachats ou radiographie pulmonaires", IsantePlusReportsProperties.PATIENTSWITHTBDIAGNOSTICWITHOUTCRACHAT);
	}
	
	private static void consultationByDay(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateAndLocationParams("consultationByDay.sql","isanteplusreports.consultationByDay",
		           "Consultation par jour", IsantePlusReportsProperties.CONSULTATIONBYDAY);
		
	}
	
	private static void numberVisitsByMonth(){
		IsantePlusReportsUtil.registerReportsWithoutParams("numberVisitsByMonth.sql","isanteplusreports.numberVisitByMonth",
		      "Nombre de visites par mois", IsantePlusReportsProperties.NUMBERVISITSBYMONTH);
		
	}
	
	private static void numberPatientBySex() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParamsOtherRenderer("numberPatientsBySex.sql","isanteplusreports.numberPatientBySex",
		           "Nombre de patients par sexe", IsantePlusReportsProperties.NUMBERPATIENTBYSEX);
	}
	
	private static void dispensingMedications(){
		IsantePlusReportsUtil.registerReportsWithoutParams("dispensingMedications.sql","isanteplusreports.dispensingMedications",
			      "Médicaments Dispensés", IsantePlusReportsProperties.DISPENSINGMEDICATIONS);
	}
	
	private static void patientsStatusList() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patient_status.sql","isanteplusreports.patientStatus",
		           "Status des patients VIH", IsantePlusReportsProperties.PATIENTSTATUS);
	}
	
	private static void patientStartingArv(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patientStartingArv.sql","isanteplusreports.patientStartedArv",
		           "Liste des patients ayant démarré un régime ARV", IsantePlusReportsProperties.PATIENT_STARTED_ARV_REGIMEN_UUID);
		
	}
	
	private static void patientArvThirtyDay() {
		IsantePlusReportsUtil.registerReportsWithoutParams("patientNextArvInThirtyDay.sql","isanteplusreports.patientArvExpectedDateInThirtyDays",
			      "La liste des patients dont la date de renflouement des ARV est prévue dans les 30 prochains jours", IsantePlusReportsProperties.PATIENT_ARV_EXPECTATION_IN_THIRTY_DAYS_UUID);
	}
	
	private static void patientNextArvArrives() {
		IsantePlusReportsUtil.registerReportsWithoutParams("patientArvEnd.sql","isanteplusreports.patientArvEnd",
			      "La liste des patients dont la date de renflouement des ARV est arrivée à terme", IsantePlusReportsProperties.PATIENT_ARV_END);
	}
	
	private static void patientsReceivingARVByPeriod() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("numberPatientReceivingARVByPeriod.sql","isanteplusreports.patientArvByPeriod",
		           "Nombre de patients ayant reçu des ARV par période", IsantePlusReportsProperties.PATIENTRECEIVINGARVBYPERIOD);
	}
	
	private static void drugsPrescription(){
		IsantePlusReportsUtil.registerReportsWithoutParams("medicamentPrescrit.sql","isanteplusreports.drugsPrescriptionAmount",
			      "Médicaments prescrits", IsantePlusReportsProperties.DRUGS_PRESCRIPTION_UUID);
	}
	
	private static void labPrescription() {
		IsantePlusReportsUtil.registerReportsWithoutParams("analyses_laboratoire_prescrites.sql","isanteplusreports.labPrescription",
			      "Analyses de laboratoire prescrites", IsantePlusReportsProperties.LAB_PRESCRIPTION_UUID);
	}
	
	private static void labPerfomed() {
		IsantePlusReportsUtil.registerReportsWithoutParams("analyses_laboratoire_effectues.sql","isanteplusreports.labDone",
			      "Analyses de laboratoire effectuées", IsantePlusReportsProperties.LAB_DONE_UUID);
	}
	
	private static void institutionFrequentingByUser() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateAndLocationParams("institution_frequenting_by_user.sql","isanteplusreports.institution_frequenting_by_user",
		           "Fréquentation de l'institution Classé par Utilisateur", IsantePlusReportsProperties.INSTITUTION_FREQUENTING_OTHER_BY_USER_UUID);
	}
	
	private static void institutionFrequentingByUserAndDate() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateAndLocationParams("institution_frequenting_by_user_and_date.sql","isanteplusreports.institution_frequenting_by_user_and_date",
		           "Fr&eacute;quentation de l'institution Class&eacute; par Utilisateur et par date", IsantePlusReportsProperties.INSTITUTION_FREQUENTING_OTHER_BY_USER_AND_DATE_UUID);
	}
	
	private static void institutionFrequenting() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateAndLocationParams("institution_frequenting.sql","isanteplusreports.institution_frequenting",
		           "Fréquentation de l'institution", IsantePlusReportsProperties.INSTITUTION_FREQUENTING);
	}
	
	private static void institutionFrequentingByDate() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateAndLocationParams("institution_frequenting_by_date.sql","isanteplusreports.institution_frequenting_by_date",
		           "Fréquentation de l'institution Classé par date", IsantePlusReportsProperties.INSTITUTION_FREQUENTING_ORDER_BY_DATE);
	}
	
	private static void saveAlertReport() {
		IsantePlusReportsUtil.registerReportsWithoutParams("alert.sql","isanteplusreports.alert",
		           "Alerte", IsantePlusReportsProperties.ALERT_REPORT_DEFINITION_UUID);
	}
	
	private static void patientWithOnlyRegisterForm() {
		IsantePlusReportsUtil.registerReportsWithoutParams("patient_with_only_register_form.sql","isanteplusreports.patient_with_only_register_form",
		           "Patients avec uniquement une fiche d'enregistrement", IsantePlusReportsProperties.PATIENT_WITH_ONLY_REGISTER_FORM);
	}
	
	private static void hivPatientWithoutFirstVisit() {
		IsantePlusReportsUtil.registerReportsWithoutParams("hiv_patient_without_first_visit.sql","isanteplusreports.hiv_patient_without_first_visit",
		           "Patients (VIH) sans fiche de première visite", IsantePlusReportsProperties.HIV_PATIENT_WITHOUT_FIRST_VISIT);
	}
	
	private static void hivPatientWithActivityAfterDisc(){
		IsantePlusReportsUtil.registerReportsWithoutParams("hiv_patient_with_activity_after_disc.sql","isanteplusreports.hiv_patient_with_activity_after_discontinuation",
		           "Patients (VIH) avec activité après discontinuation", IsantePlusReportsProperties.HIV_PATIENT_WITH_ACTIVITY_AFTER_DISC);
	}
	
	private static void numberPregnancyWomenHivTested() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_pregnancy_women_hiv_tested.sql","isanteplusreports.number_pregnancy_women_hiv_tested",
		           "Nombre de femmes enceintes testées pour le VIH", IsantePlusReportsProperties.NUMBER_PREGNANT_WOMEN_HIV_TESTED_UUID);
	}

	private static void numberPregnancyWomenHivPositive() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_prenancy_women_vih_positive.sql","isanteplusreports.number_prenancy_women_vih_positive",
		           "Nombre de femmes enceintes VIH(+)", IsantePlusReportsProperties.NUMBER_PREGNANT_WOMEN_HIV_POSITIVE_UUID);
	}
	
	private static void communityArvDistribution() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("community_arv_distribution.sql","isanteplusreports.community_arv_distribution",
		           "Rapport de distribution des ARVs en communauté (DAC)", IsantePlusReportsProperties.COMMUNITY_ARV_DISTRIBUTION_UUID);
	}
	
	private static void pregnancyWomenOnHaart() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("pregnancy_women_on_haart.sql","isanteplusreports.pregnancy_women_on_haart",
		           "Nombre de femmes enceintes VIH (+) placées sous HAART", IsantePlusReportsProperties.PREGNANCY_WOMEN_ON_HAART_UUID);
	}
	
	private static void pregnancyWomenTestedForSyphilis() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("pregnancy_women_tested_for_syphilis.sql","isanteplusreports.pregnancy_women_tested_for_syphilis",
		           "Nombre de femmes enceintes testées pour la syphilis", IsantePlusReportsProperties.PREGNANCY_WOMEN_TESTED_FOR_SYPHILIS_UUID);
	}
	
	private static void pregnancyWomenDiagnosedWithSyphilis() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_pregnant_women_diagnosed_syphilis.sql","isanteplusreports.pregnancy_women_diagnosed_with_syphilis",
		           "Nombre de femmes enceintes diagnostiquées syphilis (+)", IsantePlusReportsProperties.PREGNANCY_WOMEN_DIAGNOSED_WITH_SYPHILIS);
	}
	
	private static void numberPrenatalVisitBySite() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_prenatal_visit_by_site.sql","isanteplusreports.number_prenatal_visit_by_site",
		           "Nombre total de visites prénatales par site", IsantePlusReportsProperties.NUMBER_PRENATAL_VISIT_BY_SITE_UUID);
	}
	
	private static void numberPregnancyWomenHadFirstPrenatalCons(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_pregnancy_women_had_prenatal_cons.sql","isanteplusreports.number_pregnancy_women_had_first_consultation_prenatal",
		           "Nombre de femmes enceintes vues en première consultation prénatale", IsantePlusReportsProperties.NUMBER_PREGNANCY_WOMEN_HAD_FIRST_PRENATAL_CONSULTATION);
	}
	
	private static void numberHivPregnancyWomenGaveBirthAtHospital() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_hiv_women_gave_birth_at_hospital.sql","isanteplusreports.number_hiv_pregnancy_women_gave_birth_at_hospital",
		           "Nombre de femmes enceintes VIH(+) ayant accouché en milieu hospitalier", IsantePlusReportsProperties.NUMBER_HIV_PREGNANCY_WOMEN_GAVE_BIRTH_AT_HOSP);
	}
	
	private static void exposedInfantsWithMotherInPtmeProgram() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("exposed_infants_register_in_ptme_program.sql","isanteplusreports.exposed_infants_register_in_ptme_program",
		           "Nombre d'enfants nés de mère VIH (+) (exposés) enregistrés dans le programme PTME", IsantePlusReportsProperties.EXPOSED_INFANT_WITH_MOTHER_IN_PTME_UUID);
	}
	
	private static void numberInfantsFromMotherOnProphylaxis() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_infants_from_mother_on_prophylaxis.sql","isanteplusreports.number_infants_from_mother_on_prophylaxis",
		           "Nombre d'enfants nés de mère VIH (+) placés sous ARV comme prophylaxie dans les 72 heures apr&egrave;s la naissance", IsantePlusReportsProperties.NUMBER_INFANTS_FROM_MOTHER_ON_PROPHYLAXIS);
	}
	
	private static void frequencyPrenatalVisitsPerPatient() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("frequency_prenatal_visits_per_patient.sql","isanteplusreports.frequency_prenatal_visits_per_patient",
		           "Fréquence des visites prénatales par patient", IsantePlusReportsProperties.FREQUENCY_PRENATAL_VISITS_PER_PATIENT_UUID);
	}
	
	private static void numberExposedInfantsTestedByPcr(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_exposed_infants_tested_by_pcr.sql","isanteplusreports.number_exposed_infants_tested_by_pcr",
		           "Nombre d'enfants exposés testés par PCR", IsantePlusReportsProperties.NUMBER_EXPOSED_INFANTS_TESTED_BY_PCR_UUID);
	}
	
	private static void numberExposedInfantsConfirmedHiv() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_exposed_infants_confirmed_hiv.sql","isanteplusreports.number_exposed_infants_confirmed_hiv",
		           "Nombre d'enfants exposés confirmés VIH(+)", IsantePlusReportsProperties.NUMBER_EXPOSED_INFANTS_CONFIRMED_HIV_UUID);
	}

	private static  void numberWomenFirstPrenatalVisitFirstTrimester() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_women_seen_first_prenatal_visit_first_trimester.sql","isanteplusreports.number_women_seen_first_prenatal_visit_first_trimester",
		           "Nombre de femmes enceintes vues en première visite après leur 1er trimestre", IsantePlusReportsProperties.NUMBER_WOMEN_SEEN_FIRST_PRENATAL_VISIT_FIRST_SEMESTER_UUID);
	}
	
	private static void hivTransmissionRisksFactor() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("hiv_transmission_risks_factor.sql","isanteplusreports.hiv_transmission_risks_factor",
		           "Facteurs de risque de transmission du VIH", IsantePlusReportsProperties.HIV_TRANSMISSION_RISKS_FACTOR_UUID);
	}
	
	private static void numberVisitsByPregnantWomenToClinic() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_visits_by_pregnant_women_to_the_clinic.sql","isanteplusreports.number_visits_by_pregnant_women_to_the_clinic",
		           "Nombre de visites de femmes enceintes reçues en clinique", IsantePlusReportsProperties.NUMBER_VISITS_BY_PREGNANT_WOMEN_UUID);
	}
	
	private static void listVisitsByPregnantWomenToClinic() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_pregnancy_women_receiving_in_clinic.sql","isanteplusreports.list_pregnancy_women_receiving_in_clinic",
		           "Liste de visites de femmes enceintes reçues en clinique", IsantePlusReportsProperties.LIST_VISITS_BY_PREGNANT_WOMEN_UUID);
	}
	
	private static void listPatientsReceivingArtInCommunity() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_receiving_arv_in_community.sql","isanteplusreports.list_patients_receiving_arv_in_community",
		           "Liste des patients ayant reçu des ARVs en communauté", IsantePlusReportsProperties.LIST_PATIENTS_RECEIVING_ARV_IN_COMMUNITY);
	}
	private static void listPatientsReceivingArtInHospital() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_receiving_arv_in_hospital.sql","isanteplusreports.list_patients_receiving_arv_in_hospital",
		           "Liste des patients ayant reçu des ARVs en milieu hospitalier", IsantePlusReportsProperties.LIST_PATIENTS_RECEIVING_ARV_IN_HOSPITAL);
	}
	
	private static void listPatientsBeneficiePcr() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patient_beneficie_pcr.sql","isanteplusreports.list_patients_beneficie_pcr",
		           "Liste des patients ayant bénéficié d'un PCR", IsantePlusReportsProperties.LIST_PATIENTS_BENEFICIE_PCR_UUID);
	}
	private static void numberPatientsBeneficiePcr() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_patient_beneficie_pcr.sql","isanteplusreports.number_patients_beneficie_pcr",
		           "Nombre de patients ayant bénéficié d'un PCR", IsantePlusReportsProperties.NUMBER_PATIENTS_BENEFICIE_PCR_UUID);
	}
	
	private static void listEligibleChildrenPcr(){
		IsantePlusReportsUtil.registerReportsWithoutParams("list_eligible_children_for_pcr.sql","isanteplusreports.list_eligible_children_for_pcr",
		       "Liste des enfants éligibles pour un PCR", IsantePlusReportsProperties.LIST_ELIGIBLE_CHILDREN_FOR_PCR_UUID);
	
	}
	
	private static void numberEligibleChildrenPcr(){
		IsantePlusReportsUtil.registerReportsWithoutParams("number_eligible_children_for_pcr.sql","isanteplusreports.number_eligible_children_for_pcr",
		       "Nombre d'enfants éligibles pour un PCR", IsantePlusReportsProperties.NUMBER_ELIGIBLE_CHILDREN_FOR_PCR_UUID);
	
	}
	
	private static void numberChargeViraleByResultDate(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_patients_charge_virale_moins_plus_mille.sql","isanteplusreports.number_charge_virale_by_result_date",
		           "Charge virale en fonction du nombre de copies/ml (selon la date du résultat)", IsantePlusReportsProperties.CHARGE_VIRALE_SELON_DATE_RESULTAT);
	}
	
	private static void listPatChargeViraleMoinsMilleByResultDate(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_charge_virale_moins_mille_by_result_date.sql","isanteplusreports.list_patients_charge_virale_moins_mille_by_result_date",
		           "Liste des patients avec charge virale < 1000 copies/ml (selon la date du résultat)", IsantePlusReportsProperties.LIST_PAT_CHARGE_VIRALE_MOINS_MILLE_RES_DATE);
	}
	
	private static void listPatChargeViralePlusMilleByResultDate(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_charge_virale_plus_mille_by_result_date.sql","isanteplusreports.list_patients_charge_virale_plus_mille_by_result_date",
		           "Liste des patients avec charge virale >= 1000 copies/ml (selon la date du résultat)", IsantePlusReportsProperties.LIST_PAT_CHARGE_VIRALE_PLUS_MILLE_RES_DATE);
	}
	
	private static void numberChargeViraleByDemandDate(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_patients_charge_virale_moins_plus_mille_demand.sql","isanteplusreports.number_charge_virale_by_demand_date",
		           "Charge virale en fonction du nombre de copies/ml (selon la date de la demande)", IsantePlusReportsProperties.CHARGE_VIRALE_SELON_DATE_DEMAND);
	}
	private static void listPatChargeViraleMoinsMilleByDemandDate(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_charge_virale_moins_mille_by_demand_date.sql","isanteplusreports.list_patients_charge_virale_moins_mille_by_demand_date",
		           "Liste des patients avec charge virale < 1000 copies/ml (selon la date de la demande)", IsantePlusReportsProperties.LIST_PAT_CHARGE_VIRALE_MOINS_MILLE_DEMAND_DATE);
	}
	
	private static void listPatChargeViralePlusMilleByDemandDate(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_charge_virale_plus_mille_by_demand_date.sql","isanteplusreports.list_patients_charge_virale_plus_mille_by_demand_date",
		           "Liste des patients avec charge virale >= 1000 copies/ml (selon la date de la demande)", IsantePlusReportsProperties.LIST_PAT_CHARGE_VIRALE_PLUS_MILLE_DEMAND_DATE);
	}
	
	private static void listOfLabOrders(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_of_lab_orders.sql","isanteplusreports.list_of_lab_orders",
				"Liste des commandes de laboratoire", IsantePlusReportsProperties.LIST_OF_LAB_ORDERS);
	}
	
}
