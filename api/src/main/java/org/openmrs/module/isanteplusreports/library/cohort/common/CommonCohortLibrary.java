package org.openmrs.module.isanteplusreports.library.cohort.common;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.GenderCohortDefinition;

public class CommonCohortLibrary {
	  /**
	   * Patients who are female
	   *
	   * @return the cohort definition
	   */
	  public CohortDefinition femaleCohort() {
	    GenderCohortDefinition cohort = new GenderCohortDefinition();
	    cohort.setName("femaleCohort");
	    cohort.setFemaleIncluded(true);
	    cohort.setMaleIncluded(false);
	    return cohort;
	  }

	  /**
	   * Patients who are male
	   *
	   * @return the cohort definition
	   */
	  public CohortDefinition maleCohort() {
	    GenderCohortDefinition cohort = new GenderCohortDefinition();
	    cohort.setName("maleCohort");
	    cohort.setMaleIncluded(true);
	    cohort.setFemaleIncluded(false);
	    return cohort;
	  }	
}
