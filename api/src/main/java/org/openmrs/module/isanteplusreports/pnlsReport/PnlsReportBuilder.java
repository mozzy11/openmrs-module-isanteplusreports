package org.openmrs.module.isanteplusreports.pnlsReport;

import static j2html.TagCreator.a;
import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.h2;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.h5;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.style;
import static j2html.TagCreator.table;
import static j2html.TagCreator.td;
import static j2html.TagCreator.th;
import static j2html.TagCreator.tr;
import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.getStringFromResource;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualUtils.replaceNonBreakingSpaces;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.isanteplusreports.exception.HealthQualException;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.ui.framework.UiUtils;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import com.itextpdf.text.DocumentException;

import j2html.tags.ContainerTag;

public class PnlsReportBuilder  extends UiUtils{

	private final Log LOGGER = LogFactory.getLog(getClass());

	private static final int ROWS = 5;
		
	private static final String COLUMN_NAME_0_1_M = "0-1M";
	
	private static final String COLUMN_NAME_1_4_M = "1-4M";
	
	private static final String COLUMN_NAME_5_9_M = "5-9M";
	
	private static final String COLUMN_NAME_10_14_M = "10-14M";
	
	private static final String COLUMN_NAME_15_19_M = "15-19M";
	
	private static final String COLUMN_NAME_20_24_M = "20-24M";
	
	private static final String COLUMN_NAME_25_29_M = "25-29M";
	
	private static final String COLUMN_NAME_30_34_M = "30-34M";
	
	private static final String COLUMN_NAME_35_39_M = "35-39M";
	
	private static final String COLUMN_NAME_40_44_M = "40-44M";
	
	private static final String COLUMN_NAME_45_49_M = "45-49M";
	
	private static final String COLUMN_NAME_50_M = "50M";
	
	private static final String COLUMN_NAME_UK_M = "UnKnownM";
	
	private static final String COLUMN_NAME_Total_M = "TotalM";
	
    private static final String COLUMN_NAME_0_1_F = "0-1F";
	
	private static final String COLUMN_NAME_1_4_F = "1-4F";
	
	private static final String COLUMN_NAME_5_9_F = "5-9F";
	
	private static final String COLUMN_NAME_10_14_F = "10-14F";
	
	private static final String COLUMN_NAME_15_19_F = "15-19F";
	
	private static final String COLUMN_NAME_20_24_F = "20-24F";
	
	private static final String COLUMN_NAME_25_29_F = "25-29F";
	
	private static final String COLUMN_NAME_30_34_F = "30-34F";
	
	private static final String COLUMN_NAME_35_39_F = "35-39F";
	
	private static final String COLUMN_NAME_40_44_F = "40-44F";
	
	private static final String COLUMN_NAME_45_49_F = "45-49F";
	
	private static final String COLUMN_NAME_50_F = "50F";
	
	private static final String COLUMN_NAME_UK_F = "UnKnownF";
	
	private static final String COLUMN_NAME_Total_F = "TotalF";
	
private static final String COLUMN_NAME_0_1_T = "0-1T";
	
	private static final String COLUMN_NAME_1_4_T = "1-4T";
	
	private static final String COLUMN_NAME_5_9_T = "5-9T";
	
	private static final String COLUMN_NAME_10_14_T = "10-14T";
	
	private static final String COLUMN_NAME_15_19_T = "15-19T";
	
	private static final String COLUMN_NAME_20_24_T = "20-24T";
	
	private static final String COLUMN_NAME_25_29_T = "25-29T";
	
	private static final String COLUMN_NAME_30_34_T = "30-34T";
	
	private static final String COLUMN_NAME_35_39_T = "35-39T";
	
	private static final String COLUMN_NAME_40_44_T = "40-44T";
	
	private static final String COLUMN_NAME_45_49_T = "45-49T";
	
	private static final String COLUMN_NAME_50_T = "50T";
	
	private static final String COLUMN_NAME_UK_T = "UnKnownT";
	
	private static final String COLUMN_NAME_Total_T = "TotalT";
	
	private static final String PERIOD_DATE_FORMAT_PATTERN = "yyyy/MM/dd";

	private static final String CREATION_DATE_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";

	private static final String STRING_IF_EMPTY = "-";
	
	private int numberOfIndicatorsInOneTable = 1; // if there are too many indicators the table will be splitted
	
	private ContainerTag[] rows;
	
	private List<DataSet> dataSets;
	
	private String clinicDepartment;
	
	private String clinic;

	private Date startDate;

	private Date endDate;

	private Long femalePatients;

	private Long malePatients;
		
