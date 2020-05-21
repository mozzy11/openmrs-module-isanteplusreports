package org.openmrs.module.isanteplusreports.pnlsReport.library.cohort;

import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.COHORT_NON_ENROLLMENT_REASON_DENIAL_SQL;
import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.COHORT_NON_ENROLLMENT_REASON_DIED_SQL;
import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.COHORT_NON_ENROLLMENT_REASON_MEDICAL_SQL;
import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.COHORT_NON_ENROLLMENT_REASON_OTHER_SQL;
import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.COHORT_NON_ENROLLMENT_REASON_REFERRED_SQL;
import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.COHORT_NON_ENROLLMENT_REASON_VOLUNTARY_SQL;

import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.MSM_COHORT_SQL;
import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.SEX_PROFFESIONALS_COHORT_SQL;
import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.TRANSGENDER__COHORT_SQL;
import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.CAPTIVES__COHORT_SQL;
import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.INJECTION_DRUG_USERS_COHORT_SQL;
import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.WOMEN_TESTED_POSTIVE_FOR_CERVICAL_CANCER_COHORT_SQL ;
import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.WOMEN_TESTED_NEGATIVE_FOR_CERVICAL_CANCER_COHORT_SQL ;
import static org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants.WOMEN_WITH_SUSPECTED_CERVICAL_CANCER_COHORT_SQL  ;

import java.util.Date;

import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;

public class PnlsReportCohortLibrary {

  public static SqlCohortDefinition sqlCohortDefinition(String sqlResourceName, String cohortDefinitionName) {
		String sql = IsantePlusReportsUtil
		        .getStringFromResource(PnlsReportConstants.PNLS_REPORTS_RESOURCE_PATH + sqlResourceName);
		
		SqlCohortDefinition definition = IsantePlusReportsUtil.sqlCohortDefinition(sql, cohortDefinitionName,
		    MessageUtil.translate(cohortDefinitionName));
		
		return definition;
	}
	
  public static CohortDefinition cohortByNonEnrollmentReasonDenial() {	  
	 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition(COHORT_NON_ENROLLMENT_REASON_DENIAL_SQL ,"reason definition");
	return cohortDefinition;	  
  }
  
  public static CohortDefinition cohortByNonEnrollmentReasonVoluntary() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition(COHORT_NON_ENROLLMENT_REASON_VOLUNTARY_SQL,"reason definition");
		return cohortDefinition;	  
	  }
  
  public static CohortDefinition cohortByNonEnrollmentReasonDied() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition(COHORT_NON_ENROLLMENT_REASON_DIED_SQL ,"reason definition");
		return cohortDefinition;	  
	  }
  public static CohortDefinition cohortByNonEnrollmentReasonMedical() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition(COHORT_NON_ENROLLMENT_REASON_MEDICAL_SQL,"reason definition");
		return cohortDefinition;	  
	  }
  public static CohortDefinition cohortByNonEnrollmentReasonRefferd() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition(COHORT_NON_ENROLLMENT_REASON_REFERRED_SQL ,"reason definition");
		return cohortDefinition;	  
	  }
  
  public static CohortDefinition cohortByNonEnrollmentReasonOther() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition(COHORT_NON_ENROLLMENT_REASON_OTHER_SQL,"reason definition");
		return cohortDefinition;	  
	  }
  
  //Key populations
  public static CohortDefinition keyPopulationMsm() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition(MSM_COHORT_SQL,"reason definition");
		return cohortDefinition;	  
	  }
  
  public static CohortDefinition keyPopulationSex() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition(SEX_PROFFESIONALS_COHORT_SQL,"reason definition");
		return cohortDefinition;	  
	  }
  
  public static CohortDefinition keyPopulationTransgender() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition(TRANSGENDER__COHORT_SQL,"reason definition");
		return cohortDefinition;	  
	  }
  
  public static CohortDefinition keyPopulationCaptive() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition(CAPTIVES__COHORT_SQL,"reason definition");
		return cohortDefinition;	  
	  }
  
  public static CohortDefinition keyPopulationDrug() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition(INJECTION_DRUG_USERS_COHORT_SQL,"reason definition");
		return cohortDefinition;	  
	  }
 
  // cervical Cancer status
  
  public static CohortDefinition CervicalCancerPostive() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition( WOMEN_TESTED_POSTIVE_FOR_CERVICAL_CANCER_COHORT_SQL,"cervical cancer Postive");
		return cohortDefinition;	  
	  }
  
  public static CohortDefinition CervicalCancerNegative() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition( WOMEN_TESTED_NEGATIVE_FOR_CERVICAL_CANCER_COHORT_SQL,"cervical cancer Negative");
		return cohortDefinition;	  
	  }
  
  public static CohortDefinition CervicalCancerSuspected() {	  
		 SqlCohortDefinition cohortDefinition =  sqlCohortDefinition( WOMEN_WITH_SUSPECTED_CERVICAL_CANCER_COHORT_SQL ,"cervical cancer Suspected");
		return cohortDefinition;	  
	  }
    
}