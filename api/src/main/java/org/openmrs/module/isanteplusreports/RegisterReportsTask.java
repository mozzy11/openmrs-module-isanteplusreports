package org.openmrs.module.isanteplusreports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.isanteplusreports.util.RegisterAllReports;

//public class RegisterReportsTask extends SessionTask {
public class RegisterReportsTask {
	
	private static Log log = LogFactory.getLog(RegisterReportsTask.class);
	
	/**
	 * Does the actual data aggregation
	 */
	//protected void onExecute() {
	public void onExecute() {
		try {
			RegisterAllReports register = new RegisterAllReports();
			register.cleanTables();
			register.nextVisitSevenDays();
			register.nextVisitFourteenDays();
			register.patientAgeGroup();
			register.firstVisitAge();
			register.patientsCrachatAnormalWithoutTbDiagnostic();
			register.patientsWithTbDiagnosticsWithoutTreatment();
			register.patientsWithCompletedTbTreatment();
			register.patientsWithTbSymptomsignWithoutCrachat();
			register.consultationByDay();
			register.numberVisitsByMonth();
			register.numberPatientBySex();
			register.dispensingMedications();
			register.patientsStatusList();
		}
		catch (Exception ex) {
			log.error("One of reports has an error which blocks it and other reports to be registered");
			ex.printStackTrace();
		}
	}
	
}