	public  String[] getColumnNamesArray(){
		
		String[] colunms = {
				COLUMN_NAME_0_1_M,
				COLUMN_NAME_1_4_M ,
				COLUMN_NAME_5_9_M ,
				COLUMN_NAME_10_14_M ,
				COLUMN_NAME_15_19_M,
				COLUMN_NAME_20_24_M,
				COLUMN_NAME_25_29_M ,
				COLUMN_NAME_30_34_M,
				COLUMN_NAME_35_39_M,
				COLUMN_NAME_40_44_M,
				COLUMN_NAME_45_49_M,
				COLUMN_NAME_50_M,
				COLUMN_NAME_UK_M,
				COLUMN_NAME_Total_M,
				COLUMN_NAME_0_1_F,
				COLUMN_NAME_1_4_F ,
				COLUMN_NAME_5_9_F ,
				COLUMN_NAME_10_14_F ,
				COLUMN_NAME_15_19_F,
				COLUMN_NAME_20_24_F,
				COLUMN_NAME_25_29_F ,
				COLUMN_NAME_30_34_F,
				COLUMN_NAME_35_39_F,
				COLUMN_NAME_40_44_F,
				COLUMN_NAME_45_49_F,
				COLUMN_NAME_50_F,
				COLUMN_NAME_UK_F,
				COLUMN_NAME_Total_F,	
				COLUMN_NAME_0_1_T,
				COLUMN_NAME_1_4_T ,
				COLUMN_NAME_5_9_T ,
				COLUMN_NAME_10_14_T ,
				COLUMN_NAME_15_19_T,
				COLUMN_NAME_20_24_T,
				COLUMN_NAME_25_29_T ,
				COLUMN_NAME_30_34_T,
				COLUMN_NAME_35_39_T,
				COLUMN_NAME_40_44_T,
				COLUMN_NAME_45_49_T,
				COLUMN_NAME_50_T,
				COLUMN_NAME_UK_T,
				COLUMN_NAME_Total_T	              
		}; 
		return colunms ;
	}
		
	public  List<String> getColumnNamesList(){	
		return Arrays.asList(getColumnNamesArray());							
	  }
	
	public String buildHtmlTables() {
		String tablesHtml = buildTables().render();
		LOGGER.debug("built tables html: " + tablesHtml);
		return tablesHtml;
	}
	
	public String buildPdf() {
		String htmlForPdf = html(head(getStyleForPdf()), body(createPdfHeader(), buildTables())).render();
		LOGGER.debug("built htmlForPdf: " + htmlForPdf);
		try {
			return convertHtmlToPdfInBase64(htmlForPdf);
		} catch (Exception ex) {
			throw new HealthQualException("PDF cannot be created", ex);
		}
	}

	private ContainerTag createPdfHeader() {
		return div().withClass("center").with(
			h2(translateLabel("pdf.header")),
			h3(createPeriodString()),
			h5(createDateOfCreationString())
		);
	}

	private String createPeriodString() {
		SimpleDateFormat df = new SimpleDateFormat(PERIOD_DATE_FORMAT_PATTERN);
		return df.format(startDate) + " - " + df.format(endDate);
	}

	private String createDateOfCreationString() {
		SimpleDateFormat df = new SimpleDateFormat(CREATION_DATE_FORMAT_PATTERN);
		return translateLabel("pdf.creationDate") + " " + df.format(new Date());
	}

	private ContainerTag getStyleForPdf() {
		return style().withType("text/css").withText(getStringFromResource("healthQualPdfStyle.css"));
	}
	
	private String convertHtmlToPdfInBase64(String html) throws IOException, ParserConfigurationException, SAXException,
	        DocumentException {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		//Document doc = builder.parse(new ByteArrayInputStream(html.replaceAll("&nbsp;", "").getBytes()));
		Document doc = builder.parse(new ByteArrayInputStream(html.replaceAll("&nbsp;", "").getBytes("UTF-8")));
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(doc, null);

		renderer.layout();
		renderer.createPDF(out);
		out.flush();
		out.close();
		
		return DatatypeConverter.printBase64Binary(out.toByteArray());
	}
	
	private ContainerTag buildTables() {
		ContainerTag tables = div();
		Iterator<DataSet> iterator = getDataSets().iterator();
		while (iterator.hasNext()) {
			tables.with(buildOneTable(iterator));
			clearRows();
		}
		
		return tables;
	}

