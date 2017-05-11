package org.openmrs.module.isanteplusreports;

import org.openmrs.module.emrapi.EmrApiProperties;

public class IsantePlusReportsProperties extends EmrApiProperties {
	
	//***** PRIVILEGES ******
	public static final String PRIVILEGE_PATIENT_DASHBOARD = "App: coreapps.patientDashboard";
	
	//***** GLOBAL PROPERTIES ******
	public static final String DAILY_CHECKINS_HIDE_COUNTS = "isanteplusreports.dailyCheckins.hideCounts";
	
	public final String SEVEN_DAYS_REPORT_DEFINITION_UUID = "1c52d6ee-7cc1-4bae-a303-ffa2bdd0a8e2";
	
	public final String FOURTEEN_DAYS_REPORT_DEFINITION_UUID = "d5ced5d1-bbc7-4679-9020-51e732b69dfd";
	
	public final String PATIENT_AGE_GROUP_REPORT_DEFINITION_UUID = "5c40612b-bd9b-4cb7-8c30-1ae89fcdf038";
	
	public final String PATIENTCRACHATANORMALWITHOUTTBDIAGNOSTIC = "54ecf434-5978-4826-b049-d965fe1f81ed";
	
	public final String PATIENTWITHCOMPLETEDTBTREATMENT = "7acca9bf-ee7c-49bd-9444-faf2e66aaeb9";
	
	public final String PATIENTSWITHTBDIAGNOSTICWITHOUTTREATMENT = "5c4201e3-83c1-421a-8bc1-f23ecb67cf60";
	
	public final String PATIENTSWITHTBDIAGNOSTICWITHOUTCRACHAT = "73173f96-69fc-4c74-b31c-c7ffb9a37d69";
	
	public final String CONSULTATIONBYDAY = "d5ca9447-c2b2-4625-af65-93fc1ff58843";
	
	public final String FIRSTVISITAGE = "27882c50-7a45-475b-97c1-b0618135e667";
	
	public final String NUMBERVISITSBYMONTH = "68023435-7e82-4e95-af6b-c137476f0f02";
	
	public final String NUMBERPATIENTBYSEX = "892661ed-f096-4580-a2de-abcf8a13658d";
	
	public final String DISPENSINGMEDICATIONS = "bdd40a99-1727-43a3-b155-71b73bf681b9";
	
	public final String PATIENTSTATUS = "b5a70371-3ef5-4d6b-a3e1-1a2cfde6a66d";
	
	public final String DRUGS_PRESCRIPTION_UUID = "184c228b-ff83-41cb-97c3-6bfc7fe5c4c8";
	
	public final String LAB_PRESCRIPTION_UUID = "84f3fc41-c932-411f-b43f-f51cfe4e44a7";
	
	public final String LAB_DONE_UUID = "0a290fe4-863b-4137-bbd6-c79899baf694";
	
	public final String PATIENTRECEIVINGARVBYPERIOD = "ead22ae8-c3ab-4c27-ab8d-e63ec8658e50";
	
	public final String PATIENT_STARTED_ARV_REGIMEN_UUID = "a34a5b4a-13d7-4909-a2ef-557db15411cc";
	
	public final String PATIENT_ARV_EXPECTATION_IN_THIRTY_DAYS_UUID = "2bde1a37-dcbc-4e06-8090-72b6d5356abf";
	
	public final String PATIENT_ARV_END = "fd4f74f0-4e3b-42a3-bec0-be1367a2e2ce";
}
