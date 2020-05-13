package org.openmrs.module.isanteplusreports.library.dimension;



import java.util.Date;

import org.openmrs.module.isanteplusreports.library.cohort.common.CommonCohortLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
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
	  
	  public CohortDefinitionDimension ageZone() {
		    CohortDefinitionDimension dim = new CohortDefinitionDimension();
		    CommonCohortLibrary ageCohortLibrary = new CommonCohortLibrary();
		    dim.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		    dim.setName("age");
			dim.addCohortDefinition("0_1", ReportUtils.map(ageCohortLibrary.ageRangeCohort(0, 1), "effectiveDate=${endDate}"));
		    dim.addCohortDefinition("1_4", ReportUtils.map(ageCohortLibrary.ageRangeCohort(1, 4), "effectiveDate=${endDate}"));
		    dim.addCohortDefinition("5_9", ReportUtils.map(ageCohortLibrary.ageRangeCohort(5, 9), "effectiveDate=${endDate}"));
		    dim.addCohortDefinition("10_14", ReportUtils.map(ageCohortLibrary.ageRangeCohort(10, 14),"effectiveDate=${endDate}"));
		    dim.addCohortDefinition("15_19", ReportUtils.map(ageCohortLibrary.ageRangeCohort(15, 19), "effectiveDate=${endDate}"));
		    dim.addCohortDefinition("20_24", ReportUtils.map(ageCohortLibrary.ageRangeCohort(20, 24), "effectiveDate=${endDate}"));
		    dim.addCohortDefinition("25_29", ReportUtils.map(ageCohortLibrary.ageRangeCohort(25, 29), "effectiveDate=${endDate}"));
		    dim.addCohortDefinition("30_34", ReportUtils.map(ageCohortLibrary.ageRangeCohort(30, 34), "effectiveDate=${endDate}"));
		    dim.addCohortDefinition("35_39", ReportUtils.map(ageCohortLibrary.ageRangeCohort(35, 39), "effectiveDate=${endDate}"));
		    dim.addCohortDefinition("40_44", ReportUtils.map(ageCohortLibrary.ageRangeCohort(40, 44), "effectiveDate=${endDate}"));
		    dim.addCohortDefinition("45_49", ReportUtils.map(ageCohortLibrary.ageRangeCohort(45, 49), "effectiveDate=${endDate}"));
		    dim.addCohortDefinition("50", ReportUtils.map(ageCohortLibrary.agedAtLeastCohort(50), "effectiveDate=${endDate}"));
		    dim.addCohortDefinition("unknown", ReportUtils.map(ageCohortLibrary.unknownAgeCohort(), ""));
		    return dim;
		  }
	
}
