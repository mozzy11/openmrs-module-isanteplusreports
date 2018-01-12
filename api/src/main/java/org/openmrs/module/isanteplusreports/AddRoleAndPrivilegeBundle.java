package org.openmrs.module.isanteplusreports;

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.idSet;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.privilege;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.role;

@Component("reportsRolePrivilegeMetadata")
public class AddRoleAndPrivilegeBundle extends AbstractMetadataBundle {

	 public static class _Privilege {
	        public static final String REPORTS_PRIVILEGE = "App: reportingui.reports"; 
	        public static final String VIEW_REPORTS = "App: reportingui.reports";
	    }

	    public static class _Role {
	    		public static final String REPORTS_ROLES = "Application: View reports";
	    }

	
	@Override
	public void install() throws Exception {
		// TODO Auto-generated method stub
		install(privilege(_Privilege.REPORTS_PRIVILEGE, "Able to access reports"));
		
		install(role(_Role.REPORTS_ROLES, "Able to view reports", idSet(), idSet(
                _Privilege.REPORTS_PRIVILEGE)));
		
	}

}
