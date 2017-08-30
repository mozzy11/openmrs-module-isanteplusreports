package org.openmrs.module.isanteplusreports.healthqual.builder;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import j2html.tags.ContainerTag;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Builder;
import org.openmrs.module.isanteplusreports.exception.HealthQualException;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.report.ReportData;

import static j2html.TagCreator.div;
import static j2html.TagCreator.table;
import static j2html.TagCreator.td;
import static j2html.TagCreator.th;
import static j2html.TagCreator.tr;

public class HealthQualHtmlTableBuilder implements Builder<String> {
	
	private static final int ROWS = 7;
	
	private static final ContainerTag MALE_LABEL = th("M").withClass("label");
	
	private static final ContainerTag FEMALE_LABEL = th("F").withClass("label");
	
	private static final ContainerTag TOTAL_LABEL = th("Tot").withClass("label");
	
	private static final String MALE_NUMERATOR_COLUMN_NAME = "maleNumerator";
	
	private static final String FEMALE_NUMERATOR_COLUMN_NAME = "femalenumerator";
	
	private static final String MALE_DENOMINATOR_COLUMN_NAME = "maleDenominator";
	
	private static final String FEMALE_DENOMINATOR_COLUMN_NAME = "femaleDenominator";
	
	private static final String PERCENTAGE_STRING_FORMAT = "###.0";
	
	private int numberOfIndicatorsInOneTable = 3; // if there are too many indicators the table will be splitted
	
	private ContainerTag[] rows;
	
	private List<DataSet> dataSets;
	
	private String clinicDepartment;
	
	private String clinic;
	
	@Override
	public String build() {
		ContainerTag tables = div();
		Iterator<DataSet> iterator = getDataSets().iterator();
		while (iterator.hasNext()) {
			tables.with(buildOneTable(iterator));
			setRows(null); // clear already built table
		}
		
		return tables.render();
	}
	
	private ContainerTag buildOneTable(Iterator<DataSet> iterator) {
		buildClinicInfoTable();
		// buildLegend();
		buildPatientsInfo();
		
		for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
			buildIndicator(iterator.next());
		}
		
