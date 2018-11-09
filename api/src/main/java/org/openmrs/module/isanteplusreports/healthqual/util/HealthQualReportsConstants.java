package org.openmrs.module.isanteplusreports.healthqual.util;

import static org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants.REPORTS_SQL_PATH;

public final class HealthQualReportsConstants {

    static final String HEALTH_QUAL_REPORTS_RESOURCE_PATH = REPORTS_SQL_PATH + "healthQualReports/";

    // SQL files
    static final String NUMBER_OF_ACTIVE_PATIENTS_BY_SEX_SQL = "numberOfActivePatientsBySex.sql";

    static final String ADULT_1_INDICATOR_SQL = "retentionOfPatientsOnArt.sql";

    static final String ADULT_2_INDICATOR_SQL = "cd4AssessmentAtEnrolment.sql";

    static final String ADULT_3_INDICATOR_SQL = "arvEnrollment.sql";

    static final String ADULT_4_INDICATOR_SQL = "adultHivAndCortimixazoleProphy.sql";

    static final String ADULT_5_INDICATOR_SQL = "adultHivOnArtWithAdherentEvaluation.sql";

    static final String ADULT_6_INDICATOR_SQL = "adultHivOnArtAdherentToTreatment.sql";

    static final String ADULT_7_INDICATOR_SQL = "proportionOfPLHIVTestedForTB.sql";

    static final String ADULT_8_INDICATOR_SQL = "adultPlhivAndInh.sql";

    static final String ADULT_9_INDICATOR_SQL = "proportionOfHIVPatientsWithNutritionalAssessment.sql";

    static final String ADULT_10_INDICATOR_SQL = "proportionOfUndernourishedHIVPatients.sql";

    static final String ADULT_11_INDICATOR_SQL = "proportionOfHivWomenUsingFamilyPlanning.sql";

    static final String ADULT_12_INDICATOR_SQL = "proportionOfHIVPregnantWithHAART.sql";

    static final String ADULT_13_INDICATOR_SQL = "proportionOfWomenInPrenatalCareOrLD.sql";

    static final String ADULT_14_INDICATOR_SQL = "proportionOfHivOnArtViralLoadTest6Months.sql";

    static final String ADULT_15_INDICATOR_SQL = "proportionOfHivOnArtViralLoadTest.sql";

    static final String ADULT_16_INDICATOR_SQL = "proportionOfHivOnArtUndetectableViralLoadTest6Months.sql";

    static final String PEDIATRIC_1_INDICATOR_SQL = "childrenRegularlyFollowedOnArt.sql";

    static final String PEDIATRIC_2_INDICATOR_SQL = "childrenHivAndPlacedOnArt.sql";

    static final String PEDIATRIC_3_INDICATOR_SQL = "pediatricHivAndReceivedCotrimoxazoleProphylaxis.sql";

    static final String PEDIATRIC_4_INDICATOR_SQL = "pediatricBenefitedFromAnAdherence.sql";

    static final String PEDIATRIC_5_INDICATOR_SQL = "childrenOnArtWhoAreConsideredAdherent.sql";

    static final String PEDIATRIC_6_INDICATOR_SQL = "proportionOfHIVChildrenTestedForTB.sql";

    static final String PEDIATRIC_7_INDICATOR_SQL = "proportionOfHIVChildrenOlderThanOneYearOfAgeReceivedINH.sql";

    static final String PEDIATRIC_8_INDICATOR_SQL = "proportionOfHIVChildrenYoungerThanOneYearOfAgeReceivedINH.sql";

    static final String PEDIATRIC_9_INDICATOR_SQL = "proportionOfChildrenWithNutritionalAssessment.sql";

    static final String PEDIATRIC_10_INDICATOR_SQL = "healthQualChildrenImmunizationAndVaccination.sql";

    static final String PEDIATRIC_11_INDICATOR_SQL = "pediatricHivAndArtProphy.sql";

    static final String PEDIATRIC_12_INDICATOR_SQL = "pediatricReceivedPcrTest.sql";

