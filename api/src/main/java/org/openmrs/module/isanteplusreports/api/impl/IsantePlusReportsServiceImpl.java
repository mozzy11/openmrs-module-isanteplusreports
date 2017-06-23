/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.isanteplusreports.api.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.emrapi.EmrApiProperties;
import org.openmrs.module.isanteplusreports.Item;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.isanteplusreports.api.dao.IsantePlusReportsDao;
import org.openmrs.module.isanteplusreports.api.db.IsantePlusReportsDAO;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.definition.DefinitionContext;
import org.openmrs.module.reporting.definition.library.DefinitionLibrary;
import org.openmrs.module.reporting.evaluation.Definition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.indicator.Indicator;
import org.openmrs.module.reporting.indicator.dimension.Dimension;
import org.openmrs.util.HandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class IsantePlusReportsServiceImpl extends BaseOpenmrsService implements IsantePlusReportsService {
	
	IsantePlusReportsDao dao;
	
	UserService userService;
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	@Autowired
	private EmrApiProperties emrApiProperties;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(IsantePlusReportsDao dao) {
		this.dao = dao;
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public Item getItemByUuid(String uuid) throws APIException {
		return dao.getItemByUuid(uuid);
	}
	
	@Override
	public Item saveItem(Item item) throws APIException {
		if (item.getOwner() == null) {
			item.setOwner(userService.getUser(1));
		}
		
		return dao.saveItem(item);
	}
	
	@Override
	public CohortDefinition getCohortDefinition(String uuid) {
		return getDefinition(uuid, CohortDefinition.class);
	}
	
	@Override
	public Indicator getIndicator(String uuid) {
		return getDefinition(uuid, Indicator.class);
	}
	
	@Override
	public Dimension getDimension(String uuid) {
		return getDefinition(uuid, Dimension.class);
	}
	
	/**
	 * @return The definition of the passed uuid and type. This will first check any
	 *         DefinitionLibrary that is defined, and if none found, will check the appropriate
	 *         reporting definition service
	 */
	protected <T extends Definition> T getDefinition(String uuid, Class<T> type) {
		DefinitionLibrary<T> l = HandlerUtil.getPreferredHandler(DefinitionLibrary.class, type);
		if (l != null) {
			return l.getDefinition(uuid);
		}
		return DefinitionContext.getDefinitionByUuid(type, uuid);
	}
	
	@Override
	public DataSet patientListArvByPeriod(Integer id, String startDate, String endDate) {
		
		EvaluationContext context = new EvaluationContext();
		SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
		String result = null;
		if (id == 30) {
			result = " between 0 AND 30";
		}
		if (id == 60) {
			result = " between 31 AND 60";
		}
		if (id == 90) {
			result = " between 61 AND 90";
		}
		if (id == 120) {
			result = " between 91 AND 120";
		}
		if (id == 121) {
			result = " > 120";
		}
		PatientIdentifierType primaryIdentifierType = emrApiProperties.getPrimaryIdentifierType();
		StringBuilder sqlQuery = new StringBuilder("select "
		        + "distinct p.patient_id, p.national_id as national_id, p.given_name as Prénom, p.family_name as Nom");
		sqlQuery.append(" FROM isanteplus.patient p, isanteplus.patient_dispensing pdis, isanteplus.patient_on_arv parv");
		sqlQuery.append(" WHERE p.patient_id=pdis.patient_id");
		sqlQuery.append(" AND pdis.visit_id=parv.visit_id");
		sqlQuery.append(" AND DATEDIFF(pdis.next_dispensation_date,pdis.visit_date)" + result);
		if (startDate != null) {
			sqlQuery.append(" AND DATE(pdis.visit_date) >= '" + startDate + "'");
		}
		if (endDate != null) {
			sqlQuery.append(" AND DATE(pdis.visit_date) <= '" + endDate + "'");
		}
		//SQLQuery query = sessionFactory.getHibernateSessionFactory().getCurrentSession().createSQLQuery(sqlQuery.toString());
		//SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
		//SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
		SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sqlQuery.toString());
		List<Object[]> list = query.list();
		SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
		for (Object[] o : list) {
			DataSetRow row = new DataSetRow();
			row.addColumnValue(new DataSetColumn("patient_id", "patient_id", String.class), o[0]);
			row.addColumnValue(new DataSetColumn("ID_National", "ID_National", String.class), o[1]);
			row.addColumnValue(new DataSetColumn("Nom", "Nom", String.class), o[2]);
			row.addColumnValue(new DataSetColumn("Prenom", "Prenom", String.class), o[3]);
			dataSet.addRow(row);
		}
		return dataSet;
	}
	
}
