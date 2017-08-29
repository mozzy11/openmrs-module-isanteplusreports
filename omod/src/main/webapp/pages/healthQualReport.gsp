<%
    ui.decorateWith("appui", "standardEmrPage")
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.escapeJs(ui.message("reportingui.reportsapp.home.title")) }", link: emr.pageLink("reportingui", "reportsapp/home") },
        { label: "${ ui.message("isanteplusreports.healthqual.label") }", link: '${ ui.thisUrl() }' }
    ];
    var jq = jQuery;

    jq(document).ready(function() {
        jq('#indicatorsForm').submit(function(event) {            
            runReport();
        });
    });

    function runReport() {
        var form = jq('#indicatorsForm');
        var indicators = parseIndicators(form);
        
        jq("#codedIndicators").remove();    // if it exists
        form.append("<input id='codedIndicators' type='hidden' name='indicatorList' value='" + JSON.stringify(indicators) + "'/>");
    }

    function parseIndicators(form) {
        var parsedIndicators = [];
        var indicators = form.find(".indicator");

        indicators.each(function() {
            var indicator = jq(this);
            if (indicator.find("[name=selection]").prop('checked') === true) {
                var map = new Object();
                indicator.find('[name=options]').each(function() {
                    map[jq(this).attr('id')] = indicator.find('[name=options] :selected').val();
                });
                parsedIndicators.push(
                    createIndicator(
                        indicator.attr('id'),
                        map
                    )
                );
            }
        });
        return parsedIndicators;
    }

    function createIndicator(uuidValue, optionValue) {
        return {
            uuid: uuidValue,
            option: optionValue
        };
    }
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
    <div class="running-reports">

        <form id="indicatorsForm" class="clearfix" method="post">
            <fieldset id="run-report" style="float:right; min-width: 450px; margin-left: 10px;">
                <legend>
                    ${ ui.message("reportingui.runReport.run.legend") }
                </legend>
                <p id="parameterSection">
                    ${ ui.includeFragment("uicommons", "field/datetimepicker", [ "id": "startDateField", "label": "From Date", "formFieldName": "startDate", "defaultDate": startDate, "useTime": false ]) }
                    ${ ui.includeFragment("uicommons", "field/datetimepicker", [ "id": "endDateField", "label": "To Date", "formFieldName": "endDate", "defaultDate": endDate, "useTime": false ]) }
                <p>
                <button id="submit" type="submit">${ ui.message("reportingui.runButtonLabel") }</button>
            </fieldset>

            <table id="indicators" style="display:block; width: 100%; padding-top: 11px;">
                <thead>
                    <tr>
                        <th style="width:100%">${ ui.message("isanteplusreports.healthqual.indicator.label") }</th>
                        <th>${ ui.message("isanteplusreports.healthqual.options.label") }</th>
                        <th>${ ui.message("isanteplusreports.healthqual.selection.label") }</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th class="indicatorsHeader" colspan="3"><i>Adult Indicators</i></th>
                    </tr>

                    <%  manager.getAdultIndicators().each { adultIndicator -> %>
                        <tr id="${ adultIndicator.getUuid() }" class="indicator">
                            <td>
                                ${ adultIndicator.getName() }
                                ${ adultIndicator.getName() }
                            </td>
                            <td style="text-align: center;">
                                <% if (adultIndicator.getOption().isSet()) { %>
                                    <label style="display:inline-block;">
                                        ${adultIndicator.getOption().getLabel()}:
                                    </label> 
                                    <select id="${adultIndicator.getOption().getId()}" name="options" style="display:inline-block; margin: 0; min-width:120px; ">
                                        <%  adultIndicator.getOption().getValues().each { optionValue -> %>
                                            <option value="${optionValue}">
                                                ${optionValue}
                                            </option>
                                        <% } %>
                                    </select>
                                <% } %>
                            </td>
                            <td style="text-align: center;">
                                <div style="display:inline-block">
                                    <input name="selection" type="checkbox"/>
                                </div>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </form>

        <% if (divWithResult != null) { %>
            <h3> 
                ${ ui.message("isanteplusreports.healthqual.label") } - data
            </h3>
            <%= divWithResult %>
        <% } %>
   </div>
   
</div>
