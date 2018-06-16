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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.emrapi.EmrApiProperties;
import org.openmrs.module.isanteplus.IsantePlusObs;
import org.openmrs.module.isanteplusreports.IsantePlusReportsObs;
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
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.Indicator;
import org.openmrs.module.reporting.indicator.dimension.Dimension;
import org.openmrs.util.HandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class IsantePlusReportsServiceImpl extends BaseOpenmrsService implements IsantePlusReportsService {
	
	IsantePlusReportsDao dao;
	
	UserService userService;
	
	static Parameter id = new Parameter("id", "id", Integer.class);
	
	//EvaluationContext context = new EvaluationContext();
	//SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	/*@Autowired
	private EmrApiProperties emrApiProperties;*/
	
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
		if (id == 35) {
			result = " between 0 AND 35";
		}
		if (id == 90) {
			result = " between 36 AND 89";
		}
		if (id == 120) {
			result = " between 90 AND 120";
		}
		if (id == 180) {
			result = " between 121 AND 180";
		}
		if (id == 181) {
			result = " > 180";
		}
		//PatientIdentifierType primaryIdentifierType = emrApiProperties.getPrimaryIdentifierType();
		StringBuilder sqlQuery = new StringBuilder("select "
		        + "distinct p.st_id as st_id, p.patient_id, p.national_id as national_id, p.given_name as Prénom, p.family_name as Nom, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Age, p.gender as Sexe");
		sqlQuery.append(" FROM isanteplus.patient p, isanteplus.patient_dispensing pdis, isanteplus.patient_on_arv parv, (select pdisp.patient_id, MAX(pdisp.visit_date) as visit_date FROM isanteplus.patient_dispensing pdisp WHERE pdisp.arv_drug=1065 AND pdisp.visit_date BETWEEN '" + startDate + "' AND '" + endDate + "' GROUP BY 1) B  ");
		sqlQuery.append(" WHERE p.patient_id=pdis.patient_id");
		sqlQuery.append(" AND pdis.patient_id=parv.patient_id");
		sqlQuery.append(" AND pdis.patient_id=B.patient_id");
		sqlQuery.append(" AND pdis.visit_date=B.visit_date");
		sqlQuery.append(" AND p.patient_id NOT IN (SELECT ei.patient_id FROM isanteplus.exposed_infants ei)");
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
			row.addColumnValue(new DataSetColumn("st_id", "st_id", String.class), o[0]);
			row.addColumnValue(new DataSetColumn("patient_id", "patient_id", String.class), o[1]);
			row.addColumnValue(new DataSetColumn("ID_National", "ID_National", String.class), o[2]);
			row.addColumnValue(new DataSetColumn("Nom", "Nom", String.class), o[3]);
			row.addColumnValue(new DataSetColumn("Prenom", "Prenom", String.class), o[4]);
			row.addColumnValue(new DataSetColumn("Age", "Age", String.class), o[5]);
			row.addColumnValue(new DataSetColumn("Sexe", "Sexe", String.class), o[6]);
			dataSet.addRow(row);
		}
		return dataSet;
	}
	
	@Override
	public DataSet lastSixForms(Patient p) {
		EvaluationContext context = new EvaluationContext();
		SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
		 id.setDefaultValue(p.getPatientId());
		StringBuilder sqlQuery = new StringBuilder(
		        "select distinct"
		                + " DATE_FORMAT(enc.date_created, '%d-%m-%Y') as 'Date de création',"
		                + " entype.name as Type");
		sqlQuery.append(" FROM openmrs.encounter enc, openmrs.encounter_type entype");
		sqlQuery.append(" WHERE enc.encounter_type = entype.encounter_type_id");
		sqlQuery.append(" AND TIMESTAMPDIFF(MONTH, DATE(enc.date_created),DATE(now())) <= 6");
		sqlQuery.append(" AND entype.uuid NOT IN('17536ba6-dd7c-4f58-8014-08c7cb798ac7',"
				+ "'349ae0b4-65c1-4122-aa06-480f186c8350','709610ff-5e39-4a47-9c27-a60e740b0944',"
				+ "'5c312603-25c1-4dbe-be18-1a167eb85f97','873f968a-73a8-4f9c-ac78-9f4778b751b6','12f4d7c3-e047-4455-a607-47a40fe32460')");
		sqlQuery.append(" AND enc.patient_id = '" + p.getPatientId() + "'");
		sqlQuery.append(" ORDER BY DATE(enc.date_created) DESC");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
		//query.setInteger("primaryIdentifierType", primaryIdentifierType.getId());
		List<Object[]> list = query.list();
		SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
		for (Object[] o : list) {
			DataSetRow row = new DataSetRow();
			row.addColumnValue(new DataSetColumn("creation", "creation", String.class), o[0]);
			row.addColumnValue(new DataSetColumn("fiches", "fiches", String.class), o[1]);
			dataSet.addRow(row);
		}
		return dataSet;
	}
	
	@Override
	public DataSet firstVisitForms(Patient p) {
		EvaluationContext context = new EvaluationContext();
		SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
		 id.setDefaultValue(p.getPatientId());
		StringBuilder sqlQuery = new StringBuilder(
		        "select distinct"
		                + " DATE_FORMAT(enc.date_created, '%d-%m-%Y') as 'Date de création',"
		                + " entype.name as Type");
		sqlQuery.append(" FROM openmrs.encounter enc, openmrs.encounter_type entype");
		sqlQuery.append(" WHERE enc.encounter_type = entype.encounter_type_id");
		sqlQuery.append(" AND entype.uuid IN('17536ba6-dd7c-4f58-8014-08c7cb798ac7',"
				+ "'349ae0b4-65c1-4122-aa06-480f186c8350','709610ff-5e39-4a47-9c27-a60e740b0944',"
				+ "'5c312603-25c1-4dbe-be18-1a167eb85f97','12f4d7c3-e047-4455-a607-47a40fe32460')");
		sqlQuery.append(" AND enc.patient_id = '" + p.getPatientId() + "'");
		sqlQuery.append(" ORDER BY DATE(enc.date_created) DESC");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
		//query.setInteger("primaryIdentifierType", primaryIdentifierType.getId());
		List<Object[]> list = query.list();
		SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
		for (Object[] o : list) {
			DataSetRow row = new DataSetRow();
			row.addColumnValue(new DataSetColumn("creationDate", "creationDate", String.class), o[0]);
			row.addColumnValue(new DataSetColumn("ficheName", "ficheName", String.class), o[1]);
			dataSet.addRow(row);
		}
		return dataSet;
	}
	@Override
	public List<IsantePlusReportsObs> getLabsResult(Patient patient) {
		// TESTS ORDERED = 1271
		List<IsantePlusReportsObs> labHistory = new ArrayList<IsantePlusReportsObs>();
		Integer labConceptId = 1271;
		Concept testsOrdered = Context.getConceptService().getConcept(labConceptId);

		for (Obs obs : Context.getObsService().getObservationsByPersonAndConcept(patient.getPerson(), testsOrdered)) {
			if (obs != null) {

				//Integer result = Integer.parseInt(obs.getValueCoded().toString());
				Integer result = obs.getValueCoded().getConceptId();
				Concept resultTest = Context.getConceptService().getConcept(result);

				for (Obs obs1 : Context.getObsService().getObservationsByPersonAndConcept(patient.getPerson(), resultTest)) {
					if (obs.getEncounter().getEncounterId() == obs1.getEncounter().getEncounterId()) {
						IsantePlusReportsObs obsres = new IsantePlusReportsObs(obs1);
						labHistory.add(obsres);

					}
				}
			}
		}
		return labHistory;
	}
	
}
