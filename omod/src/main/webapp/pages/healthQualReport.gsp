<%
    ui.decorateWith("appui", "standardEmrPage")
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.escapeJs(ui.message("reportingui.reportsapp.home.title")) }", link: emr.pageLink("reportingui", "reportsapp/home") },
        { label: "${ ui.message("isanteplusreports.healthqual.label") }", link: "${ ui.thisUrl() }" }
    ];
</script>

<style>
.clearfix::after {
    content: "";
    clear: both;
    display: table;
}

.indicatorsHeader {
    text-align: center; 
    background-color: #fefad3;
}

#indicators thead th {
    background-color: #fdf59a;
}

#indicators th, #indicators td {
    white-space: nowrap;
}

</style>

<div>

    <h1>${ ui.message("isanteplusreports.healthqual.label") }</h1>
    <div class="running-reports" class="clearfix">

        <form id="nonCodedForm" method="post">
            <fieldset id="run-report" style="float:right; min-width: 450px; margin-left: 10px;">
                <legend>
                    ${ ui.message("reportingui.runReport.run.legend") }
                </legend>

                    <% for (int i=0; i < reportManager.parameters.size(); i++) {

                        def parameter = reportManager.parameters.get(i); %>
                        <p id="parameter${i}Section">
                            <% if (parameter.name == "startDate") { %>
                                ${ ui.includeFragment("uicommons", "field/datetimepicker", [ "id": "startDateField", "label": parameter.label, "formFieldName": "startDate", "defaultDate": startDate, "useTime": false ]) }
                            <% } else if (parameter.name == "endDate") { %>
                                ${ ui.includeFragment("uicommons", "field/datetimepicker", [ "id": "endDateField", "label": parameter.label, "formFieldName": "endDate", "defaultDate": endDate, "useTime": false ]) }
                            <% } %>
                        <p>

                    <% } %>

                    <button id="submit" type="submit">${ ui.message("reportingui.runButtonLabel") }</button>
            </fieldset>

            <table id="indicators" style="display:block; width: 100%; padding-top: 11px;">
                <thead>
                    <tr>
                        <th style="width:100%">Indicator</th>
                        <th>Options</th>
                        <th>Selection</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th class="indicatorsHeader" colspan="3"><i>Adult Indicators</i></th>
                    </tr>
                    <tr>
                        <td>Retention of patients on ARV treatment</td>
                        <td style="text-align: center;">
                            <div>
                                <label style="display:inline-block;">Peroid:</label> 
                                <select style="display:inline-block; margin: 0; min-width:120px; ">
                                    <option value="">6 months</option>
                                    <option value="">12 months</option>
                                    <option value="">24 months</option>
                                    <option value="">48 months</option>
                                    <option value="">60 months</option>
                                </select>
                            <div>
                        </td>
                        <td style="text-align: center;">
                            <div style="display:inline-block">
                                <input type="checkbox"/>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>

        <% if (startDate != null || endDate != null) { %>
            <h3> 
                ${ ui.message("isanteplusreports.healthqual.label") } - response
            </h3>
            <table id="non-coded-diagnoses" width="100%" border="1" cellspacing="0" cellpadding="2">
            </table>
        <% } %>
   </div>
   
</div>
