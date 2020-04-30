package org.openmrs.module.isanteplusreports.library.dimension;

import org.openmrs.module.isanteplusreports.library.cohort.common.CommonCohortLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension;

public class CommonDimension {

	/**
	   * Gender dimension
	   *
	   * @return the {@link org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension}
	   */
	  public CohortDefinitionDimension gender() {
	    CohortDefinitionDimension dim = new CohortDefinitionDimension();
	    CommonCohortLibrary genderCohortLibrary = new CommonCohortLibrary();
	    dim.setName("gender");
		dim.addCohortDefinition("M", ReportUtils.map(genderCohortLibrary.maleCohort(), ""));
	    dim.addCohortDefinition("F", ReportUtils.map(genderCohortLibrary.femaleCohort(), ""));
	    return dim;
	  }
	
}
