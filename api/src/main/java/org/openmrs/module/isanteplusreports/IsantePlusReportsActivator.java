/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.isanteplusreports;


import java.sql.Connection;
/*import java.util.Calendar;
import java.util.UUID;*/

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;
/*import org.hibernate.SessionFactory;
import org.openmrs.api.context.Context;*/
import org.openmrs.module.ModuleActivator;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.isanteplusreports.api.db.IsantePlusReportsDAO;
/*import org.openmrs.scheduler.SchedulerException;
import org.openmrs.scheduler.Task;
import org.openmrs.scheduler.TaskDefinition;*/

/**
 * This class contains the logic that is run every time this module is either started or stopped.
 */
public class IsantePlusReportsActivator extends BaseModuleActivator {
	
	//protected Log log = LogFactory.getLog(getClass());
	//private static Log log = LogFactory.getLog(IsantePlusReportsActivator.class);
	private static Log log = LogFactory.getLog(IsantePlusReportsActivator.class);
	//protected Log log = LogFactory.getLog(getClass());
	
	private static IsantePlusReportsDAO dao;
	/**
     * @param dao the dao to set
     */
    public void setDao(IsantePlusReportsDAO dao) {
	    IsantePlusReportsActivator.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public IsantePlusReportsDAO getDao() {
	    return dao;
    }
  
   public Connection getConnection()
    {
    	return dao.getConnection();
    }
   
	/**
	 * @see ModuleActivator#willRefreshContext()
	 */
	public void willRefreshContext() {
		log.info("Refreshing IsantePlusReports Module");
	}
	
	/**
	 * @see ModuleActivator#contextRefreshed()
	 */
	public void contextRefreshed() {
		log.info("IsantePlusReports Module refreshed");
	}
	
	/**
	 * @see ModuleActivator#willStart()
	 */
	public void willStart() {
		log.info("Starting IsantePlusReports Module");
	}
	
	/**
	 * @see ModuleActivator#started()
	 */
	public void started() {
		log.info("IsantePlusReports Module started");
		//essai();
		//registerTask("Register Reports", "Registers report definitions", RegisterReports.class, 60 * 60 * 24l);
		//registerTask("Register Reports", "Registers report definitions", RegisterReportsTask.class);
		//exemple();
		RegisterReportsTask register = new RegisterReportsTask();
		try {
			register.onExecute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @see ModuleActivator#willStop()
	 */
	public void willStop() {
		log.info("Stopping IsantePlusReports Module");
	}
	
	/**
	 * @see ModuleActivator#stopped()
	 */
	public void stopped() {
		log.info("IsantePlusReports Module stopped");
	}
	/*private boolean registerTask(String name, String description, Class<? extends Task> clazz) {
		try {
			Context.addProxyPrivilege("Manage Scheduler");
		
			TaskDefinition taskDef = Context.getSchedulerService().getTaskByName(name);
			if (taskDef == null) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MINUTE, 1);
				taskDef = new TaskDefinition();
				taskDef.setTaskClass(clazz.getCanonicalName());
				taskDef.setStartOnStartup(true);
				taskDef.setStarted(true);
				taskDef.setStartTime(cal.getTime());
				taskDef.setName(name);
				taskDef.setUuid(UUID.randomUUID().toString()); 
				taskDef.setDescription(description);
				Context.getSchedulerService().scheduleTask(taskDef);
			}
			
		} catch (SchedulerException ex) {
			log.warn("Unable to register task '" + name + "' with scheduler", ex);
			return false;
		} finally {
			Context.removeProxyPrivilege("Manage Scheduler");
		}
		return true;
	}*/
	
		
}