		return table().with(getRows());
	}
	
	private void buildClinicInfoTable() {
		fillEmptyRow(getRows()[0], 2);
		fillEmptyRow(getRows()[1], 2);
		
		ContainerTag labels = getRows()[2];
		ContainerTag data = getRows()[3];
		
		labels.with(th(translateLabel("department")).withClass("label"), th(translateLabel("clinic")).withClass("label"));
		
		data.with(td(clinicDepartment).attr("rowspan", "3"), td(clinic).attr("rowspan", "3"));
	}
	
	//	private void buildLegend() {
	//		fillEmptyRow(getRows()[0], 1);
	//		fillEmptyRow(getRows()[1], 1);
	//
	//		getRows()[2].with(th("<Type>").withClass("label"));
	//		getRows()[3].with(td("<Adult>").withClass("label"));
	//		getRows()[4].with(td("<Ped>").withClass("label"));
	//		getRows()[5].with(td("<Total>").withClass("label").withClass("total"));
	//	}
	
	private void buildPatientsInfo() {
		fillEmptyRow(getRows()[0], 3);
		
		getRows()[1].with(th(translateLabel("activePatients")).attr("colspan", "3").withClass("label"));
		
		buildIndicatorSummary(createSummaryArray(11, 22));
	}
	
	private void buildIndicator(DataSet data) {
		getRows()[0].with(td(data.getDefinition().getName()).attr("colspan", "9").withClass("indicatorLabel"));
		getRows()[1].with(td(translateLabel("activePatients")).attr("colspan", "3").withClass("label"),
		    td(translateLabel("denominator")).attr("colspan", "3").withClass("label"), td(translateLabel("percentage"))
		            .attr("colspan", "3").withClass("label"));
		
		Integer[] numerator = createSummaryArray(getDataSetIntegerValue(data, MALE_NUMERATOR_COLUMN_NAME),
		    getDataSetIntegerValue(data, FEMALE_NUMERATOR_COLUMN_NAME));
		buildIndicatorSummary(numerator);
		
		Integer[] denominator = createSummaryArray(getDataSetIntegerValue(data, MALE_DENOMINATOR_COLUMN_NAME),
		    getDataSetIntegerValue(data, FEMALE_DENOMINATOR_COLUMN_NAME));
		buildIndicatorSummary(denominator);
		
		buildIndicatorSummary(createPercentageArray(numerator, denominator));
	}

	private <T> void buildIndicatorSummary(T[] dataArray) {
		getRows()[2].with(MALE_LABEL, FEMALE_LABEL, TOTAL_LABEL);
		
		final int ROW = 3;
		getRows()[ROW].with(td(dataArray[0].toString()).attr("rowspan", "3"));
		getRows()[ROW].with(td(dataArray[1].toString()).attr("rowspan", "3"));
		getRows()[ROW].with(td(dataArray[2].toString()).attr("rowspan", "3").withClass("total"));
	}
	
	private Integer[] createSummaryArray(Integer males, Integer females) {
		return new Integer[] { males, females, males + females };
	}
	
	private String[] createPercentageArray(Integer[] dividend, Integer[] factor) {
		DecimalFormat df = new DecimalFormat(PERCENTAGE_STRING_FORMAT);
		df.setRoundingMode(RoundingMode.HALF_UP);
		
		final int SIZE = 3;
		String result[] = new String[SIZE];
		for (int i = 0; i < SIZE; ++i) {
			result[i] = df.format(100.0f * dividend[i] / factor[i]);
		}
		return result;
	}
	
	// filling by empty <td>
	private void fillEmptyRow(ContainerTag row, Integer length) {
		row.with(td().attr("colspan", length.toString()));
	}
	
	public String getClinic() {
		return clinic;
	}
	
	public void setClinic(String clinic) {
		this.clinic = clinic;
	}
	
	public String getClinicDepartment() {
		return clinicDepartment;
	}
	
	public void setClinicDepartment(String clinicDepartment) {
		this.clinicDepartment = clinicDepartment;
	}
	
	public ContainerTag[] getRows() {
		if (rows == null) {
			rows = new ContainerTag[ROWS];
			for (int i = 0; i < ROWS; ++i) {
				rows[i] = tr();
			}
		}
		return rows;
	}
	
	public void setRows(ContainerTag[] rows) {
		this.rows = rows;
	}
	
	public List<DataSet> getDataSets() {
		if (dataSets == null) {
			dataSets = new LinkedList<DataSet>();
		}
		return dataSets;
	}
	
	public void setDataSets(List<DataSet> dataSets) {
		this.dataSets = dataSets;
	}
	
	public void addDataSet(DataSet i) {
		getDataSets().add(i);
	}
	
	public void addReportData(ReportData reportData) {
		getDataSets().addAll(reportData.getDataSets().values());
	}
	
	public int getNumberOfIndicatorsInOneTable() {
		return numberOfIndicatorsInOneTable;
	}
	
	public void setNumberOfIndicatorsInOneTable(int numberOfIndicatorsInOneTable) {
		this.numberOfIndicatorsInOneTable = numberOfIndicatorsInOneTable;
	}
	
	private Integer getDataSetIntegerValue(DataSet dataSet, String columnName) {
		Object value = dataSet.iterator().next().getColumnValue(columnName);
		if (value == null || StringUtils.isBlank(value.toString())) {
			dataSet.getDefinition().getName();
			throw new HealthQualException("`" + dataSet.getDefinition().getName() + "` report - column `" + columnName
			        + "` doesn't exist in dataSet. Probably there is a bug in report SQL");
		}
		return Integer.valueOf(value.toString());
	}

	private String translateLabel(String labelName) {
		return MessageUtil.translate("isanteplusreports.healthqual.result." + labelName + ".label");
	}
}
