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
package org.openmrs.module.isanteplusreports.api.impl;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.isanteplusreports.api.db.IsantePlusReportsDAO;
import org.openmrs.module.reporting.cohort.query.service.CohortQueryService;
//import org.openmrs.module.emrapi.EmrApiProperties;
//import org.openmrs.module.isanteplusreports.dataset.definition.NonCodedDiagnosisDataSetDefinition;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.definition.DefinitionContext;
import org.openmrs.module.reporting.definition.library.DefinitionLibrary;
import org.openmrs.module.reporting.evaluation.Definition;
import org.openmrs.module.reporting.indicator.Indicator;
import org.openmrs.module.reporting.indicator.dimension.Dimension;
import org.openmrs.util.HandlerUtil;


;/**
 * It is a default implementation of {@link IsantePlusReportsService}.
 */
public class IsantePlusReportsServiceImpl extends BaseOpenmrsService implements IsantePlusReportsService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
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
  
   public Connection getConnection()
    {
    	return dao.getConnection();
    }
   
   private SessionFactory sessionFactory;
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
	 * @return The definition of the passed uuid and type.  This will first check any
	 * DefinitionLibrary that is defined, and if none found, will check the appropriate
	 * reporting definition service
	 */
	protected <T extends Definition> T getDefinition(String uuid, Class<T> type) {
		@SuppressWarnings("unchecked")
		DefinitionLibrary<T> l = HandlerUtil.getPreferredHandler(DefinitionLibrary.class, type);
		if (l != null) {
			return l.getDefinition(uuid);
		}
		return DefinitionContext.getDefinitionByUuid(type, uuid);
	}
   
   
   public List<Parameter> parametersList(String queryString)
   {
	   List<Parameter> parameters =  
				Context.getService(CohortQueryService.class).getNamedParameters(queryString);
		return parameters;
   }
   @SuppressWarnings({ "unchecked" })
