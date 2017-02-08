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
package org.openmrs.module.isanteplusreports.api;

import java.sql.Connection;
import java.util.List;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.transaction.annotation.Transactional;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.indicator.Indicator;
import org.openmrs.module.reporting.indicator.dimension.Dimension;

/**
 * This service exposes module's core functionality. It is a Spring managed bean which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(IsantePlusReportsService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface IsantePlusReportsService extends OpenmrsService {
     
	/*
	 * Add service methods here
	 * 
	 */
	/**
	 * @return the CohortDefinition with the passed uuid that is defined in a DefinitionLibrary
	 * if none is defined in a DefinitionLibrary it will query the Reporting definition service
	 */
	public CohortDefinition getCohortDefinition(String uuid);

	/**
	 * @return the Indicator with the passed uuid that is defined in a DefinitionLibrary
	 * if none is defined in a DefinitionLibrary it will query the Reporting definition service
	 */
	public Indicator getIndicator(String uuid);

	/**
	 * @return the Dimension with the passed uuid that is defined in a DefinitionLibrary
	 * if none is defined in a DefinitionLibrary it will query the Reporting definition service
	 */
public Dimension getDimension(String uuid);
	
	public Connection getConnection();
	public List<Parameter> parametersList(String queryString);
	public DataSet evaluateList();
	public DataSet patientTrancheAge();
	public DataSet numberPatient();
	public DataSet nextVisitSevenDays();
	public DataSet nextVisitFourteenDays();
}