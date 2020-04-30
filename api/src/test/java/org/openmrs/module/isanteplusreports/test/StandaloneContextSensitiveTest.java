package org.openmrs.module.isanteplusreports.test;
import java.util.Properties;

import org.hibernate.cfg.Environment;
import org.openmrs.test.BaseModuleContextSensitiveTest;


public class StandaloneContextSensitiveTest extends BaseModuleContextSensitiveTest {

    @Override
    public Properties getRuntimeProperties() {
        Properties p = super.getRuntimeProperties();
        String url = "jdbc:h2:mem:isanteplus;MODE=MYSQL;DB_CLOSE_DELAY=30;LOCK_TIMEOUT=10000;"
        		+ "INIT=CREATE SCHEMA IF NOT EXISTS isanteplus\\;SET SCHEMA isanteplus\\;"
        		+ "RUNSCRIPT FROM 'classpath:org/openmrs/module/isanteplusreports/sql/init.sql'\\;"
				+ "RUNSCRIPT FROM 'classpath:org/openmrs/module/isanteplusreports/sql/data.sql'\\;";

        p.setProperty(Environment.URL, url);
        
        return p;
    }

}