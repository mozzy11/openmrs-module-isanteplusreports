package org.openmrs.module.isanteplusreports.pnlsReport;

import static org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants.REPORTS_SQL_PATH;

public class PnlsReportConstants {
	
	public static final String PNLS_REPORTS_RESOURCE_PATH = REPORTS_SQL_PATH + "pnlsIndicatorReport/";
	
	public static final String PNLS_GENERAL_PURPOSE_SUFFIX = "_PNLS";
			
	public final static String NEWLY_ENROLLED_PATIENTS_ON_ART_SQL = "newPatientsEnrolledOnART.sql";
	
	public final static String REFERRED_IN_PATIENTS_ENROLED_ON_ART_SQL = "refferedInPatientsEnrolledOnArt.sql";
	
	public final static String NEW_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_SQL ="newlyReferredInBreatFeedingWomen.sql";
	
	public final static String MSM_COHORT_SQL ="";
	
	public final static String SEX_PROFFESIONALS_COHORT_SQL ="";
	
	public final static String TRANSGENDER__COHORT_SQL ="";
	
	public final static String CAPTIVES__COHORT_SQL ="";
	
	public final static String INJECTION_DRUG_USERS_COHORT_SQL ="";
	
	public final static String DIED_REASON_COHORT_SQL ="";
	
	public final static String VOLUNTARY_REASON_COHORT_SQL ="";
	
	public final static String DENIAL_REASON_COHORT_SQL ="";
	
	public final static String MEDICAL_DECISION_REASON_COHORT_SQL ="";
	
	public final static String REFERRED_B4_REASON_COHORT_SQL ="";
	
	
	public static final String NEWLY_ENROLLED_PATIENTS_ON_ART_UUID = "06460e12-937d-11ea-bb37-0242ac130059";
	
	public final static String REFERRED_IN_PATIENTS_ENROLED_ON_ART_UUID = "5d9f227e-98dd-11ea-bb37-0242ac130010";
	
	public final static String NEW_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_UUID = "f21159a7-b758-42d5-b797-3fe132eb1e34";
	
	
	public static final String NEWLY_ENROLLED_PATIENTS_ON_ART_MESSAGE = "isanteplusreports.newlyEnrolledPatientsOnArt";
	
	public final static String REFERRED_IN_PATIENTS_ENROLED_ON_ART_MESSAGE = "isanteplusreports.referredInPatientsAnArt";
	
	public final static String NEW_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_MESSAGE = "isanteplusreports.newlyEnrolledBreastFeedingWomenOnArt" ;
}
