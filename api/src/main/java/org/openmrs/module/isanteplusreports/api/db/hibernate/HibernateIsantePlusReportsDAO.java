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
package org.openmrs.module.isanteplusreports.api.db.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.api.db.IsantePlusReportsDAO;

/**
 * It is a default implementation of  {@link IsantePlusReportsDAO}.
 */
public class HibernateIsantePlusReportsDAO implements IsantePlusReportsDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	private Connection conn;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }
    @SuppressWarnings("unchecked")
	public List<Location> ReturnNumberPatientsDAO(){
   		String responseSelectSQL= "select * FROM Location";
	    List<Location> resultat = sessionFactory.getCurrentSession().createSQLQuery(responseSelectSQL).list();
	    return resultat;     
    }
    
    public Connection getConnection(){
    	  String url = Context.getRuntimeProperties().getProperty("connection.url");
	      String username = Context.getRuntimeProperties().getProperty("connection.username");
	      String password = Context.getRuntimeProperties().getProperty("connection.password");
		// on recupere les preferences de connexion
		try {
			//on charge le driver JDBC
			Class.forName("com.mysql.jdbc.Driver");
                        //on creer la connexion
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	public SessionFactory getFactorySessionValue() {
		// TODO Auto-generated method stub
		return sessionFactory;
	}
    
    
}