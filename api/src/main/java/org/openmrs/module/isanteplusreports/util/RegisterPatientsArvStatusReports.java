package org.openmrs.module.isanteplusreports.util;

import org.openmrs.module.appframework.context.SessionContext;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;

public class RegisterPatientsArvStatusReports extends SessionContext {
	
	//IsantePlusReportsProperties props = new IsantePlusReportsProperties();
	
	//static IsantePlusReportsProperties props;
	//IsantePlusReportsUtil reportsUtil;
	
	public static void registerAllPatientsArvStatusReports()
	{
		numberPatientsByArvStatus();
		listPatientsDeathOnArt();
		listPatientsStoppedOnArt();
		listPatientsTransfertOnArt();
		listPatientsDeathOnPreArt();
		listPatientsTransferredOnPreArt();
		listPatientsRegularActiveOnArt();
		listPatientsRecentOnPreArt();
		listPatientsMissingAppointmentOnArt();
		listPatientsLostToFollowUpOnArt();
		listPatientsLostToFollowUpOnPreArt();
		listPatientsActiveOnPreArt();
		
	}
	
	private static void numberPatientsByArvStatus() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_patient_by_status.sql","isanteplusreports.number_patient_by_arv_status",
		           "Nombre de patients par status ARV", IsantePlusReportsProperties.NUMBER_PATIENTS_BY_ARV_STATUS_UUID);
	}
	
	private static void listPatientsDeathOnArt(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_death_on_art.sql","isanteplusreports.death_on_art",
		           "Liste des patients décédés sous ARV", IsantePlusReportsProperties.LIST_PATIENTS_DEATH_ON_ART);
	}
	
	private static void listPatientsStoppedOnArt() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_stopped_on_art.sql","isanteplusreports.stopped_on_art",
		           "Liste des patients arrêtés sous ARV", IsantePlusReportsProperties.LIST_PATIENTS_STOPPED_ON_ART);
	}
	
	private static void listPatientsTransfertOnArt() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_transfert_on_art.sql","isanteplusreports.transfered_on_art",
		           "Liste des patients Transférés sous ARV", IsantePlusReportsProperties.LIST_PATIENTS_TRANSFERED_ON_ART);
	}
	
	private static void listPatientsDeathOnPreArt() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_death_on_pre_art.sql","isanteplusreports.death_on_pre_art",
		           "Liste des patients Décédés en Pré-ARV", IsantePlusReportsProperties.LIST_PATIENTS_DEATH_ON_PRE_ART);
	}
	private static void listPatientsTransferredOnPreArt() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_transferred_on_pre_art.sql","isanteplusreports.transferred_on_pre_art",
		           "Liste des patients Transférés en Pré-ARV", IsantePlusReportsProperties.LIST_PATIENTS_TRANSFERRED_ON_PRE_ART);
	}
	
	private static void listPatientsRegularActiveOnArt() {
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_regular_active_on_arv.sql","isanteplusreports.regular_active_on_art",
           "Liste des patients Réguliers (actifs sous ARV)", IsantePlusReportsProperties.LIST_PATIENTS_REGULAR_ACTIVE_ON_ART);
    }
	private static void listPatientsRecentOnPreArt(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_regular_active_on_arv.sql","isanteplusreports.recent_on_pre_art",
	           "Liste des patients récents en Pré-ARV", IsantePlusReportsProperties.LIST_PATIENTS_RECENT_ON_PRE_ART);
	}
	private static void listPatientsMissingAppointmentOnArt(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_missing_appointment_on_arv.sql","isanteplusreports.missing_appointment",
	           "Liste des patients Rendez-vous ratés", IsantePlusReportsProperties.LIST_PATIENTS_MISSING_APPOINTMENT_ON_ART_UUID);
	}
	private static void listPatientsLostToFollowUpOnArt(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_lost_to_follow_up_on_art.sql","isanteplusreports.list_patients_lost_to_follow_up_on_art",
	           "Liste des patients Perdus de vue", IsantePlusReportsProperties.LIST_PATIENTS_LOST_TO_FOLLOW_UP_ON_ART_UUID);
	}
	private static void listPatientsLostToFollowUpOnPreArt(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_lost_to_follow_up_on_pre_art.sql","isanteplusreports.list_patients_lost_to_follow_up_on_pre_art",
	           "Liste des patients Perdus de vue en Pré-ARV", IsantePlusReportsProperties.LIST_PATIENTS_LOST_TO_FOLLOW_UP_ON_PRE_ART_UUID);
	}
	private static void listPatientsActiveOnPreArt(){
		IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_active_on_pre_art.sql","isanteplusreports.list_patients_active_on_pre_art",
	           "Liste des patients Actifs en Pré-ARV", IsantePlusReportsProperties.LIST_PATIENTS_ACTIVE_ON_PRE_ART_UUID);
	}


}
