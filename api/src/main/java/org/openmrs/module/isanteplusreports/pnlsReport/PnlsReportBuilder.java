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
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.COLUMNS_ARRAY_14_BY_3 ; 
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.COULUMNS_ARRAY_1_BY_1;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.COLUMNS_ARRAY_14_BY_14 ;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.KEY_POPN_COLUMNS_ARRAY;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.CTX_COLUMNS_ARRAY;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.COLUMNS_ARRAY_AGE_BY_15;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.COLUMNS_ARRAY_ARV_PATIENTS_BY_TB_DIAGNOSIS_TEST;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.COLUMNS_ARRAY_14_BY_4;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.COLUMNS_ARRAY_KEY_POPN_SINGLE_ROW ;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.COLUMNS_ARRAY_4By7 ;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.COLUMNS_ARRAY_ACTIVE_PATIENTS_BY_REGIME ;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.COLUMNS_ARRAY_CERVICAL_CANCER_STATUS;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.COLUMNS_ARRAY_CERVICAL_CANCER_TREATMENT;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.COLUMNS_ARRAY_FAMILY_PLANNING ;

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

	private static final int ROWS_5 = 5;
	
	private static final int ROWS_15 = 15;
	
    private static final int ROWS_2 = 2;
    
    private static final int ROWS_3 = 3;
	
	private static final int ROWS_4 = 4;
	
	private static final int ROWS_8 = 8;
	
	private static final int ROWS_7 = 7;
	
	private static final int ROWS_9 = 9;
	
	private static final int ROWS_11 = 11;
	
	private static final int ROWS_6 = 6;
	
	private static final int ROWS_13 = 13;
		
	
	private static final String PERIOD_DATE_FORMAT_PATTERN = "yyyy/MM/dd";

	private static final String CREATION_DATE_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";

	private static final String STRING_IF_EMPTY = "-";
	
	private int numberOfIndicatorsInOneTable = 1; // if there are too many indicators the table will be splitted
	
	private ContainerTag[] rows5;
	
	private ContainerTag[] rows14;
	
	private ContainerTag[] rows2;
	
	private ContainerTag[] rows3;
	
	private ContainerTag[] rows4;
	
	private ContainerTag[] rows8;
	
	private ContainerTag[] rows7;
	
	private ContainerTag[] rows9;
	
	private ContainerTag[] rows11;
	
	private ContainerTag[] rows6;
	
	private ContainerTag[] rows13;
	
	private List<DataSet> dataSets14By3;
	
	private List<DataSet> dataSets1By1;
	
	private List<DataSet> dataSets14By14;
	
	private List<DataSet> dataSets6By3;
	
	private List<DataSet> dataSets6By2;
	
	private List<DataSet> dataSets14By6;
	
	private List<DataSet> dataSets4By5;
	
	private List<DataSet> dataSets4By7;
	
	private List<DataSet> dataSets3By1;
	
	private List<DataSet> dataSets14By4;
	
	private List<DataSet> dataSets14By9;
	
	private List<DataSet> dataSets10By4;
	
	private List<DataSet> dataSets10By4II;
	
	private List<DataSet> dataSets10By11;
	
	private String clinicDepartment;
	
	private String clinic;

	private Date startDate;

	private Date endDate;

	private Long femalePatients;

	private Long malePatients;
		
	public  String[] getColumnNamesArray14By3(){ 
		return COLUMNS_ARRAY_14_BY_3 ;
	}
		
	public  List<String> getColumnNamesList14By3(){	
		return Arrays.asList(getColumnNamesArray14By3());							
	  }
	
	public  String[] getBreastFeedingColumnNamesArray(){		
		return COULUMNS_ARRAY_1_BY_1 ;
	}
	
	public  List<String> getBreastFeedingColumnNamesList(){	
		return Arrays.asList(getBreastFeedingColumnNamesArray());							
	  }
	
	public  String[] getColumnNamesArray14By14(){ 
		return COLUMNS_ARRAY_14_BY_14;
	}
	
	public  List<String> getColumnNamesList14By14(){	
		return Arrays.asList(getColumnNamesArray14By14());							
	  }
	
	public  String[] getKeyPopnColumnNamesArray(){ 
		return KEY_POPN_COLUMNS_ARRAY;
	}
	
	public  List<String> getKeyPopnColumnNamesList(){	
		return Arrays.asList(getKeyPopnColumnNamesArray());							
	  }
	
	public  String[] getCtxColumnNamesArray(){ 
		return CTX_COLUMNS_ARRAY;
	}
	
	public  List<String> getCtxColumnNamesList(){	
		return Arrays.asList(getCtxColumnNamesArray());							
	  }
	
	public  String[] getAgeBy15ColumnNamesArray(){ 
		return COLUMNS_ARRAY_AGE_BY_15;
	}
	
	public  List<String> getAgeBy15ColumnNamesList(){	
		return Arrays.asList(getAgeBy15ColumnNamesArray());							
	  }
	
	public  String[] getArvPatientsByTbDiagnosisTestColumnNamesArray(){ 
		return COLUMNS_ARRAY_ARV_PATIENTS_BY_TB_DIAGNOSIS_TEST;
	}
	
	public  List<String> getArvPatientsByTbDiagnosisTestColumnNamesList(){	
		return Arrays.asList( getArvPatientsByTbDiagnosisTestColumnNamesArray());							
	  }
	
	public  String[] getColumnNamesArray14By4(){ 
		return COLUMNS_ARRAY_14_BY_4;
	}
	
	public  List<String> getColumnNamesList14By4(){	
		return Arrays.asList(getColumnNamesArray14By4());							
	  }
	
	public  String[] getKeyPopnSingleRowColumnNamesArray(){ 
		return COLUMNS_ARRAY_KEY_POPN_SINGLE_ROW ;
	}
	
	public  List<String> getKeyPopnSingleRowColumnNamesList(){	
		return Arrays.asList(getKeyPopnSingleRowColumnNamesArray());							
	  }
	
	public  String[] getColumnNamesArray4By7(){ 
		return COLUMNS_ARRAY_4By7;
	}
	
	public  List<String> getColumnNamesList4By7(){	
		return Arrays.asList(getColumnNamesArray4By7());							
	  }
	
	public  String[] getActivePatientRegimenLinesColumnNamesArray(){ 
		return COLUMNS_ARRAY_ACTIVE_PATIENTS_BY_REGIME;
	}
	
	public  List<String>getActivePatientRegimenLinesColumnNamesList(){	
		return Arrays.asList(getActivePatientRegimenLinesColumnNamesArray());							
	  }
	
	public  String[] getwomenCancerStatusColumnNamesArray(){ 
		return COLUMNS_ARRAY_CERVICAL_CANCER_STATUS;
	}
	
	public  List<String>getwomenCancerStatusColumnNamesList(){	
		return Arrays.asList(getwomenCancerStatusColumnNamesArray());							
	  }
	
	public  String[] getwomenCancerTreatmentColumnNamesArray(){ 
		return COLUMNS_ARRAY_CERVICAL_CANCER_TREATMENT;
	}
	
	public  List<String>getwomenCancerTreatmentColumnNamesList(){	
		return Arrays.asList(getwomenCancerTreatmentColumnNamesArray());							
	  }
	
	public  String[] getFamilyPlanningColumnNamesArray(){ 
		return COLUMNS_ARRAY_FAMILY_PLANNING;
	}
	
	public  List<String>getFamilyPlanningColumnNamesList(){	
		return Arrays.asList(getFamilyPlanningColumnNamesArray());							
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
		Iterator<DataSet> iterator1 = getDataSets14By3().iterator();
		while (iterator1.hasNext()) {
			tables.with(buildOneTable14By3(iterator1));
			clearRows();
		}	
		Iterator<DataSet> iterator2 = getDataSets1By1().iterator();
		while (iterator2.hasNext()) {
			tables.with(buildOneTable1By1(iterator2));
			clearRows2();
		}	
		
		Iterator<DataSet> iterator3 =getDataSets14By14().iterator();
		while (iterator3.hasNext()) {
			tables.with(buildOneTable14By14(iterator3));
			clearRows14();
		}		
		Iterator<DataSet> iterator4 = getDataSets6By3().iterator();		
		while (iterator4.hasNext()) {
			tables.with(buildOneTableKeyPopn(iterator4));
			clearRows4();
		}
		Iterator<DataSet> iterator5 = getDataSets14By6().iterator();		
		while (iterator5.hasNext()) {
			tables.with(buildOneCtxTable(iterator5));
			clearRows8();
		}		
		Iterator<DataSet> iterator6 = getDataSets4By5().iterator();		
		while (iterator6.hasNext()) {
			tables.with(buildOneTbTable(iterator6));
			clearRows7();
		}
		
		Iterator<DataSet> iterator7 = getDataSets3By1().iterator();		
		while (iterator7.hasNext()) {
			tables.with(buildOneTbDiagnosisTestTable(iterator7));
			clearRows2();
		}
		
		Iterator<DataSet> iterator8 = getDataSets14By4().iterator();		
		while (iterator8.hasNext()) {
			tables.with(buildOne14By4Table(iterator8));
			clearRows7();
		}
		
		Iterator<DataSet> iterator9 = getDataSets6By2().iterator();		
		while (iterator9.hasNext()) {
			tables.with(buildOneTableKeyPopnBySingleRow(iterator9));
			clearRows3();
		}
		
		Iterator<DataSet> iterator10 = getDataSets4By7().iterator();		
		while (iterator10.hasNext()) {
			tables.with(buildOneTableForActivePatientsOverMonths(iterator10));
			clearRows9();
		}
		
		Iterator<DataSet> iterator11 = getDataSets14By9().iterator();		
		while (iterator11.hasNext()) {
			tables.with(buildOneTableForActivePatientsByRegimenLines(iterator11));
			clearRows11();
		}
		
		Iterator<DataSet> iterator12 = getDataSets10By4().iterator();		
		while (iterator12.hasNext()) {
			 tables.with(buildOneTableforCervicalCancerStatus(iterator12));
			clearRows6();
		}
		
		Iterator<DataSet> iterator13 = getDataSets10By4II().iterator();		
		while (iterator13.hasNext()) {
			tables.with(buildOneTableforCervicalCancerTreatment(iterator13));
			clearRows6();
		}
		
		Iterator<DataSet> iterator14 = getDataSets10By11().iterator();		
		while (iterator14.hasNext()) {
			tables.with(buildOneTableFamillyPlanning(iterator14));
			clearRows13();
		}
		return tables;
	}

	private ContainerTag buildOneTable14By3(Iterator<DataSet> iterator) {
		buildGenderSummaryTable();
		for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
			buildIndicator14By3(iterator.next());
		}		
		return table().with(getRows5());
	}
	
	private ContainerTag buildOneTable1By1(Iterator<DataSet> iterator) {
		for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
			buildIndicator1By1(iterator.next());
		}		
		return table().with(getRows2());
	}
	
	private ContainerTag buildOneTable14By14(Iterator<DataSet> iterator) {
		   buildNotEnrolledReasonSummaryTable();
		   buildNotEnrolledGenderSummaryTable();
		for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
			buildIndicator14By14(iterator.next());
		}		
		return table().with(getRows14());
	}
	
	private ContainerTag buildOneTableKeyPopn(Iterator<DataSet> iterator) {
		   buildPopulationSummaryTable();
		for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
			buildIndicatorKeyPopn(iterator.next());
		}		
		return table().with(getRows4());
	}
	
	private ContainerTag buildOneTableKeyPopnBySingleRow(Iterator<DataSet> iterator) {
		for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
			buildIndicatorKeyPopnSingleRow(iterator.next());
		}		
		return table().with(getRows3());
	}
	
	private ContainerTag buildOneCtxTable(Iterator<DataSet> iterator) {
		    buildCtxSummaryTable();
		    buildCtxGenderSummaryTable();
		for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
			buildCtxIndicator(iterator.next());
		}		
		return table().with(getRows8());
	}
	
	private ContainerTag buildOneTableForActivePatientsByRegimenLines(Iterator<DataSet> iterator) {
	    buildRegimeLinesSummaryTable();
	    buildRegimensLineGenderSummaryTable();
	for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
		buildRegimensLineIndicator(iterator.next());
	}		
	return table().with(getRows11());
 }
	
	private ContainerTag buildOneTbTable(Iterator<DataSet> iterator) {
		 buildTbSummaryTable();
	     buildTbGenderSummaryTable();
	for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
		buildTbIndicator(iterator.next());
	}		
	return table().with(getRows7());
   }
	
	private ContainerTag buildOneTableForActivePatientsOverMonths(Iterator<DataSet> iterator) {
		 buildPatientSummaryOverMonthsTable();
		 buildTbGenderSummaryTableOverMonhs();
	for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
		buildIndicatorFotArvPatientsOverMonths(iterator.next());
	}		
	return table().with(getRows9());
  }
	
	private ContainerTag buildOne14By4Table(Iterator<DataSet> iterator) {
		 buildTbSummaryTable();
	     buildTbGenderSummaryTable();
	for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
		buildIndicator14By4(iterator.next());
	}		
	return table().with(getRows7());
  }
	
	private ContainerTag buildOneTbDiagnosisTestTable(Iterator<DataSet> iterator) {
		buildTbDiagnosisTestSummaryTable();
	for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
		buildTbDagnosisTestIndicator(iterator.next());
	}		
	return table().with(getRows2());
  }
	
	
	private ContainerTag buildOneTableforCervicalCancerStatus(Iterator<DataSet> iterator) {
		buildCancerSummaryTable();
		for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
			buildIndicatorCervicalCancerStatus(iterator.next());
		}		
		return table().with(getRows6());
	}
	
	private ContainerTag buildOneTableforCervicalCancerTreatment(Iterator<DataSet> iterator) {
		buildCancerTreatmentSummaryTable();
		for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
			buildIndicatorCervicalCancerTreatment(iterator.next());
		}		
		return table().with(getRows6());
	}
	
	private ContainerTag buildOneTableFamillyPlanning(Iterator<DataSet> iterator) {		   
		   buildFpMethodSummaryTable();
		   buildFpGenderSummaryTable();
		for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
			buildIndicatorFamilyPlanning(iterator.next());
		}		
		return table().with(getRows13());
	}
	
	private void buildGenderSummaryTable() {
		fillEmptyRow(getRows5()[0], 1);
		fillEmptyRow(getRows5()[1], 1);
		ContainerTag maleLabel = getRows5()[2];
		ContainerTag femaleLable = getRows5()[3];
		ContainerTag Subtotal = getRows5()[4];
		maleLabel.with(th(translateLabel("Males")).withClass("label"));
		femaleLable.with(th(translateLabel("FeMales")).withClass("label"));
		Subtotal.with(th(translateLabel("Subtotal")).withClass("label"));		
	}
	

	private void buildCancerSummaryTable() {
		fillEmptyRow(getRows6()[0], 1);
		fillEmptyRow(getRows6()[1], 1);
		ContainerTag negative = getRows6()[2];
		ContainerTag postive = getRows6()[3];
		ContainerTag suspected = getRows6()[4];
		ContainerTag Subtotal = getRows6()[5];
		negative.with(th(translateLabel("negative")).withClass("label"));
		postive.with(th(translateLabel("postive")).withClass("label"));
		suspected .with(th(translateLabel("suspected")).withClass("label"));
		Subtotal.with(th(translateLabel("Subtotal")).withClass("label"));		
	}
	
	
	private void buildCancerTreatmentSummaryTable() {
		fillEmptyRow(getRows6()[0], 1);
		fillEmptyRow(getRows6()[1], 1);
		ContainerTag negative = getRows6()[2];
		ContainerTag postive = getRows6()[3];
		ContainerTag suspected = getRows6()[4];
		ContainerTag Subtotal = getRows6()[5];
		negative.with(th(translateLabel("cryo")).withClass("label"));
		postive.with(th(translateLabel("thermo")).withClass("label"));
		suspected .with(th(translateLabel("leep")).withClass("label"));
		Subtotal.with(th(translateLabel("Subtotal")).withClass("label"));		
	}
	
	private void buildPopulationSummaryTable() {
		fillEmptyRow(getRows4()[0], 1);
		fillEmptyRow(getRows4()[1], 1);
		ContainerTag newEnroll = getRows4()[2];
		ContainerTag reffrenced = getRows4()[3];
		newEnroll.with(th(translateLabel("NewPatients")).withClass("label"));
		reffrenced.with(th(translateLabel("Referenced")).withClass("label"));
	}
	
	private void buildCtxSummaryTable() {
		fillEmptyRow(getRows8()[0], 1);
		fillEmptyRow(getRows8()[1], 1);
		ContainerTag newCtx = getRows8()[2];
		ContainerTag newTotal = getRows8()[4];
		ContainerTag activeCtx = getRows8()[5];
		ContainerTag activeTotal = getRows8()[7];
		newCtx.with(th(translateLabel("newCtx")).attr("rowspan", "2").withClass("label"));
		activeCtx.with(th(translateLabel("activeCtx")).attr("rowspan", "2").withClass("label"));
		newTotal.with(th(translateLabel("Total")).withClass("label"));
		activeTotal.with(th(translateLabel("Total")).withClass("label"));
	}
	
	private void  buildRegimeLinesSummaryTable() {
		fillEmptyRow(getRows11()[0], 1);
		fillEmptyRow(getRows11()[1], 1);
		ContainerTag regime1 = getRows11()[2];
		ContainerTag regime1Total = getRows11()[4];
		ContainerTag regime2 = getRows11()[5];
		ContainerTag regime2Total = getRows11()[7];
		ContainerTag regime3 = getRows11()[8];
		ContainerTag regime3Total = getRows11()[10];
		regime1.with(th(translateLabel("regime1")).attr("rowspan", "2").withClass("label"));
		regime2.with(th(translateLabel("regime2")).attr("rowspan", "2").withClass("label"));
		regime3.with(th(translateLabel("regime3")).attr("rowspan", "2").withClass("label"));
		regime1Total.with(th(translateLabel("Total")).withClass("label"));
		regime2Total.with(th(translateLabel("Total")).withClass("label"));
		regime3Total.with(th(translateLabel("Total")).withClass("label"));
	}
	
	private void buildTbSummaryTable() {
		fillEmptyRow(getRows7()[0], 1);
		fillEmptyRow(getRows7()[1], 1);
		ContainerTag newlyTb = getRows7()[2];
		ContainerTag AlreadyTb = getRows7()[4];
		ContainerTag Total = getRows7()[6];
		newlyTb.with(th(translateLabel("newTb")).attr("rowspan", "2").withClass("label"));
		AlreadyTb.with(th(translateLabel("alreadyTb")).attr("rowspan", "2").withClass("label"));
		Total.with(th(translateLabel("Total")).withClass("label"));
	}
	
	private void buildPatientSummaryOverMonthsTable() {
		fillEmptyRow(getRows9()[0], 1);
		fillEmptyRow(getRows9()[1], 1);
		ContainerTag less3Months = getRows9()[2];
		ContainerTag btn3_5Months = getRows9()[4];
		ContainerTag over5Months = getRows9()[6];
		ContainerTag Total = getRows9()[8];
		less3Months.with(th(translateLabel("less3M")).attr("rowspan", "2").withClass("label"));
		btn3_5Months.with(th(translateLabel("btn3_5M")).attr("rowspan", "2").withClass("label"));
		over5Months.with(th(translateLabel("over5M")).attr("rowspan", "2").withClass("label"));
		Total.with(th(translateLabel("Total")).withClass("label"));
	}
	
	private void buildTbDiagnosisTestSummaryTable(){
		fillEmptyRow(getRows2()[0], 1);
		ContainerTag diagnosisTestLable = getRows2()[1];
		diagnosisTestLable.with(th(translateString(PnlsReportConstants.ARV_PATIENTS_WITH_SAMPLES_SENT_TO_DIAGNOSTIC_TEST_MESSAGE)).withClass("label"));
	}
	
	private void buildNotEnrolledReasonSummaryTable() {
		fillEmptyRow(getRows14()[0], 1);
		fillEmptyRow(getRows14()[1], 1);
		ContainerTag A = getRows14()[2];
		ContainerTag B = getRows14()[4];
		ContainerTag C = getRows14()[6];
		ContainerTag D = getRows14()[8];
		ContainerTag E = getRows14()[10];
		ContainerTag F = getRows14()[12];
		ContainerTag Subtotal = getRows14()[14];
		A.with(th(translateLabel("died")).attr("rowspan", "2").withClass("label"));
		B.with(th(translateLabel("voluntary")).attr("rowspan", "2").withClass("label"));
		C.with(th(translateLabel("denial")).attr("rowspan", "2").withClass("label"));
		D.with(th(translateLabel("medical")).attr("rowspan", "2").withClass("label"));
		E.with(th(translateLabel("refered")).attr("rowspan", "2").withClass("label"));
		F.with(th(translateLabel("other")).attr("rowspan", "2").withClass("label"));
		Subtotal.with(th(translateLabel("Total")).withClass("label"));		
	}
	
	private void buildFpMethodSummaryTable() {
		fillEmptyRow(getRows13()[0], 1);
		fillEmptyRow(getRows13()[1], 1);
		ContainerTag pills = getRows13()[2];
		ContainerTag inject = getRows13()[3];
		ContainerTag impl = getRows13()[4];
		ContainerTag vagTabs = getRows13()[5];
		ContainerTag lam = getRows13()[6];
		ContainerTag necklace = getRows13()[7];
		ContainerTag condom = getRows13()[8];
		ContainerTag ccv = getRows13()[10];
		ContainerTag Subtotal = getRows13()[12];
		pills.with(th(translateLabel("pills")).attr("rowspan", "1").withClass("label"));
		inject.with(th(translateLabel("inject")).attr("rowspan", "1").withClass("label"));
		impl.with(th(translateLabel("impl")).attr("rowspan", "1").withClass("label"));
		vagTabs.with(th(translateLabel("vagTabs")).attr("rowspan", "1").withClass("label"));
		lam.with(th(translateLabel("lam")).attr("rowspan", "1").withClass("label"));
		necklace.with(th(translateLabel("necklace")).attr("rowspan", "1").withClass("label"));
		condom .with(th(translateLabel("condom")).attr("rowspan", "2").withClass("label"));		
		ccv.with(th(translateLabel("ccv")).attr("rowspan", "2").withClass("label"));
		Subtotal.with(th(translateLabel("Total")).withClass("label"));			
	}
	
	private void buildNotEnrolledGenderSummaryTable() {
		fillEmptyRow(getRows14()[0], 1);
		fillEmptyRow(getRows14()[1], 1);
		ContainerTag MA = getRows14()[2];
		ContainerTag FA = getRows14()[3];
		ContainerTag MB = getRows14()[4];
		ContainerTag FB = getRows14()[5];
		ContainerTag MC = getRows14()[6];
		ContainerTag FC = getRows14()[7];
		ContainerTag MD = getRows14()[8];
		ContainerTag FD = getRows14()[9];
		ContainerTag ME = getRows14()[10];
		ContainerTag FE = getRows14()[11];
		ContainerTag MF = getRows14()[12];
		ContainerTag FF = getRows14()[13];
		ContainerTag T = getRows14()[14];
		MA.with(th("M").withClass("label"));
		FA.with(th("F").withClass("label"));
		MB.with(th("M").withClass("label"));
		FB.with(th("F").withClass("label"));
		MC.with(th("M").withClass("label"));
		FC.with(th("F").withClass("label"));
		MD.with(th("M").withClass("label"));
		FD.with(th("F").withClass("label"));
		ME.with(th("M").withClass("label"));
		FE.with(th("F").withClass("label"));
		MF.with(th("M").withClass("label"));
		FF.with(th("F").withClass("label"));
		T.with(th(".").withClass("label"));
	}
	
	private void buildFpGenderSummaryTable() {
		fillEmptyRow(getRows13()[0], 1);
		fillEmptyRow(getRows13()[1], 1);
		ContainerTag FA = getRows13()[2];
		ContainerTag MB = getRows13()[8];
		ContainerTag FB = getRows13()[9];
		ContainerTag MC = getRows13()[10];
		ContainerTag FC = getRows13()[11];
		ContainerTag T = getRows13()[12];
		FA.with(th(translateLabel("FeMales")).attr("rowspan", "6").withClass("label"));	
		MB.with(th(translateLabel("Males")).withClass("label"));
		FB.with(th(translateLabel("FeMales")).withClass("label"));
		MC.with(th(translateLabel("Males")).withClass("label"));
		FC.with(th(translateLabel("FeMales")).withClass("label"));
		T.with(th(".").withClass("label"));
	}
	
	private void buildCtxGenderSummaryTable() {
		fillEmptyRow(getRows8()[0], 1);
		fillEmptyRow(getRows8()[1], 1);
		ContainerTag MN = getRows8()[2];
		ContainerTag FN = getRows8()[3];
		ContainerTag E1 = getRows8()[4];
		ContainerTag MA = getRows8()[5];
		ContainerTag FA = getRows8()[6];
		ContainerTag E2 = getRows8()[7];
		MN.with(th("M").withClass("label"));
		FN.with(th("F").withClass("label"));
		MA.with(th("M").withClass("label"));
		FA.with(th("F").withClass("label"));
		E1.with(th(".").withClass("label"));
		E2.with(th(".").withClass("label"));
	}
	
	private void buildRegimensLineGenderSummaryTable() {
		fillEmptyRow(getRows11()[0], 1);
		fillEmptyRow(getRows11()[1], 1);
		ContainerTag M1 = getRows11()[2];
		ContainerTag F1 = getRows11()[3];
		ContainerTag E1 = getRows11()[4];
		ContainerTag M2 = getRows11()[5];
		ContainerTag F2 = getRows11()[6];
		ContainerTag E2 = getRows11()[7];
		ContainerTag M3 = getRows11()[8];
		ContainerTag F3 = getRows11()[9];
		ContainerTag E3 = getRows11()[10];
		M1.with(th("M").withClass("label"));
		F1.with(th("F").withClass("label"));
		M2.with(th("M").withClass("label"));
		F2.with(th("F").withClass("label"));
		M3.with(th("M").withClass("label"));
		F3.with(th("F").withClass("label"));
		E1.with(th(".").withClass("label"));
		E2.with(th(".").withClass("label"));
		E3.with(th(".").withClass("label"));
	}
	
	private void buildTbGenderSummaryTable() {
		fillEmptyRow(getRows7()[0], 1);
		fillEmptyRow(getRows7()[1], 1);
		ContainerTag MA = getRows7()[2];
		ContainerTag FA = getRows7()[3];
		ContainerTag MB = getRows7()[4];
		ContainerTag FB = getRows7()[5];
		ContainerTag E2 = getRows7()[6];
		MA.with(th("M").withClass("label"));
		FA.with(th("F").withClass("label"));
		MB.with(th("M").withClass("label"));
		FB.with(th("F").withClass("label"));
		E2.with(th(".").withClass("label"));
	}
	
	private void buildTbGenderSummaryTableOverMonhs() {
		fillEmptyRow(getRows9()[0], 1);
		fillEmptyRow(getRows9()[1], 1);
		ContainerTag MA = getRows9()[2];
		ContainerTag FA = getRows9()[3];
		ContainerTag MB = getRows9()[4];
		ContainerTag FB = getRows9()[5];
		ContainerTag MC = getRows9()[6];
		ContainerTag FC = getRows9()[7];
		ContainerTag E2 = getRows9()[8];
		MA.with(th("M").withClass("label"));
		FA.with(th("F").withClass("label"));
		MB.with(th("M").withClass("label"));
		FB.with(th("F").withClass("label"));
		MC.with(th("M").withClass("label"));
		FC.with(th("F").withClass("label"));
		E2.with(th(".").withClass("label"));
	}
				
	private void buildIndicator14By14(DataSet data) {
		getRows14()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "14").withClass("indicatorLabel"));
		getRows14()[1].with(td(translateLabel("1-0")).attr("colspan", "1").withClass("label"),
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
				
		int[] dataSet = createSummaryArray(getColumnNamesList14By14(),data);
		String reportName = data.getDefinition().getName();
		buildSummary14By14(dataSet ,getColumnNamesArray14By14(),reportName );	
	}
	
	private void buildIndicator14By3(DataSet data) {
		getRows5()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "14").withClass("indicatorLabel"));
		getRows5()[1].with(td(translateLabel("1-0")).attr("colspan", "1").withClass("label"),
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
				
		int[] dataSet = createSummaryArray(getColumnNamesList14By3() ,data);
		String reportName = data.getDefinition().getName();
		buildIndicatorSummary14By3(dataSet ,getColumnNamesArray14By3(),reportName );	
	}
	
	private void buildIndicatorCervicalCancerStatus(DataSet data) {
		getRows6()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "10").withClass("indicatorLabel"));
		getRows6()[1].with( td(translateLabel("15-19")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("20-24")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("25-29")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("30-34")) .attr("colspan", "1").withClass("label"),                  
                          td(translateLabel("35-39")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("40-44")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("45-49")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("50")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("UnKnown")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("Total")) .attr("colspan", "1").withClass("label")) ;
				
		int[] dataSet = createSummaryArray(getwomenCancerStatusColumnNamesList() ,data);
		String reportName = data.getDefinition().getName();
		buildIndicatorSummary10By4(dataSet ,getwomenCancerStatusColumnNamesArray(),reportName );	
	}
	
	private void buildIndicatorCervicalCancerTreatment(DataSet data) {
		getRows6()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "10").withClass("indicatorLabel"));
		getRows6()[1].with( td(translateLabel("15-19")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("20-24")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("25-29")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("30-34")) .attr("colspan", "1").withClass("label"),                  
                          td(translateLabel("35-39")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("40-44")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("45-49")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("50")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("UnKnown")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("Total")) .attr("colspan", "1").withClass("label")) ;
				
		int[] dataSet = createSummaryArray(getwomenCancerTreatmentColumnNamesList() ,data);
		String reportName = data.getDefinition().getName();
		buildIndicatorTreatmentSummary(dataSet ,getwomenCancerTreatmentColumnNamesArray(),reportName );	
		final int ROW = 5;
		populateTable6WithSum(ROW, dataSet[0],dataSet[10],dataSet[20] );
		populateTable6WithSum(ROW, dataSet[1],dataSet[11],dataSet[21] );
		populateTable6WithSum(ROW, dataSet[2],dataSet[12],dataSet[22] );
		populateTable6WithSum(ROW, dataSet[3],dataSet[13],dataSet[23] );
		populateTable6WithSum(ROW, dataSet[4],dataSet[14],dataSet[24] );
		populateTable6WithSum(ROW, dataSet[5],dataSet[15],dataSet[25] );
		populateTable6WithSum(ROW, dataSet[6],dataSet[16],dataSet[26] );
		populateTable6WithSum(ROW, dataSet[7],dataSet[17],dataSet[27] );
		populateTable6WithSum(ROW, dataSet[8],dataSet[18],dataSet[28] );
		populateTable6WithSum(ROW, dataSet[9],dataSet[19],dataSet[29] );
		
	}
	
	private void buildIndicatorFamilyPlanning(DataSet data) {
		getRows13()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "10").withClass("indicatorLabel"));
		getRows13()[1].with( td(translateLabel("15-19")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("20-24")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("25-29")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("30-34")) .attr("colspan", "1").withClass("label"),                  
                          td(translateLabel("35-39")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("40-44")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("45-49")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("50")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("UnKnown")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("Total")) .attr("colspan", "1").withClass("label")) ;
				
		int[] dataSet = createSummaryArray(getFamilyPlanningColumnNamesList() ,data);
		String reportName = data.getDefinition().getName();
		buildIndicatorFamillyPlanningSummary(dataSet ,getFamilyPlanningColumnNamesArray(),reportName );
		final int ROW = 12;
		populateTable13WithSum(ROW, dataSet[0],dataSet[10],dataSet[20],dataSet[30],dataSet[40],dataSet[50],dataSet[60],dataSet[70],dataSet[80],dataSet[90] );
		populateTable13WithSum(ROW, dataSet[1],dataSet[11],dataSet[21],dataSet[31],dataSet[41],dataSet[51],dataSet[61],dataSet[71],dataSet[81],dataSet[91] );
		populateTable13WithSum(ROW, dataSet[2],dataSet[12],dataSet[22],dataSet[32],dataSet[42],dataSet[52],dataSet[62],dataSet[72],dataSet[82],dataSet[92] );
		populateTable13WithSum(ROW, dataSet[3],dataSet[13],dataSet[23],dataSet[33],dataSet[43],dataSet[53],dataSet[63],dataSet[73],dataSet[83],dataSet[93] );
		populateTable13WithSum(ROW, dataSet[4],dataSet[14],dataSet[24],dataSet[34],dataSet[44],dataSet[54],dataSet[64],dataSet[74],dataSet[84],dataSet[94] );
		populateTable13WithSum(ROW, dataSet[5],dataSet[15],dataSet[25],dataSet[35],dataSet[45],dataSet[55],dataSet[65],dataSet[75],dataSet[85],dataSet[95] );
		populateTable13WithSum(ROW, dataSet[6],dataSet[16],dataSet[26],dataSet[36],dataSet[46],dataSet[56],dataSet[66],dataSet[76],dataSet[86],dataSet[96] );
		populateTable13WithSum(ROW, dataSet[7],dataSet[17],dataSet[27],dataSet[37],dataSet[47],dataSet[57],dataSet[67],dataSet[77],dataSet[87],dataSet[97] );
		populateTable13WithSum(ROW, dataSet[8],dataSet[18],dataSet[28],dataSet[38],dataSet[48],dataSet[58],dataSet[68],dataSet[78],dataSet[88],dataSet[98] );
		populateTable13WithSum(ROW, dataSet[9],dataSet[19],dataSet[29],dataSet[39],dataSet[49],dataSet[59],dataSet[69],dataSet[79],dataSet[89],dataSet[99] );		
	}
	
	private void buildIndicator14By4(DataSet data) {
		getRows7()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "14").withClass("indicatorLabel"));
		getRows7()[1].with(td(translateLabel("1-0")).attr("colspan", "1").withClass("label"),
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
				
		int[] dataSet = createSummaryArray(getColumnNamesList14By4() ,data);
		String reportName = data.getDefinition().getName();
		buildIndicatorSummary14By4(dataSet ,getColumnNamesArray14By4(),reportName );
		final int ROW = 6;
		populateTable7WithSum(ROW ,dataSet[0],dataSet[14], dataSet[28] , dataSet [42]);
		populateTable7WithSum(ROW ,dataSet[1],dataSet[15], dataSet[29] , dataSet [43]);
		populateTable7WithSum(ROW ,dataSet[2],dataSet[16], dataSet[30] , dataSet [44]);
		populateTable7WithSum(ROW ,dataSet[3],dataSet[17], dataSet[31] , dataSet [45]);
		populateTable7WithSum(ROW ,dataSet[4],dataSet[18], dataSet[32] , dataSet [46]);
		populateTable7WithSum(ROW ,dataSet[5],dataSet[19], dataSet[33] , dataSet [47]);
		populateTable7WithSum(ROW ,dataSet[6],dataSet[20], dataSet[34] , dataSet [48]);
		populateTable7WithSum(ROW ,dataSet[7],dataSet[21], dataSet[35] , dataSet [49]);
		populateTable7WithSum(ROW ,dataSet[8],dataSet[22], dataSet[36] , dataSet [50]);
		populateTable7WithSum(ROW ,dataSet[9],dataSet[23], dataSet[37] , dataSet [51]);
		populateTable7WithSum(ROW ,dataSet[10],dataSet[24], dataSet[38] , dataSet [52]);
		populateTable7WithSum(ROW ,dataSet[11],dataSet[25], dataSet[39] , dataSet [53]);
		populateTable7WithSum(ROW ,dataSet[12],dataSet[26], dataSet[40] , dataSet [54]);
		populateTable7WithSum(ROW ,dataSet[13],dataSet[27], dataSet[41] , dataSet [55]);
	}
	
	
	private void buildCtxIndicator(DataSet data) {
		getRows8()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "14").withClass("indicatorLabel"));
		getRows8()[1].with(td(translateLabel("1-0")).attr("colspan", "1").withClass("label"),
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
				
		int[] dataSet = createSummaryArray(getCtxColumnNamesList() ,data);
		String reportName = data.getDefinition().getName();
		buildIndicatorSummary14By6(dataSet ,getCtxColumnNamesArray(),reportName );	
	}
	
	private void buildRegimensLineIndicator(DataSet data) {
		getRows11()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "14").withClass("indicatorLabel"));
		getRows11()[1].with(td(translateLabel("1-0")).attr("colspan", "1").withClass("label"),
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
				
		int[] dataSet = createSummaryArray(getActivePatientRegimenLinesColumnNamesList() ,data);
		String reportName = data.getDefinition().getName();
		buildIndicatorSummary14By9(dataSet ,getActivePatientRegimenLinesColumnNamesArray(),reportName );	
	}
	
	private void buildTbIndicator(DataSet data) {
		getRows7()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "4").withClass("indicatorLabel"));
		getRows7()[1].with(td(translateLabel("<15")).attr("colspan", "1").withClass("label"),
		                  td(translateLabel(">15")).attr("colspan", "1").withClass("label"),
                          td(translateLabel("UnKnown")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("Total")) .attr("colspan", "1").withClass("label")) ;
				
		int[] dataSet = createSummaryArray(getAgeBy15ColumnNamesList() ,data);
		String reportName = data.getDefinition().getName();
		buildIndicatorSummary4By5(dataSet ,getAgeBy15ColumnNamesArray(),reportName );
		final int ROW = 6;
		populateTable7WithSum(ROW ,dataSet[0],dataSet[4], dataSet[8] , dataSet [12]);
		populateTable7WithSum(ROW ,dataSet[1],dataSet[5], dataSet[9] , dataSet [13]);
		populateTable7WithSum(ROW ,dataSet[2],dataSet[6], dataSet[10] , dataSet [14]);
		populateTable7WithSum(ROW ,dataSet[3],dataSet[7], dataSet[11] , dataSet [15]);
	}
	
	private void buildIndicatorFotArvPatientsOverMonths(DataSet data) {
		getRows9()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "4").withClass("indicatorLabel"));
		getRows9()[1].with(td(translateLabel("<15")).attr("colspan", "1").withClass("label"),
		                  td(translateLabel(">15")).attr("colspan", "1").withClass("label"),
                          td(translateLabel("UnKnown")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("Total")) .attr("colspan", "1").withClass("label")) ;
				
		int[] dataSet = createSummaryArray(getColumnNamesList4By7() ,data);
		String reportName = data.getDefinition().getName();
		buildIndicatorSummary4By7(dataSet ,getColumnNamesArray4By7(),reportName );
		final int ROW = 8;
		populateTable9WithSum(ROW ,dataSet[0],dataSet[4], dataSet[8] , dataSet [12], dataSet [16], dataSet [20]);
		populateTable9WithSum(ROW ,dataSet[1],dataSet[5], dataSet[9] , dataSet [13], dataSet [17], dataSet [21]);
		populateTable9WithSum(ROW ,dataSet[2],dataSet[6], dataSet[10] , dataSet [14], dataSet [18], dataSet [22]);
		populateTable9WithSum(ROW ,dataSet[3],dataSet[7], dataSet[11] , dataSet [15], dataSet [19], dataSet [23]);
		
	}
	
	private void buildTbDagnosisTestIndicator(DataSet data) {
		getRows2()[0].with(td(translateLabel("geneExpertTest")).attr("colspan", "1").withClass("label"),
		                  td(translateLabel("OtherTest")).attr("colspan", "1").withClass("label"),
                          td(translateLabel("Total")) .attr("colspan", "1").withClass("label")) ;
				
		int[] dataSet = createSummaryArray(getArvPatientsByTbDiagnosisTestColumnNamesList() ,data);
		String reportName = data.getDefinition().getName();
		final int ROW1 = 1;
		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		String row1 =  ConstructUrl(reportUrl ,reportName, getArvPatientsByTbDiagnosisTestColumnNamesArray()[0]);
		String row2 =  ConstructUrl(reportUrl ,reportName, getArvPatientsByTbDiagnosisTestColumnNamesArray()[1]);
		populateTable2(ROW1, dataSet[0],row1);
		populateTable2(ROW1, dataSet[1],row2);
		getRows2()[ROW1].with(td(Integer.toString(dataSet[0]+ dataSet[1])));
	}
	
	
	private void buildIndicatorKeyPopn(DataSet data) {
		getRows4()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "6").withClass("indicatorLabel"));
		getRows4()[1].with(td(translateLabel("msm")).attr("colspan", "1").withClass("label"),
		                  td(translateLabel("sexP")).attr("colspan", "1").withClass("label"),
		                  td(translateLabel("trans")) .attr("colspan", "1").withClass("label"),
		                  td(translateLabel("captv")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("drug")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("Total")) .attr("colspan", "1").withClass("label")) ;				
		int[] dataSet = createSummaryArray(getKeyPopnColumnNamesList() ,data);
		String reportName = data.getDefinition().getName();
		buildIndicatorSummaryKeyPopn(dataSet ,getKeyPopnColumnNamesArray(),reportName );	
	}
	
	private void buildIndicatorKeyPopnSingleRow(DataSet data) {
		getRows3()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "6").withClass("indicatorLabel"));
		getRows3()[1].with(td(translateLabel("msm")).attr("colspan", "1").withClass("label"),
		                  td(translateLabel("sexP")).attr("colspan", "1").withClass("label"),
		                  td(translateLabel("trans")) .attr("colspan", "1").withClass("label"),
		                  td(translateLabel("captv")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("drug")) .attr("colspan", "1").withClass("label"),
                          td(translateLabel("Total")) .attr("colspan", "1").withClass("label")) ;				
		int[] dataSet = createSummaryArray(getKeyPopnSingleRowColumnNamesList() ,data);
		String reportName = data.getDefinition().getName();
		buildSummaryKeyPopnSingleRow(dataSet ,getKeyPopnSingleRowColumnNamesArray(),reportName );
		populateTable(2, dataSet[0]+ dataSet[1]+ dataSet[2]+ dataSet[3] + dataSet[4] );
	}
	
	private void buildIndicator1By1(DataSet data){
		getRows2()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "1").withClass("indicatorLabel"));

		int[] dataSet = createSummaryArray(getBreastFeedingColumnNamesList() ,data);
		String reportName = data.getDefinition().getName();
		final int ROW1 = 1;
		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

		String row1 =  ConstructUrl(reportUrl ,reportName, getBreastFeedingColumnNamesArray()[0]);
		populateTable2(ROW1, dataSet[0],row1);
	}
	
	private void buildIndicatorSummary14By3(int[] dataArray ,String[] columnsArray, String reportName ) {

		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		
		int colCount = 0;
		for (int ROW = 2; ROW <= 4 ; ROW ++) {			
		    for (int col = 0; col <= 13 ; col ++) {
		    	 if(colCount < dataArray.length) {
			        String row =  ConstructUrl(reportUrl ,reportName, columnsArray[colCount ]);
			        populateTable(ROW,dataArray[colCount ],row); 
			        colCount ++ ;
			    }
			 }
		} 	
	}
	
	private void buildIndicatorSummary10By4(int[] dataArray ,String[] columnsArray, String reportName ) {

		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		
		int colCount = 0;
		for (int ROW = 2; ROW <= 5 ; ROW ++) {			
		    for (int col = 0; col <= 9 ; col ++) {
		    	 if(colCount < dataArray.length) {
			        String row =  ConstructUrl(reportUrl ,reportName, columnsArray[colCount ]);
			        populateTable6(ROW,dataArray[colCount ],row); 
			        colCount ++ ;
			    }
			 }
		} 	
	}
	
	private void buildIndicatorTreatmentSummary(int[] dataArray ,String[] columnsArray, String reportName ) {

		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		
		int colCount = 0;
		for (int ROW = 2; ROW <= 4 ; ROW ++) {			
		    for (int col = 0; col <= 9 ; col ++) {
		    	 if(colCount < dataArray.length) {
			        String row =  ConstructUrl(reportUrl ,reportName, columnsArray[colCount ]);
			        populateTable6(ROW,dataArray[colCount ],row); 
			        colCount ++ ;
			    }
			 }
		} 	
	}
	
	private void buildIndicatorFamillyPlanningSummary(int[] dataArray ,String[] columnsArray, String reportName ) {

		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		
		int colCount = 0;
		for (int ROW = 2; ROW <= 11 ; ROW ++) {			
		    for (int col = 0; col <= 9 ; col ++) {
		    	 if(colCount < dataArray.length) {
			        String row =  ConstructUrl(reportUrl ,reportName, columnsArray[colCount ]);
			        populateTable13(ROW,dataArray[colCount ],row); 
			        colCount ++ ;
			    }
			 }
		} 	
	}
	
	private void buildIndicatorSummary14By4(int[] dataArray ,String[] columnsArray, String reportName ) {

		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		
		int colCount = 0;
		for (int ROW = 2; ROW <= 5 ; ROW ++) {			
		    for (int col = 0; col <= 13 ; col ++) {
		    	 if(colCount < dataArray.length) {
			        String row =  ConstructUrl(reportUrl ,reportName, columnsArray[colCount ]);
			        populateTable7(ROW,dataArray[colCount ],row); 
			        colCount ++ ;
			    }
			 }
		} 	
	}
	
	private void buildIndicatorSummary14By6(int[] dataArray ,String[] columnsArray, String reportName ) {

		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		
		int colCount = 0;
		for (int ROW = 2; ROW <= 7 ; ROW ++) {			
		    for (int col = 0; col <= 13 ; col ++) {
		    	 if(colCount < dataArray.length) {
			        String row =  ConstructUrl(reportUrl ,reportName, columnsArray[colCount ]);
			        populateTable8(ROW,dataArray[colCount ],row); 
			        colCount ++ ;
			    }
			 }
		} 	
	}
	
	private void buildIndicatorSummary14By9(int[] dataArray ,String[] columnsArray, String reportName ) {

		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		
		int colCount = 0;
		for (int ROW = 2; ROW <= 10 ; ROW ++) {			
		    for (int col = 0; col <= 13 ; col ++) {
		    	 if(colCount < dataArray.length) {
			        String row =  ConstructUrl(reportUrl ,reportName, columnsArray[colCount ]);
			        populateTable11(ROW,dataArray[colCount ],row); 
			        colCount ++ ;
			    }
			 }
		} 	
	}
	
	private void buildIndicatorSummary4By5(int[] dataArray ,String[] columnsArray, String reportName ) {

		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		
		int colCount = 0;
		for (int ROW = 2; ROW <= 5 ; ROW ++) {			
		    for (int col = 0; col <= 3 ; col ++) {
		    	 if(colCount < dataArray.length) {
			        String row =  ConstructUrl(reportUrl ,reportName, columnsArray[colCount ]);
			        populateTable7(ROW,dataArray[colCount ],row); 
			        colCount ++ ;
			    }
			 }
		} 	
	}
	
	private void buildIndicatorSummary4By7(int[] dataArray ,String[] columnsArray, String reportName ) {

		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		
		int colCount = 0;
		for (int ROW = 2; ROW <= 7 ; ROW ++) {			
		    for (int col = 0; col <= 3 ; col ++) {
		    	 if(colCount < dataArray.length) {
			        String row =  ConstructUrl(reportUrl ,reportName, columnsArray[colCount ]);
			        populateTable9(ROW,dataArray[colCount ],row); 
			        colCount ++ ;
			    }
			 }
		} 	
	}
	
	private void buildIndicatorSummaryKeyPopn(int[] dataArray ,String[] columnsArray, String reportName ) {

		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		
		int colCount = 0;
		for (int ROW = 2; ROW <= 3 ; ROW ++) {			
		    for (int col = 0; col <= 5 ; col ++) {
		    	 if(colCount < dataArray.length) {
			        String row =  ConstructUrl(reportUrl ,reportName, columnsArray[colCount]);
			        populateTable4(ROW,dataArray[colCount ],row); 
			        colCount ++ ;
			    }
			 }
		} 	
	}
	
  private void buildSummary14By14(int[] dataArray ,String[] columnsArray, String reportName ) {		
		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		int colCount = 0;
		for (int ROW = 2; ROW <= 14 ; ROW ++) {			
		    for (int col = 0; col <= 13 ; col ++) {
		    	 if(colCount < dataArray.length) {
			        String row =  ConstructUrl(reportUrl ,reportName, columnsArray[colCount ]);
			        populateTable14By14(ROW,dataArray[colCount ],row); 
			        colCount ++ ;
			    }
			 }
		} 
		
	}
  
  private void buildSummaryKeyPopnSingleRow(int[] dataArray ,String[] columnsArray, String reportName ) {		
		String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
		int colCount = 0;
		for (int ROW = 2; ROW <= 2 ; ROW ++) {			
		    for (int col = 0; col <= 4 ; col ++) {
		    	 if(colCount < dataArray.length) {
			        String row =  ConstructUrl(reportUrl ,reportName, columnsArray[colCount ]);
			        populateTable3(ROW,dataArray[colCount ],row); 
			        colCount ++ ;
			    }
			 }
		} 
		
	}
	
	private String ConstructUrl( String reportBaseUrl , String reportName,String columnName) {
		 return String.format("%s?savedDataSetKey=%s&savedColumnKey=%s&columnKeyType=numerator", reportBaseUrl, reportName, columnName);
	}
	
	private void populateTable14By14(Integer ROW ,int data , String rowLink) {
		getRows14()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}
		
	private void populateTable(Integer ROW ,int data , String rowLink) {
		getRows5()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}
	
	private void populateTable6(Integer ROW ,int data , String rowLink) {
		getRows6()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}
	
	private void populateTable8(Integer ROW ,int data , String rowLink) {
		getRows8()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}
	
	private void populateTable11(Integer ROW ,int data , String rowLink) {
		getRows11()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}
	
	private void populateTable7(Integer ROW ,int data , String rowLink) {
		getRows7()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}
	

	private void populateTable9(Integer ROW ,int data , String rowLink) {
		getRows9()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}
	
	private void populateTable2(Integer ROW ,int data , String rowLink) {
		getRows2()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}
	
	private void populateTable3(Integer ROW ,int data , String rowLink) {
		getRows3()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}
	
	private void populateTable4(Integer ROW ,int data , String rowLink) {
		getRows4()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}
	
	private void populateTable13(Integer ROW ,int data , String rowLink) {
		getRows13()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}
	
	private void populateTable7WithSum(Integer ROW ,int data1 ,int data2 ,int data3 , int data4 ) {
		getRows7()[ROW].with(td(Integer.toString(data1 + data2 +data3 + data4)));
	}
	
	private void populateTable9WithSum(Integer ROW ,int data1 ,int data2 ,int data3 , int data4 , int data5 , int data6 ) {
		getRows9()[ROW].with(td(Integer.toString(data1 + data2 +data3 + data4  + data5  + data6)));
	}
	
	private void populateTable6WithSum(Integer ROW ,int data1 ,int data2 ,int data3  ) {
		getRows6()[ROW].with(td(Integer.toString(data1 + data2 +data3)));
	}
	
	private void populateTable13WithSum(Integer ROW ,int data1 ,int data2 ,int data3,int data4 ,int data5 ,int data6 ,int data7 ,int data8 ,int data9,int data10   ) {
		getRows13()[ROW].with(td(Integer.toString(data1 + data2 +data3 +data4 +data5 +data6 +data7 +data8 +data9 +data10)));
	}
	
	private void populateTable(Integer ROW , int data ) {
		getRows3()[ROW].with(td(Integer.toString(data)));
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
	
	public ContainerTag[] getRows5() {
		if (rows5 == null) {
			rows5 = new ContainerTag[ROWS_5];
			for (int i = 0; i < ROWS_5; ++i) {
				rows5[i] = tr();
			}
		}
		return rows5;
	}
	
	public ContainerTag[] getRows4() {
		if (rows4 == null) {
			rows4 = new ContainerTag[ROWS_4];
			for (int i = 0; i < ROWS_4; ++i) {
				rows4[i] = tr();
			}
		}
		return rows4;
	}
	
	public ContainerTag[] getRows14() {
		if (rows14 == null) {
			rows14 = new ContainerTag[ROWS_15];
			for (int i = 0; i < ROWS_15; ++i) {
				rows14[i] = tr();
			}
		}
		return rows14;
	}
	
	public ContainerTag[] getRows2() {
		if (rows2 == null) {
			rows2 = new ContainerTag[ROWS_2];
			for (int i = 0; i < ROWS_2; ++i) {
				rows2[i] = tr();
			}
		}
		return rows2;
	}
	
	public ContainerTag[] getRows3() {
		if (rows3 == null) {
			rows3 = new ContainerTag[ROWS_3];
			for (int i = 0; i < ROWS_3; ++i) {
				rows3[i] = tr();
			}
		}
		return rows3;
	}
	
	public ContainerTag[] getRows8() {
		if (rows8 == null) {
			rows8 = new ContainerTag[ROWS_8];
			for (int i = 0; i < ROWS_8; ++i) {
				rows8[i] = tr();
			}
		}
		return rows8;
	}
	
	public ContainerTag[] getRows7() {
		if (rows7 == null) {
			rows7 = new ContainerTag[ROWS_7];
			for (int i = 0; i < ROWS_7; ++i) {
				rows7[i] = tr();
			}
		}
		return rows7;
	}

	public ContainerTag[] getRows9() {
		if (rows9 == null) {
			rows9 = new ContainerTag[ROWS_9];
			for (int i = 0; i < ROWS_9; ++i) {
				rows9[i] = tr();
			}
		}
		return rows9;
	}
	
	public ContainerTag[] getRows11() {
		if (rows11 == null) {
			rows11 = new ContainerTag[ROWS_11];
			for (int i = 0; i < ROWS_11; ++i) {
				rows11[i] = tr();
			}
		}
		return rows11;
	}
	
	public ContainerTag[] getRows6() {
		if (rows6 == null) {
			rows6 = new ContainerTag[ROWS_6];
			for (int i = 0; i < ROWS_6; ++i) {
				rows6[i] = tr();
			}
		}
		return rows6;
	}
	
	public ContainerTag[] getRows13() {
		if (rows13 == null) {
			rows13 = new ContainerTag[ROWS_13];
			for (int i = 0; i < ROWS_13; ++i) {
				rows13[i] = tr();
			}
		}
		return rows13;
	}
	
	private void clearRows() {
		setRows(null);
	}
	
	private void clearRows7() {
		setRows7(null);
	}
	
	private void clearRows2() {
		setRows2(null);
	}
	
	private void clearRows3() {
		setRows3(null);
	}

	private void clearRows9() {
		setRows9(null);
	}
	
	private void clearRows11() {
		setRows11(null);
	} 
	
	private void clearRows6() {
		setRows6(null);
	} 
	
	private void clearRows13() {
		setRows13(null);
	} 
	
	private void clearRows4() {
		setRows4(null);
	} 
	
	private void clearRows14() {
		setRows14(null);
	}
	
	private void clearRows8() {
		setRows8(null);
	}
	
	public void setRows(ContainerTag[] rows) {
		this.rows5 = rows;
	}
	
	public void setRows2(ContainerTag[] rows) {
		this.rows2 = rows;
	}
	
	public void setRows3(ContainerTag[] rows) {
		this.rows3 = rows;
	}
	
	public void setRows7(ContainerTag[] rows) {
		this.rows7 = rows;
	}
	
	public void setRows9(ContainerTag[] rows) {
		this.rows9 = rows;
	}
	
	public void setRows11(ContainerTag[] rows) {
		this.rows11 = rows;
	}
	
	public void setRows6(ContainerTag[] rows) {
		this.rows6 = rows;
	}
	
	public void setRows13(ContainerTag[] rows) {
		this.rows13 = rows;
	}
	
	public void setRows4(ContainerTag[] rows) {
		this.rows4 = rows;
	}
	
	public void setRows14(ContainerTag[] rows) {
		this.rows14 = rows;
	}
	
	public void setRows8(ContainerTag[] rows) {
		this.rows8 = rows;
	}
	
	
	public List<DataSet> getDataSets14By3() {
		if (dataSets14By3 == null) {
			dataSets14By3 = new LinkedList<DataSet>();
		}
		return dataSets14By3;
	}
	
	public List<DataSet> getDataSets1By1() {
		if (dataSets1By1 == null) {
			dataSets1By1 = new LinkedList<DataSet>();
		}
		return dataSets1By1;
	}
	
	public List<DataSet> getDataSets14By14() {
		if (dataSets14By14 == null) {
			dataSets14By14 = new LinkedList<DataSet>();
		}
		return dataSets14By14;
	}
	
	public List<DataSet> getDataSets6By3() {
		if (dataSets6By3 == null) {
			dataSets6By3 = new LinkedList<DataSet>();
		}
		return dataSets6By3;
	}
	
	public List<DataSet> getDataSets6By2() {
		if (dataSets6By2 == null) {
			dataSets6By2 = new LinkedList<DataSet>();
		}
		return dataSets6By2;
	}
	
	
	public List<DataSet> getDataSets14By6(){
		if (dataSets14By6 == null) {
			dataSets14By6 = new LinkedList<DataSet>();
		}
		return dataSets14By6;
	}
	
	public List<DataSet> getDataSets4By5(){
		if (dataSets4By5 == null) {
			dataSets4By5 = new LinkedList<DataSet>();
		}
		return dataSets4By5;
	}
	
	public List<DataSet> getDataSets4By7(){
		if (dataSets4By7 == null) {
			dataSets4By7 = new LinkedList<DataSet>();
		}
		return dataSets4By7;
	}
	
	public List<DataSet> getDataSets3By1(){
		if (dataSets3By1 == null) {
			dataSets3By1 = new LinkedList<DataSet>();
		}
		return dataSets3By1;
	}
	
	public List<DataSet> getDataSets14By4(){
		if (dataSets14By4 == null) {
			dataSets14By4= new LinkedList<DataSet>();
		}
		return dataSets14By4;
	}
	
	public List<DataSet> getDataSets14By9(){
		if (dataSets14By9 == null) {
			dataSets14By9= new LinkedList<DataSet>();
		}
		return dataSets14By9;
	}
	
	public List<DataSet> getDataSets10By4(){
		if (dataSets10By4 == null) {
			dataSets10By4= new LinkedList<DataSet>();
		}
		return dataSets10By4;
	}
	
	public List<DataSet> getDataSets10By4II(){
		if (dataSets10By4II == null) {
			dataSets10By4II= new LinkedList<DataSet>();
		}
		return dataSets10By4II;
	}
	
	public List<DataSet> getDataSets10By11(){
		if (dataSets10By11 == null) {
			dataSets10By11= new LinkedList<DataSet>();
		}
		return dataSets10By11;
	}
	
	public void setDataSets(List<DataSet> dataSets) {
		this.dataSets14By3 = dataSets;
	}
	
	public void addDataSet(DataSet i) {
		getDataSets14By3().add(i);
	}
	
	public void addReportData(ReportData reportData) {
		if(StringUtils.equals(reportData.getDefinition().getDescription(), PnlsReportConstants.REPORT_DESCRIPTION_14BY3)) {				
			getDataSets14By3().addAll(reportData.getDataSets().values());
			}else if (StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_1BY1)){
				getDataSets1By1().addAll(reportData.getDataSets().values());
			}else if(StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_14BY14)) {
				getDataSets14By14().addAll(reportData.getDataSets().values());	
			}else if(StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_6BY3)) {
				getDataSets6By3().addAll(reportData.getDataSets().values());
			}else if(StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_14BY6)) {
				 getDataSets14By6().addAll(reportData.getDataSets().values());	
			}else if(StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_4BY5)) {
				getDataSets4By5().addAll(reportData.getDataSets().values());
			}else if(StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_3BY1)) {
				getDataSets3By1().addAll(reportData.getDataSets().values());
			}else if(StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_14BY4)) {
				getDataSets14By4().addAll(reportData.getDataSets().values());
			}else if(StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_6BY2)) {
				getDataSets6By2().addAll(reportData.getDataSets().values());
			}else if(StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_4BY7)) {
				getDataSets4By7().addAll(reportData.getDataSets().values());
			}else if(StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_14BY9)) {
				getDataSets14By9().addAll(reportData.getDataSets().values());
			}else if(StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_10BY4)) {
				getDataSets10By4().addAll(reportData.getDataSets().values());
			}else if(StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_10BY4_II)) {
				getDataSets10By4II().addAll(reportData.getDataSets().values());
			} if(StringUtils.equals(reportData.getDefinition().getDescription() ,PnlsReportConstants.REPORT_DESCRIPTION_10BY11)) {
				getDataSets10By11().addAll(reportData.getDataSets().values());
			}
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
	
	private static String translateString(String labelName) {
		return translate(labelName);
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