package org.openmrs.module.isanteplusreports;

//import org.openmrs.module.emrapi.EmrApiProperties;

public class IsantePlusReportsProperties{
	
	//***** PRIVILEGES ******
	public static final String PRIVILEGE_PATIENT_DASHBOARD = "App: coreapps.patientDashboard";
	
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
	
	public static final String NUMBERPATIENTBYSEX = "892661ed-f096-4580-a2de-abcf8a13658d";
	
	public final String DISPENSINGMEDICATIONS = "bdd40a99-1727-43a3-b155-71b73bf681b9";
	
	public final String PATIENTSTATUS = "b5a70371-3ef5-4d6b-a3e1-1a2cfde6a66d";
	
	public final String DRUGS_PRESCRIPTION_UUID = "184c228b-ff83-41cb-97c3-6bfc7fe5c4c8";
	
	public final String LAB_PRESCRIPTION_UUID = "84f3fc41-c932-411f-b43f-f51cfe4e44a7";
	
	public final String LAB_DONE_UUID = "0a290fe4-863b-4137-bbd6-c79899baf694";
	
	public final String PATIENTRECEIVINGARVBYPERIOD = "ead22ae8-c3ab-4c27-ab8d-e63ec8658e50";
	
	public final String PATIENT_STARTED_ARV_REGIMEN_UUID = "a34a5b4a-13d7-4909-a2ef-557db15411cc";
	
	public final String PATIENT_ARV_EXPECTATION_IN_THIRTY_DAYS_UUID = "2bde1a37-dcbc-4e06-8090-72b6d5356abf";
	
	public final String PATIENT_ARV_END = "fd4f74f0-4e3b-42a3-bec0-be1367a2e2ce";
	
	public final String INSTITUTION_FREQUENTING_OTHER_BY_USER_UUID = "6cd7ff44-4139-4289-8a1c-125cbb9b305a";
	
	public final String INSTITUTION_FREQUENTING_OTHER_BY_USER_AND_DATE_UUID = "ce6214e2-08e5-43bc-8b83-c934e712b28c";
	
	public final String INSTITUTION_FREQUENTING = "1ed1cfcf-38f6-460f-b7c0-7fa3ea0dfd2a";
	
	public final String INSTITUTION_FREQUENTING_ORDER_BY_DATE = "33c5cee6-8102-43c1-975e-c11b3ce26ea9";
	
	public final String ALERT_REPORT_DEFINITION_UUID = "bbb6e146-67f3-49fd-be43-7aeb776def9b";
	
	public final String PATIENT_WITH_ONLY_REGISTER_FORM = "dfa9a7a6-629f-46db-9648-60a8ca9214be";
	
	public final String HIV_PATIENT_WITHOUT_FIRST_VISIT = "aea43fb1-3bed-4745-a316-4c55cfdd868e";
	
	public final String HIV_PATIENT_WITH_ACTIVITY_AFTER_DISC = "1d83343e-9467-42e7-b2dd-a69362802d62";
	
	public final String FORM_RECENTLY_FILLED_UUID = "93845fc4-d16e-4594-8497-2afda9d398d6";
	
	public final String NUMBER_PREGNANT_WOMEN_HIV_TESTED_UUID = "7a55fec8-6189-4e0f-9742-a462e4dbc189";
	
	public final String NUMBER_PREGNANT_WOMEN_HIV_POSITIVE_UUID = "f5c6a8db-73cb-4537-b2ce-833cf850fad4";
	
	public final String COMMUNITY_ARV_DISTRIBUTION_UUID = "437469a1-7112-47a7-b48a-6cec7bf87260";
	
	public final String PREGNANCY_WOMEN_ON_HAART_UUID = "b3c29c8d-6918-4b16-90b7-aff3d1204860";
	
	public final String PREGNANCY_WOMEN_TESTED_FOR_SYPHILIS_UUID = "23f61edd-76b1-4e68-a24d-c3c16a91436d";
	
	public final String PREGNANCY_WOMEN_DIAGNOSED_WITH_SYPHILIS = "613d0119-cb40-4fed-aa67-56f032a20cc9";
	
	public final String NUMBER_PRENATAL_VISIT_BY_SITE_UUID = "b7f7ea96-3fe4-4a17-99b5-1ad2cdaadf50";
	
	public final String NUMBER_PREGNANCY_WOMEN_HAD_FIRST_PRENATAL_CONSULTATION = "60051dfe-85f3-45e1-9df2-a45bbed2918b";
	
	public final String NUMBER_HIV_PREGNANCY_WOMEN_GAVE_BIRTH_AT_HOSP = "a366a646-382a-4eb1-b2a7-16282579c9df";
	
	public final String EXPOSED_INFANT_WITH_MOTHER_IN_PTME_UUID = "f786f658-c9a8-4a3b-bfc1-6232807ef8fe";
	
	public final String NUMBER_INFANTS_FROM_MOTHER_ON_PROPHYLAXIS = "d0ba4eba-dc36-4adc-9c0e-f0eb244fd279";
	
	public final String FREQUENCY_PRENATAL_VISITS_PER_PATIENT_UUID = "6d2de0f7-2396-4e17-9399-5a704b3ea7f9";
	
	public final String NUMBER_EXPOSED_INFANTS_TESTED_BY_PCR_UUID = "314972cc-0d5c-45c6-9445-54a942e96b43";
	
	public final String NUMBER_EXPOSED_INFANTS_CONFIRMED_HIV_UUID = "1c072b8f-2740-4cb4-aff7-6d0095df2437";
	
	public final String NUMBER_WOMEN_SEEN_FIRST_PRENATAL_VISIT_FIRST_SEMESTER_UUID = "99dd9482-6e97-49be-8e6e-123ba8f689cb";
	
	public final String NUMBER_PATIENTS_BY_ARV_STATUS_UUID = "28cf5e0c-e1d6-49de-8b24-eb165a242d69";
	
	public final String HIV_TRANSMISSION_RISKS_FACTOR_UUID = "b66e2895-d766-4fad-b12f-19b979c82d06";
	
	public final String NUMBER_VISITS_BY_PREGNANT_WOMEN_UUID = "6647de56-ada8-4a01-a5ed-3e8db17d3957";
	
	public final String LIST_VISITS_BY_PREGNANT_WOMEN_UUID = "a3d743f9-fc42-429e-bd71-e2b9e375cc61";

	public static final String HEALTH_QUAL_CHILDREN_REGULARLY_FOLLOWED_ON_ART = "aa8b29c6-6c2c-4161-8990-ccdcceb92e3e";
}
