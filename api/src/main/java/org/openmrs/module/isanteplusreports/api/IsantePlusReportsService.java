/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.isanteplusreports.api;

import java.util.Date;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.isanteplus.IsantePlusObs;
import org.openmrs.module.isanteplusreports.IsantePlusReportsConfig;
import org.openmrs.module.isanteplusreports.IsantePlusReportsObs;
import org.openmrs.module.isanteplusreports.Item;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.indicator.Indicator;
import org.openmrs.module.reporting.indicator.dimension.Dimension;
import org.springframework.transaction.annotation.Transactional;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface IsantePlusReportsService extends OpenmrsService {
	
	/**
	 * Returns an item by uuid. It can be called by any authenticated user. It is fetched in read
	 * only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized()
	@Transactional(readOnly = true)
	Item getItemByUuid(String uuid) throws APIException;
	
	/**
	 * Saves an item. Sets the owner to superuser, if it is not set. It can be called by users with
	 * this module's privilege. It is executed in a transaction.
	 * 
	 * @param item
	 * @return
	 * @throws APIException
	 */
	@Authorized(IsantePlusReportsConfig.MODULE_PRIVILEGE)
	@Transactional
	Item saveItem(Item item) throws APIException;
	
	/**
	 * @return the CohortDefinition with the passed uuid that is defined in a DefinitionLibrary if
	 *         none is defined in a DefinitionLibrary it will query the Reporting definition service
	 */
	public CohortDefinition getCohortDefinition(String uuid);
	
	/**
	 * @return the Indicator with the passed uuid that is defined in a DefinitionLibrary if none is
	 *         defined in a DefinitionLibrary it will query the Reporting definition service
	 */
	public Indicator getIndicator(String uuid);
	
	/**
	 * @return the Dimension with the passed uuid that is defined in a DefinitionLibrary if none is
	 *         defined in a DefinitionLibrary it will query the Reporting definition service
	 */
	public Dimension getDimension(String uuid);
	
	//DataSet patientListArvByPeriod(Integer id, Date startDate, Date endDate);
	
	DataSet patientListArvByPeriod(Integer id, String startDate, String endDate);

	DataSet lastSixForms(Patient p);

	DataSet firstVisitForms(Patient p);

	List<IsantePlusReportsObs> getLabsResult(Patient patient);
	
	//DataSet patientListArvByPeriod(int parseInt, Date startDate, Date endDate);
	
}
