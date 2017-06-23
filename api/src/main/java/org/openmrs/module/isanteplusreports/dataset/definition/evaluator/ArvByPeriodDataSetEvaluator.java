/*
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

package org.openmrs.module.isanteplusreports.dataset.definition.evaluator;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.openmrs.annotation.Handler;
import org.openmrs.api.PatientService;
import org.openmrs.api.UserService;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.emrapi.EmrApiProperties;
import org.openmrs.module.isanteplusreports.dataset.definitions.ArvByPeriodDataSetDefinition;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.evaluator.DataSetEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.openmrs.module.isanteplusreports.api.db.IsantePlusReportsDAO;

import java.util.Date;
import java.util.List;

@Handler(supports = ArvByPeriodDataSetDefinition.class)
public class ArvByPeriodDataSetEvaluator implements DataSetEvaluator {
	
	private final Log log = LogFactory.getLog(getClass());
	
	//private IsantePlusReportsDAO dao;
	private IsantePlusReportsDAO dao;
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(IsantePlusReportsDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * @return the dao
	 */
	public IsantePlusReportsDAO getDao() {
		return dao;
	}
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	@Autowired
	private EmrApiProperties emrApiProperties;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PatientService patientService;
	
	/*
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}*/
	
	/**
	 * @return the sessionFactory
	 */
	/*public SessionFactory getSessionFactory() {
		return sessionFactory;
	}*/
	
	@Override
	public DataSet evaluate(DataSetDefinition dataSetDefinition, EvaluationContext context) throws EvaluationException {
		Long startTime = new Date().getTime();
		ArvByPeriodDataSetDefinition dsd = (ArvByPeriodDataSetDefinition) dataSetDefinition;
		Date startDate = ObjectUtil.nvl(dsd.getStartDate(), DateUtils.addDays(new Date(), -7));
		Date endDate = ObjectUtil.nvl(dsd.getEndDate(), new Date());
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		//PatientIdentifierType primaryIdentifierType = emrApiProperties.getPrimaryIdentifierType();
		StringBuilder sqlQuery = new StringBuilder(
		        "select "
		                + "count(distinct case when DATEDIFF(pdis.next_dispensation_date,pdis.visit_date) between 0 AND 30 THEN p.patient_id END) as '0-30 jours',"
		                + "count(distinct case when DATEDIFF(pdis.next_dispensation_date,pdis.visit_date) between 31 AND 60 THEN p.patient_id END) as '31-60 jours',"
		                + "count(distinct case when DATEDIFF(pdis.next_dispensation_date,pdis.visit_date) between 61 AND 90 THEN p.patient_id END) as '61-90 jours',"
		                + "count(distinct case when DATEDIFF(pdis.next_dispensation_date,pdis.visit_date) between 91 AND 120 THEN p.patient_id END) as '91-120 jours',"
		                + "count(distinct case when DATEDIFF(pdis.next_dispensation_date,pdis.visit_date) > 120 THEN p.patient_id END) as '>120 jours',count(distinct p.patient_id) as 'Patient unique'");
		sqlQuery.append(" FROM isanteplus.patient p, isanteplus.patient_dispensing pdis, isanteplus.patient_on_arv parv");
		sqlQuery.append(" WHERE p.patient_id=pdis.patient_id");
		sqlQuery.append(" AND pdis.visit_id=parv.visit_id");
		if (startDate != null) {
			sqlQuery.append(" AND pdis.visit_date >= :startDate");
		}
		if (endDate != null) {
			sqlQuery.append(" AND pdis.visit_date <= :endDate");
		}
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
		//query.setInteger("primaryIdentifierType", primaryIdentifierType.getId());
		if (startDate != null) {
			query.setTimestamp("startDate", startDate);
		}
		if (startDate != null) {
			query.setTimestamp("endDate", endDate);
		}
		
		List<Object[]> list = query.list();
		SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
		for (Object[] o : list) {
			DataSetRow row = new DataSetRow();
			row.addColumnValue(new DataSetColumn("0-30 jours", "0-30 jours", String.class), o[0]);
			row.addColumnValue(new DataSetColumn("31-60 jours", "31-60 jours", String.class), o[1]);
			row.addColumnValue(new DataSetColumn("61-90 jours", "61-90 jours", String.class), o[2]);
			row.addColumnValue(new DataSetColumn("91-120 jours", "91-120 jours", String.class), o[3]);
			row.addColumnValue(new DataSetColumn(">120", ">120", String.class), o[4]);
			dataSet.addRow(row);
		}
		return dataSet;
	}
	
	public DataSet patientListArvByPeriod(Integer id, Date startD, Date endD, DataSetDefinition dataSetDefinition,
	        EvaluationContext context) throws EvaluationException {
		
		ArvByPeriodDataSetDefinition dsd = (ArvByPeriodDataSetDefinition) dataSetDefinition;
		Date startDate = ObjectUtil.nvl(dsd.getStartDate(), startD);
		Date endDate = ObjectUtil.nvl(dsd.getEndDate(), endD);
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		/*EvaluationContext context = new EvaluationContext();
		SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();*/
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
		
		StringBuilder sqlQuery = new StringBuilder("select "
		        + "p.national_id as national_id, p.given_name as Prénom, p.family_name as Nom");
		sqlQuery.append(" FROM isanteplus.patient p, isanteplus.patient_dispensing pdis, isanteplus.patient_on_arv parv");
		sqlQuery.append(" WHERE p.patient_id=pdis.patient_id");
		sqlQuery.append(" AND pdis.visit_id=parv.visit_id");
		/*sqlQuery.append(" DATEDIFF(pdis.next_dispensation_date,pdis.visit_date)" + result);
		if (startDate != null) {
			sqlQuery.append(" AND pdis.visit_date >= " + startDate);
		}
		if (endDate != null) {
			sqlQuery.append(" AND pdis.visit_date <= " + endDate);
		}
		*/
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
		
		//query.setInteger("primaryIdentifierType", primaryIdentifierType.getId());
		/*	if (startDate != null) {
				query.setTimestamp("startDate", startDate);
			}
			if (startDate != null) {
				query.setTimestamp("endDate", endDate);
			}
			*/
		
		List<Object[]> list = query.list();
		SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
		for (Object[] o : list) {
			DataSetRow row = new DataSetRow();
			row.addColumnValue(new DataSetColumn("ID National", "ID National", String.class), o[0]);
			row.addColumnValue(new DataSetColumn("Nom", "Nom", String.class), o[1]);
			row.addColumnValue(new DataSetColumn("Prenom", "Prenom", String.class), o[2]);
			dataSet.addRow(row);
		}
		return dataSet;
	}
	
}
