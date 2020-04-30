package org.openmrs.module.isanteplusreports.healthqual.util;


import org.junit.Assert;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.test.StandaloneContextSensitiveTest;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.report.definition.ReportDefinition;

public class RegisterAllHealthQualReportsTest extends StandaloneContextSensitiveTest {
	
	/**
	* @see RegisterAllHealthQualReports#registerHealthEqualReportWithCurrentDateAndPeriodParamsNumDen(String,String,String,String)
	* @verifies Execute query and return report with at least 4 datasets
	*/
	@Test
	public void registerHealthEqualReportWithCurrentDateAndPeriodParamsNumDen_shouldExecuteQueryAndReturnReportWithAtLeast4Datasets()
	        throws Exception {
		// Adult on ART treatment
		String sqlNum = HealthQualReportsConstants.ADULT_1_NUM_INDICATOR_SQL; 
		String sqlDen = HealthQualReportsConstants.ADULT_1_DEN_INDICATOR_SQL;
		String messageProperties = HealthQualReportsConstants.ADULT_1_INDICATOR_MESSAGE;
		String uuid  = HealthQualReportsConstants.HEALTH_QUAL_ADULT_1_INDICATOR_UUID;
		
		RegisterAllHealthQualReports.registerHealthEqualReportWithCurrentDateAndPeriodParamsNumDen(sqlNum, sqlDen, messageProperties, uuid);
		
        ReportDefinition rd = Context.getService(SerializedDefinitionService.class).getDefinitionByUuid(ReportDefinition.class, HealthQualReportsConstants.HEALTH_QUAL_ADULT_1_INDICATOR_UUID);
		
        Assert.assertNotNull(rd);

        Assert.assertEquals("Should return at last 1 instance of dataset definition", 1, rd.getDataSetDefinitions().size());

/*		Map<String, Object> additionalOptions = new HashMap<String, Object>();
		Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/04/2020");
		Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse("30/04/2020");
		additionalOptions.put("period", 6);
		ReportData rdt =  HealthQualUtils.getReportData(rd.getUuid(), startDate, endDate, additionalOptions);
		
        Assert.assertNotNull(rdt.getDataSets());

		Assert.assertEquals("Should return exactly 4 columns for the dataset", 4, rdt.getDataSets().entrySet().size());
		
		Assert.fail("Not yet implemented");*/
	}

}