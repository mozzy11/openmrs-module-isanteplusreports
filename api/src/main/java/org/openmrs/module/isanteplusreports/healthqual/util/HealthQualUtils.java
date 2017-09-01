package org.openmrs.module.isanteplusreports.healthqual.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.exception.HealthQualException;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class HealthQualUtils {

    private static final Log LOGGER = LogFactory.getLog(HealthQualUtils.class);

    public static ReportData getReportData(String reportUuid, Date startDate, Date endDate, Map<String, Object> additionalOptions) {
        ReportDefinitionService reportDefinitionService = Context.getService(ReportDefinitionService.class);
        ReportDefinition reportDefinition = reportDefinitionService.getDefinitionByUuid(reportUuid);
        Map<String, Object> parameterValues = new HashMap<String, Object>();

        for (Parameter parameter : reportDefinition.getParameters()) {
            if (parameter.getName().equals("startDate")) {
                parameterValues.put("startDate", startDate);
            } else if (parameter.getName().equals("endDate")) {
                parameterValues.put("endDate", endDate);
            } else if (additionalOptions != null && additionalOptions.containsKey(parameter.getName())) {
                parameterValues.put(parameter.getName(), additionalOptions.get(parameter.getName()));
            } else {
                LOGGER.error("Report cannot be evaluated - missing '" + parameter.getName() + "' parameter'");
                throw new HealthQualException("Report cannot be evaluated - missing '" + parameter.getName() + "' parameter'");
            }
        }

        EvaluationContext evaluationContext = new EvaluationContext();
        evaluationContext.setParameterValues(parameterValues);
        ReportData reportData = null;
        try {
            reportData = reportDefinitionService.evaluate(reportDefinition, evaluationContext);
        } catch (EvaluationException e) {
            LOGGER.error("Report evaluation exception was thrown");
            throw new HealthQualException("Report cannot be evaluated", e);
        } return reportData;
    }

    public static String readResource(String file) {
        InputStream in = HealthQualUtils.class.getClassLoader().getResourceAsStream(file);
        try {
            try {
                return IOUtils.toString(in);
            } finally {
                in.close();
            }
        } catch (Exception ex) {
            LOGGER.error("Cannot read '" + file + "' resource");
            throw new HealthQualException("Cannot read '" + file + "' resource", ex);
        }
    }
}