public DataSet evaluateList() {
	   sessionFactory = dao.getFactorySessionValue();
	    EvaluationContext context = new EvaluationContext();
	    SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
		//NonCodedDiagnosisDataSetDefinition dsd = (NonCodedDiagnosisDataSetDefinition) dataSetDefinition;

		/*Date fromDate = ObjectUtil.nvl(dsd.getFromDate(), DateUtils.addDays(new Date(), -7));
		Date toDate = ObjectUtil.nvl(dsd.getToDate(), new Date());
		fromDate = DateUtil.getStartOfDay(fromDate);
		toDate = DateUtil.getEndOfDay(toDate);
        String nonCoded = ObjectUtil.nvl(dsd.getNonCoded(),null);
        Provider provider = ObjectUtil.nvl(dsd.getProvider(), null);*/
       
       // PatientIdentifierType primaryIdentifierType = emrApiProperties.getPrimaryIdentifierType();
       // Concept nonCodedConcept = emrApiProperties.getDiagnosisMetadata().getNonCodedDiagnosisConcept();

        StringBuilder sqlQuery = new StringBuilder("select " +
                "    o.value_text as 'nonCodedDiagnosis', " +
                "    o.creator as 'creatorId', " +
                "    n.given_name as 'creatorFirstName', " +
                "    n.family_name as 'creatorLastName', " +
                "    o.date_created as 'dateCreated', " +
                "    o.person_id as 'patientId', " +
                "    id1.identifier as 'patientIdentifier', " +
                "    o.obs_id as 'obsId', " +
                "    e.visit_id as 'visitId', " +
                "    e.encounter_datetime as 'encounterDateTime', " +
                "    n1.given_name as 'patientFirstName', " +
                "    n1.family_name as 'patientLastName'");
        sqlQuery.append(" from obs o ");
        sqlQuery.append(" inner join patient_identifier as id1 on (o.person_id = id1.patient_id) ");
        sqlQuery.append(" inner join encounter as e on (o.encounter_id = e.encounter_id) ");
        sqlQuery.append(" inner join users as u on (o.creator = u.user_id) ");
        sqlQuery.append(" inner join person_name as n on (u.person_id = n.person_id and n.voided=0) " );
        sqlQuery.append(" inner join person_name as n1 on (o.person_id = n1.person_id and n1.voided=0) " );
       
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
      
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("nonCodedDiagnosis", "nonCodedDiagnosis", String.class), o[0]);
            row.addColumnValue(new DataSetColumn("creatorId", "creatorId", String.class), o[1]);
            row.addColumnValue(new DataSetColumn("creatorFirstName", "creatorFirstName", String.class), o[2]);
            row.addColumnValue(new DataSetColumn("creatorLastName", "creatorLastName", String.class), o[3]);
            row.addColumnValue(new DataSetColumn("dateCreated", "dateCreated", String.class), o[4]);
            row.addColumnValue(new DataSetColumn("patientId", "patientId", String.class), o[5]);
            row.addColumnValue(new DataSetColumn("patientIdentifier", "patientIdentifier", String.class), o[6]);
            row.addColumnValue(new DataSetColumn("obsId", "obsId", String.class), o[7]);
            row.addColumnValue(new DataSetColumn("visitId", "visitId", String.class), o[8]);
            row.addColumnValue(new DataSetColumn("encounterDateTime", "encounterDateTime", String.class), o[9]);
            row.addColumnValue(new DataSetColumn("patientFirstName", "patientFirstName", String.class), o[10]);
            row.addColumnValue(new DataSetColumn("patientLastName", "patientLastName", String.class), o[11]);
            dataSet.addRow(row);
        }
		return dataSet;
	}
   
   @SuppressWarnings({ "unchecked" })
   public DataSet patientTrancheAge() {
	   sessionFactory = dao.getFactorySessionValue();
	    EvaluationContext context = new EvaluationContext();
	    SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
        StringBuilder sqlQuery = new StringBuilder("select " +
        		"count(p.patient_id) as nbpatient,"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now()))<15 THEN p.patient_id else null END) AS '0-14'," +
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 15 AND 20 THEN p.patient_id else null END) AS '15-20',"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 21 AND 30 THEN p.patient_id else null END) AS '21-30',"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 31 AND 40 THEN p.patient_id else null END) AS '31-40',"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 41 AND 50 THEN p.patient_id else null END) AS '41-50',"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 51 AND 60 THEN p.patient_id else null END) AS '51-60',"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 61 AND 70 THEN p.patient_id else null END) AS '61-70',"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 71 AND 80 THEN p.patient_id else null END) AS '71-80',"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 81 AND 90 THEN p.patient_id else null END) AS '81-90',"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 91 AND 100 THEN p.patient_id else null END) AS '91-100',"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 101 AND 110 THEN p.patient_id else null END) AS '101-110',"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 111 AND 120 THEN p.patient_id else null END) AS '111-120',"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) between 121 AND 130 THEN p.patient_id else null END) AS '121-130',"+
        		"COUNT(CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,now())) > 130 THEN p.patient_id else null END) AS '>130'");
        sqlQuery.append(" FROM isanteplus.patient p ");
       
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
       
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            if(Integer.parseInt(o[1].toString())>0)
           row.addColumnValue(new DataSetColumn("0-14", "0-14", String.class), o[1]);
            if(Integer.parseInt(o[2].toString())>0)
           row.addColumnValue(new DataSetColumn("15-20", "15-20", String.class), o[2]);
            if(Integer.parseInt(o[3].toString())>0)
           row.addColumnValue(new DataSetColumn("21-30", "21-30", String.class), o[3]);
            if(Integer.parseInt(o[4].toString())>0)
           row.addColumnValue(new DataSetColumn("31-40", "31-40", String.class), o[4]);
            if(Integer.parseInt(o[5].toString())>0)
           row.addColumnValue(new DataSetColumn("41-50", "41-50", String.class), o[5]);
            if(Integer.parseInt(o[6].toString())>0)
           row.addColumnValue(new DataSetColumn("51-60", "51-60", String.class), o[6]);
            if(Integer.parseInt(o[7].toString())>0)
           row.addColumnValue(new DataSetColumn("61-70", "61-70", String.class), o[7]);
            if(Integer.parseInt(o[8].toString())>0)
           row.addColumnValue(new DataSetColumn("71-80", "71-80", String.class), o[8]);
            if(Integer.parseInt(o[9].toString())>0)
           row.addColumnValue(new DataSetColumn("81-90", "81-90", String.class), o[9]);
            if(Integer.parseInt(o[10].toString())>0)
           row.addColumnValue(new DataSetColumn("91-100", "91-100", String.class), o[10]);
            if(Integer.parseInt(o[11].toString())>0)
           row.addColumnValue(new DataSetColumn("101-110", "101-110", String.class), o[11]);
            if(Integer.parseInt(o[12].toString())>0)
           row.addColumnValue(new DataSetColumn("111-120", "111-120", String.class), o[12]);
            if(Integer.parseInt(o[13].toString())>0)
           row.addColumnValue(new DataSetColumn("121-130", "121-130", String.class), o[13]);
            if(Integer.parseInt(o[14].toString())>0)
           row.addColumnValue(new DataSetColumn(">130", ">130", String.class), o[14]);
           row.addColumnValue(new DataSetColumn("", "", String.class), o[0]);
          
          // i++;
            dataSet.addRow(row);
        }
		return dataSet;
	}
   
   @SuppressWarnings("unchecked")