	private ContainerTag buildOneTable(Iterator<DataSet> iterator) {
		buildGenderSummaryTable();
		for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
			buildIndicator(iterator.next());
		}		
		return table().with(getRows());
	}
	
	private void buildGenderSummaryTable() {
		fillEmptyRow(getRows()[0], 1);
		fillEmptyRow(getRows()[1], 1);
		ContainerTag maleLabel = getRows()[2];
		ContainerTag femaleLable = getRows()[3];
		ContainerTag Subtotal = getRows()[4];
		maleLabel.with(th(translateLabel("Males")).withClass("label"));
		femaleLable.with(th(translateLabel("FeMales")).withClass("label"));
		Subtotal.with(th(translateLabel("Subtotal")).withClass("label"));		
	}
				
	private void buildIndicator(DataSet data) {
		getRows()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "14").withClass("indicatorLabel"));
		getRows()[1].with(td(translateLabel("1-0")).attr("colspan", "1").withClass("label"),
		                  td(translateLabel("1-4")).attr("colspan", "1").withClass("label"),
		                  td(translateLabel("5-9")) .attr("colspan", "1").withClass("label"),
		                  td(translateLabel("10-14")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("15-19")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("20-24")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("25-29")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("30-34")) .attr("colspan", "1").withClass("label"),                  
                          td(translateLabel("35-39")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("40-44")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("45-49")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("50")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("UnKnown")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("Total")) .attr("colspan", "1").withClass("label")) ;
				
		int[] dataSet = createSummaryArray(getColumnNamesList() ,data);
		String reportName = data.getDefinition().getName();
		buildIndicatorSummary(dataSet ,getColumnNamesArray(),reportName );	
	}
	
	private void buildIndicatorSummary(int[] dataArray ,String[] columnsArray, String reportName ) {
		
		final int ROW2 = 2;
		final int ROW3 = 3;
		final int ROW4 = 4;

		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		String row1 =  ConstructUrl(reportUrl ,reportName, columnsArray[0]);
		String row2 =  ConstructUrl(reportUrl ,reportName, columnsArray[1]);
		String row3 =  ConstructUrl(reportUrl ,reportName, columnsArray[2]);
		String row4 =  ConstructUrl(reportUrl ,reportName, columnsArray[3]);
		String row5 =  ConstructUrl(reportUrl ,reportName, columnsArray[4]);
		String row6 =  ConstructUrl(reportUrl ,reportName, columnsArray[5]);
		String row7 =  ConstructUrl(reportUrl ,reportName, columnsArray[6]);
		String row8 =  ConstructUrl(reportUrl ,reportName, columnsArray[7]);
		String row9 =  ConstructUrl(reportUrl ,reportName, columnsArray[8]);
		String row10 = ConstructUrl(reportUrl ,reportName, columnsArray[9]);
		String row11 = ConstructUrl(reportUrl ,reportName, columnsArray[10]);
		String row12 = ConstructUrl(reportUrl ,reportName, columnsArray[11]);
		String row13 = ConstructUrl(reportUrl ,reportName, columnsArray[12]);
		String row14 = ConstructUrl(reportUrl ,reportName, columnsArray[13]);
		
		String row15 =  ConstructUrl(reportUrl ,reportName, columnsArray[14]);
		String row16 =  ConstructUrl(reportUrl ,reportName, columnsArray[15]);
		String row17 =  ConstructUrl(reportUrl ,reportName, columnsArray[16]);
		String row18 =  ConstructUrl(reportUrl ,reportName, columnsArray[17]);
		String row19 =  ConstructUrl(reportUrl ,reportName, columnsArray[18]);
		String row20 =  ConstructUrl(reportUrl ,reportName, columnsArray[19]);
		String row21 =  ConstructUrl(reportUrl ,reportName, columnsArray[20]);
		String row22 =  ConstructUrl(reportUrl ,reportName, columnsArray[21]);
		String row23 =  ConstructUrl(reportUrl ,reportName, columnsArray[22]);
		String row24 =  ConstructUrl(reportUrl ,reportName, columnsArray[23]);
		String row25 =  ConstructUrl(reportUrl ,reportName, columnsArray[24]);
		String row26 =  ConstructUrl(reportUrl ,reportName, columnsArray[25]);
		String row27 =  ConstructUrl(reportUrl ,reportName, columnsArray[26]);
		String row28 =  ConstructUrl(reportUrl ,reportName, columnsArray[27]);
		

		String row29 =  ConstructUrl(reportUrl ,reportName, columnsArray[28]);
		String row30 =  ConstructUrl(reportUrl ,reportName, columnsArray[29]);
		String row31 =  ConstructUrl(reportUrl ,reportName, columnsArray[30]);
		String row32 =  ConstructUrl(reportUrl ,reportName, columnsArray[31]);
		String row33 =  ConstructUrl(reportUrl ,reportName, columnsArray[32]);
		String row34 =  ConstructUrl(reportUrl ,reportName, columnsArray[33]);
		String row35 =  ConstructUrl(reportUrl ,reportName, columnsArray[34]);
		String row36 =  ConstructUrl(reportUrl ,reportName, columnsArray[35]);
		String row37 =  ConstructUrl(reportUrl ,reportName, columnsArray[36]);
		String row38 =  ConstructUrl(reportUrl ,reportName, columnsArray[37]);
		String row39 =  ConstructUrl(reportUrl ,reportName, columnsArray[38]);
		String row40 =  ConstructUrl(reportUrl ,reportName, columnsArray[39]);
		String row41 =  ConstructUrl(reportUrl ,reportName, columnsArray[40]);
		String row42 =  ConstructUrl(reportUrl ,reportName, columnsArray[41]);
		
		//populates second row		
		populateTable(ROW2,dataArray[0],row1);
		populateTable(ROW2,dataArray[1],row2);
		populateTable(ROW2,dataArray[2],row3);
		populateTable(ROW2,dataArray[3],row4);
		populateTable(ROW2,dataArray[4],row5);
		populateTable(ROW2,dataArray[5],row6);
		populateTable(ROW2,dataArray[6],row7);
		populateTable(ROW2,dataArray[7],row8);
		populateTable(ROW2,dataArray[8],row9);
		populateTable(ROW2,dataArray[9],row10);
		populateTable(ROW2,dataArray[10],row11);
		populateTable(ROW2,dataArray[11],row12);
		populateTable(ROW2,dataArray[12],row13);
		populateTable(ROW2,dataArray[13],row14);
		
		
		//populates third row
		populateTable(ROW3,dataArray[14],row15);
		populateTable(ROW3,dataArray[15],row16);
		populateTable(ROW3,dataArray[16],row17);
		populateTable(ROW3,dataArray[17],row18);
		populateTable(ROW3,dataArray[18],row19);
		populateTable(ROW3,dataArray[19],row20);
		populateTable(ROW3,dataArray[20],row21);
		populateTable(ROW3,dataArray[21],row22);
		populateTable(ROW3,dataArray[22],row23);
		populateTable(ROW3,dataArray[23],row24);
		populateTable(ROW3,dataArray[24],row25);
		populateTable(ROW3,dataArray[25],row26);
		populateTable(ROW3,dataArray[26],row27);
		populateTable(ROW3,dataArray[27],row28);
		
		//populate fourth row
		populateTable(ROW4,dataArray[28],row29);
		populateTable(ROW4,dataArray[29],row30);
		populateTable(ROW4,dataArray[30],row31);
		populateTable(ROW4,dataArray[31],row32);
		populateTable(ROW4,dataArray[32],row33);
		populateTable(ROW4,dataArray[33],row34);
		populateTable(ROW4,dataArray[34],row35);
		populateTable(ROW4,dataArray[35],row36);
		populateTable(ROW4,dataArray[36],row37);
		populateTable(ROW4,dataArray[37],row38);
		populateTable(ROW4,dataArray[38],row39);
		populateTable(ROW4,dataArray[39],row40);
		populateTable(ROW4,dataArray[40],row41);
		populateTable(ROW4,dataArray[41],row42);		
	}
	
	private String ConstructUrl( String reportBaseUrl , String reportName,String columnName) {
		 return String.format("%s?savedDataSetKey=%s&savedColumnKey=%s&columnKeyType=numerator", reportBaseUrl, reportName, columnName);
	}
		
	private void populateTable(Integer ROW ,int data , String rowLink) {
		getRows()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}
	
	private int[] createSummaryArray(List<String> columNames, DataSet dataSet) {
		List<Integer> columnValues = new ArrayList<Integer>();
		for (String column : columNames ) {
			columnValues.add(getDataSetIntegerValue( dataSet,column));	 	
		}
		return columnValues.stream().mapToInt(Integer::intValue).toArray();
	}
		
	// filling by empty <td>
	private void fillEmptyRow(ContainerTag row, Integer length) {
		row.with(td().attr("colspan", length.toString()));
	}
	
	public String getClinic() {
		return StringUtils.isNotBlank(clinic) ? clinic : STRING_IF_EMPTY;
	}
	
	public void setClinic(String clinic) {
		this.clinic = clinic;
	}
	
	public String getClinicDepartment() {
		return StringUtils.isNotBlank(clinicDepartment) ? clinicDepartment : STRING_IF_EMPTY;
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

	private void clearRows() {
		setRows(null);
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
	
	private static String translateLabel(String labelName) {
		return translate("isanteplusreports.pnls.result." + labelName + ".label");
	}

	private static String translate(String code) {
		return replaceNonBreakingSpaces(MessageUtil.translate(code));
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getFemalePatients() {
		return femalePatients;
	}

	public void setFemalePatients(Long femalePatients) {
		this.femalePatients = femalePatients;
	}

	public Long getMalePatients() {
		return malePatients;
	}

	public void setMalePatients(Long malePatients) {
		this.malePatients = malePatients;
	}

}