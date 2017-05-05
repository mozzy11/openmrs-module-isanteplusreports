<%
    def angularLocale = context.locale.toString().toLowerCase();

    ui.decorateWith("appui", "standardEmrPage")

    ui.includeJavascript("uicommons", "moment.min.js")
    ui.includeJavascript("uicommons", "angular.min.js")
    ui.includeJavascript("uicommons", "i18n/angular-locale_" + angularLocale + ".js")
    ui.includeJavascript("isanteplusreports", "receivingArvReport.js")
    ui.includeJavascript("isanteplusreports", "ui-bootstrap-tpls-0.6.0.min.js")
    ui.includeCss("mirebalaisreports", "dailyReport.css")

    def CHECKINS_REPORT_DEFINITION_UUID = "ead22ae8-c3ab-4c27-ab8d-e63ec8658e50"
    def hideCounts = ((hideCounts != null) && (reportDefinition.uuid == CHECKINS_REPORT_DEFINITION_UUID)) ? hideCounts : false
%>

<%= ui.includeFragment("appui", "messages", [ codes: [
        reportDefinition.name,
        reportDefinition.description,
        "mirebalaisreports.dailyRegistrations.overall",
        "mirebalaisreports.dailyClinicalEncounters.clinicalCheckIns",
        "mirebalaisreports.dailyClinicalEncounters.consultWithoutVitals",
        "mirebalaisreports.dailyCheckInEncounters.overall",
        "mirebalaisreports.dailyCheckInEncounters.dataQuality.multipleCheckins",
        "mirebalaisreports.dailyCheckInEncounters.CLINICAL_new",
        "mirebalaisreports.dailyCheckInEncounters.CLINICAL_return",
        context.locationService.allLocations.collect { "ui.i18n.Location.name." + it.uuid },
        context.encounterService.allEncounterTypes.collect { "ui.i18n.EncounterType.name." + it.uuid }
    ].flatten()
]) %>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.escapeJs(ui.message("reportingui.reportsapp.home.title")) }", link: emr.pageLink("reportingui", "reportsapp/home") },
        { label: "${ ui.message(ui.format(reportDefinition)) }", link: "${ ui.escapeJs(ui.thisUrl()) }" }
    ];

    window.reportDefinition = {
        uuid: '${ reportDefinition.uuid}'
    };
</script>
<div>
  Work on progress
</div>