public DataSet numberPatient() {
	   sessionFactory = dao.getFactorySessionValue();
	    EvaluationContext context = new EvaluationContext();
	    SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
        StringBuilder sqlQuery = new StringBuilder("SELECT " +
        		" loc.name as clinic,COUNT(Distinct pai.patient_id) as nbpatient ");
        sqlQuery.append(" from location loc, patient_identifier pai ");
        sqlQuery.append(" WHERE loc.location_id=pai.location_id ");
        sqlQuery.append(" group by loc.name ");
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
       
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
           row.addColumnValue(new DataSetColumn("Sites", "Sites", String.class), o[0]);
           row.addColumnValue(new DataSetColumn("Resultat", "Resultat", String.class), o[1]);
            dataSet.addRow(row);
        }
        
		return dataSet;
	}

@SuppressWarnings("unchecked")
@Override
public DataSet nextVisitSevenDays() {
	sessionFactory = dao.getFactorySessionValue();
	  EvaluationContext context = new EvaluationContext();
	    SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
    StringBuilder sqlQuery = new StringBuilder("SELECT " +
    		"pa.national_id as identifier, pa.patient_id,pa.given_name as given_name,"+
  		"pa.family_name as family_name, pa.gender as gender,pa.birthdate as birthdate,"+
    		" pa.telephone as telephone,f.name as 'fiches',pv.next_visit_date as 'prochaine_visits' ");
    sqlQuery.append(" from isanteplus.patient pa, isanteplus.patient_visit pv, openmrs.form f ");
    sqlQuery.append(" where pa.patient_id=pv.patient_id ");
    sqlQuery.append(" AND pv.form_id=f.form_id ");
    sqlQuery.append(" and pv.next_visit_date between date(now()) ");
    sqlQuery.append(" and date_add(date(now()),interval 7 day) ");
    SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
   
    List<Object[]> list = query.list();
    SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
    for (Object[] o : list) {
        DataSetRow row = new DataSetRow();
       row.addColumnValue(new DataSetColumn("identifier", "identifier", String.class), o[0]);
       row.addColumnValue(new DataSetColumn("given_name", "given_name", String.class), o[2]);
       row.addColumnValue(new DataSetColumn("family_name", "family_name", String.class), o[3]);
       row.addColumnValue(new DataSetColumn("gender", "gender", String.class), o[4]);
       row.addColumnValue(new DataSetColumn("birthdate", "birthdate", String.class), o[5]);
       row.addColumnValue(new DataSetColumn("telephone", "telephone", String.class), o[6]);
       row.addColumnValue(new DataSetColumn("fiches", "fiches", String.class), o[7]);
       row.addColumnValue(new DataSetColumn("prochaine_visits", "prochaine_visits", String.class), o[8]);
        dataSet.addRow(row);
    }
		return dataSet;
}

@SuppressWarnings("unchecked")
@Override
public DataSet nextVisitFourteenDays() {
	sessionFactory = dao.getFactorySessionValue();
	  EvaluationContext context = new EvaluationContext();
	    SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
      StringBuilder sqlQuery = new StringBuilder("SELECT " +
      		" pa.national_id as identifier, pa.patient_id,pa.given_name as given_name, "+
    		" pa.family_name as family_name, pa.gender as gender,pa.birthdate as birthdate, "+
      		" pa.telephone as telephone,f.name as 'fiches',pv.next_visit_date as 'prochaine_visits' ");
      sqlQuery.append(" from isanteplus.patient pa, isanteplus.patient_visit pv, openmrs.form f ");
      sqlQuery.append(" where pa.patient_id=pv.patient_id ");
      sqlQuery.append(" AND pv.form_id=f.form_id ");
      sqlQuery.append(" and pv.next_visit_date between date(now()) ");
      sqlQuery.append(" and date_add(date(now()),interval 14 day) ");
      SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
     
      List<Object[]> list = query.list();
      SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
      for (Object[] o : list) {
          DataSetRow row = new DataSetRow();
         row.addColumnValue(new DataSetColumn("identifier", "identifier", String.class), o[0]);
         row.addColumnValue(new DataSetColumn("given_name", "given_name", String.class), o[2]);
         row.addColumnValue(new DataSetColumn("family_name", "family_name", String.class), o[3]);
         row.addColumnValue(new DataSetColumn("gender", "gender", String.class), o[4]);
         row.addColumnValue(new DataSetColumn("birthdate", "birthdate", String.class), o[5]);
         row.addColumnValue(new DataSetColumn("telephone", "telephone", String.class), o[6]);
         row.addColumnValue(new DataSetColumn("fiches", "fiches", String.class), o[7]);
         row.addColumnValue(new DataSetColumn("prochaine_visits", "prochaine_visits", String.class), o[8]);
          dataSet.addRow(row);
      }
  	return dataSet;
	   
}
	
}