    static final String PEDIATRIC_13_INDICATOR_SQL = "pediatricNegativePcrTest.sql";

    // Indicator message properties
    static final String ADULT = "isanteplusreports.adult";

    static final String PEDIATRIC = "isanteplusreports.pediatric";

    static final String NUMBER_OF_ACTIVE_PATIENTS_BY_SEX_MESSAGE = "isanteplusreports.numberOfActivePatientsBySex";

    static final String ADULT_1_INDICATOR_MESSAGE = ADULT + 1;

    static final String ADULT_2_INDICATOR_MESSAGE = ADULT + 2;

    static final String ADULT_3_INDICATOR_MESSAGE = ADULT + 3;

    static final String ADULT_4_INDICATOR_MESSAGE = ADULT + 4;

    static final String ADULT_5_INDICATOR_MESSAGE = ADULT + 5;

    static final String ADULT_6_INDICATOR_MESSAGE = ADULT + 6;

    static final String ADULT_7_INDICATOR_MESSAGE = ADULT + 7;

    static final String ADULT_8_INDICATOR_MESSAGE = ADULT + 8;

    static final String ADULT_9_INDICATOR_MESSAGE = ADULT + 9;

    static final String ADULT_10_INDICATOR_MESSAGE = ADULT + 10;

    static final String ADULT_11_INDICATOR_MESSAGE = ADULT + 11;

    static final String ADULT_12_INDICATOR_MESSAGE = ADULT + 12;

    static final String ADULT_13_INDICATOR_MESSAGE = ADULT + 13;

    static final String ADULT_14_INDICATOR_MESSAGE = ADULT + 14;

    static final String ADULT_15_INDICATOR_MESSAGE = ADULT + 15;

    static final String ADULT_16_INDICATOR_MESSAGE = ADULT + 16;

    static final String PEDIATRIC_1_INDICATOR_MESSAGE = PEDIATRIC + 1;

    static final String PEDIATRIC_2_INDICATOR_MESSAGE = PEDIATRIC + 2;

    static final String PEDIATRIC_3_INDICATOR_MESSAGE = PEDIATRIC + 3;

    static final String PEDIATRIC_4_INDICATOR_MESSAGE = PEDIATRIC + 4;

    static final String PEDIATRIC_5_INDICATOR_MESSAGE = PEDIATRIC + 5;

    static final String PEDIATRIC_6_INDICATOR_MESSAGE = PEDIATRIC + 6;

    static final String PEDIATRIC_7_INDICATOR_MESSAGE = PEDIATRIC + 7;

    static final String PEDIATRIC_8_INDICATOR_MESSAGE = PEDIATRIC + 8;

    static final String PEDIATRIC_9_INDICATOR_MESSAGE = PEDIATRIC + 9;

    static final String PEDIATRIC_10_INDICATOR_MESSAGE = PEDIATRIC + 10;

    static final String PEDIATRIC_11_INDICATOR_MESSAGE = PEDIATRIC + 11;

    static final String PEDIATRIC_12_INDICATOR_MESSAGE = PEDIATRIC + 12;

    static final String PEDIATRIC_13_INDICATOR_MESSAGE = PEDIATRIC + 13;

    // indicators' UUIDs
    public static final String NUMBER_OF_ACTIVE_PATIENTS_BY_SEX_UUID = "69679207-04a8-4fb9-b3ad-75a8dd5c0a58";

    public static final String HEALTH_QUAL_ADULT_1_INDICATOR_UUID = "43f9c7f2-01b9-45c4-9da0-5776bee49c3f";

    public static final String HEALTH_QUAL_ADULT_2_INDICATOR_UUID = "3fe2c0a0-0b4a-4a51-8ccb-95052fdb83c4";

    public static final String HEALTH_QUAL_ADULT_3_INDICATOR_UUID = "cb263f8d-680f-4487-a36b-afe5af355d18";

    public static final String HEALTH_QUAL_ADULT_4_INDICATOR_UUID = "abc01b2b-f296-4cfb-bf22-ad09686b1cff";

    public static final String HEALTH_QUAL_ADULT_5_INDICATOR_UUID = "f38c0a9b-e27d-4eed-b71a-e8829b7cc36f";

