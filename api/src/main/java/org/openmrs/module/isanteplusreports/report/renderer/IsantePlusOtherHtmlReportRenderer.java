package org.openmrs.module.isanteplusreports.report.renderer;

import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.common.Localized;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.renderer.RenderingException;
import org.openmrs.module.reporting.report.renderer.ReportDesignRenderer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

/**
 * A Default Renderer Implementation that aims to support all ReportDefinitions
 */
@Handler
@Localized("reporting.SimpleHtmlReportRenderer")
public class IsantePlusOtherHtmlReportRenderer extends ReportDesignRenderer {
	
	/**
	 * @see ReportRenderer#getRenderedContentType(org.openmrs.module.reporting.report.ReportRequest)
	 */
	public String getRenderedContentType(ReportRequest request) {
		return "text/html";
	}
	
	/**
	 * @see ReportRenderer#getFilename(org.openmrs.module.reporting.report.ReportRequest)
	 */
	@Override
	public String getFilename(ReportRequest request) {
		return getFilenameBase(request) + ".html";
	}
	
	/**
	 * @see ReportRenderer#render(ReportData, String, OutputStream)
	 */
	public void render(ReportData results, String argument, OutputStream out) throws IOException, RenderingException {
		
		Writer w = new OutputStreamWriter(out, "UTF-8");
		
		w.write("<html>");
		w.write("<head>");
		w.write("<body>");
		for (String key : results.getDataSets().keySet()) {
			DataSet dataset = results.getDataSets().get(key);
			List<DataSetColumn> columns = dataset.getMetaData().getColumns();
			List<Parameter> parameter = dataset.getDefinition().getParameters();
			
			w.write("<h4>" + key + "</h4>");
			if (dataset != null) {
				w.write("<table id=\"simple-html-parameter-" + key + "\" class=\"display simple-html-parameter" + key
				        + "\" style=\"min-width:100px\"><tr>");
				for (Parameter param : parameter) {
					if (param != null) {
						w.write("<tr>");
						w.write("<td><b>" + param.getName() + " :</b></td>");
						w.write("<td>" + dataset.getContext().getParameterValue(param.getName()) + "</td>");
						w.write("</tr>");
					}
				}
				w.write("</table><br/>");
			}
			w.write("<table id=\"simple-html-dataset-" + key + "\" class=\"display simple-html-dataset" + key
			        + "\" border=\"1" + key + "\" style=\"min-width:100px\">");
			for (DataSetRow row : dataset) {
				
				for (DataSetColumn column : columns) {
					w.write("<tr>");
					Object colValue = row.getColumnValue(column.getName());
					if (Integer.valueOf(colValue.toString()) > 0) {
						w.write("<th>" + column.getName() + "</th>");
						w.write("<td>");
						if (colValue instanceof Cohort) {
							w.write(Integer.toString(((Cohort) colValue).size()));
						} else {
							w.write(colValue.toString());
						}
						w.write("</td>");
					}
					
					w.write("</tr>");
				}
				
			}
			w.write("</table>");
		}
		w.write("</body>");
		w.write("</head>");
		w.write("</html>");
		w.flush();
	}
	
}