    public static final String HEALTH_QUAL_ADULT_6_INDICATOR_UUID = "68dfcc2f-0d4c-4f0d-af07-c0475f6ed981";

    public static final String HEALTH_QUAL_ADULT_7_INDICATOR_UUID = "1a261933-d626-4a63-a18a-a94e189871a5";

    public static final String HEALTH_QUAL_ADULT_8_INDICATOR_UUID = "08e3581e-3d3e-4145-8a34-7b750fd928d3";

    public static final String HEALTH_QUAL_ADULT_9_INDICATOR_UUID = "4a7a39b4-f2d1-4adf-b883-cd348c8f278f";

    public static final String HEALTH_QUAL_ADULT_10_INDICATOR_UUID = "a1b6c965-8779-4ea8-965c-02bccb4d56e4";

    public static final String HEALTH_QUAL_ADULT_11_INDICATOR_UUID = "ec6b6a7d-3583-4063-8d13-d5ae896b143b";

    public static final String HEALTH_QUAL_ADULT_12_INDICATOR_UUID = "cc351cca-c6ea-4fb6-a9b8-859860cbc4db";

    public static final String HEALTH_QUAL_ADULT_13_INDICATOR_UUID = "90706f98-e32e-4ae6-b4e4-ed05ef8b27bb";

    public static final String HEALTH_QUAL_ADULT_14_INDICATOR_UUID = "8de6b6df-3a33-4928-be52-8738ad7ba918";

    public static final String HEALTH_QUAL_ADULT_15_INDICATOR_UUID = "25d30ec8-26d2-4fd8-9216-e66bb78cc164";

    public static final String HEALTH_QUAL_ADULT_16_INDICATOR_UUID = "ccf71a0a-a866-4d4c-ab25-89aad6a194fe";

    public static final String HEALTH_QUAL_PEDIATRIC_1_INDICATOR_UUID = "aa8b29c6-6c2c-4161-8990-ccdcceb92e3e";

    public static final String HEALTH_QUAL_PEDIATRIC_2_INDICATOR_UUID = "85df69ec-f4bf-4711-8a19-d961aae4728d";

    public static final String HEALTH_QUAL_PEDIATRIC_3_INDICATOR_UUID = "41f24149-455b-40f4-89d4-0d8f3d752ab5";

    public static final String HEALTH_QUAL_PEDIATRIC_4_INDICATOR_UUID = "bb6f1ccb-33f7-4013-a17f-771a3ff43d23";

    public static final String HEALTH_QUAL_PEDIATRIC_5_INDICATOR_UUID = "8c1135ec-6c52-4747-a7b1-2de83ff13da9";

    public static final String HEALTH_QUAL_PEDIATRIC_6_INDICATOR_UUID = "d73c965f-8cdd-4996-bbca-a65d8a19db42";

    public static final String HEALTH_QUAL_PEDIATRIC_7_INDICATOR_UUID = "3517beaf-83fb-43b6-a8a5-8a6cbf9e1acf";

    public static final String HEALTH_QUAL_PEDIATRIC_8_INDICATOR_UUID = "b40f7761-69c2-4a8b-8f0d-0cda0d131b5e";

    public static final String HEALTH_QUAL_PEDIATRIC_9_INDICATOR_UUID = "0bc15688-6906-44f3-8b32-a2d0d148ad2f";

    public static final String HEALTH_QUAL_PEDIATRIC_10_INDICATOR_UUID = "58861622-d479-4b23-9b07-7c3bea1b84c7";

    public static final String HEALTH_QUAL_PEDIATRIC_11_INDICATOR_UUID = "5cd16174-7acb-4978-ab0f-735cec42b703";

    public static final String HEALTH_QUAL_PEDIATRIC_12_INDICATOR_UUID = "2baddb89-084b-4152-b9ce-3b9826e79495";

    public static final String HEALTH_QUAL_PEDIATRIC_13_INDICATOR_UUID = "28a71811-5511-40eb-8ca5-79464cd3448a";
}
