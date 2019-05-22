<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeJavascript("coreapps", "fragments/datamanagement/codeDiagnosisDialog.js")
    ui.includeJavascript("uicommons", "datatables/jquery.dataTables.min.js")

%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.escapeJs(ui.message("reportingui.reportsapp.home.title")) }", link: emr.pageLink("reportingui", "reportsapp/home") },
        { label: "${ ui.message("isanteplusreports.patientStatus") }", link: "${ ui.thisUrl() }" }
    ];
</script>

<script type="text/javascript">
	var modal = document.getElementById('divpopup');
	modal.style.display = "none";
	jq(document).ready(function() {
		jq('#submit').on('click', function()
		{
			modal.style.display = "block";
		});
		
	});
			
</script>


<div ng-app="patientStatusReport" ng-controller="patientStatusReportController">

	 <div class="running-reports">
     
          <form id="nonCodedForm" method="post">
    <fieldset id="run-report">
        <legend>
            ${ ui.message("reportingui.runReport.run.legend") }
        </legend>

        <% for (int i=0; i<reportManager.parameters.size(); i++) {
            def parameter = reportManager.parameters.get(i); %>
            <p id="parameter${i}Section">
                <% if (parameter.name == "startDate") { %>
                ${ ui.includeFragment("uicommons", "field/datetimepicker", [ "id": "startDateField", "label": parameter.label, "formFieldName": "startDate", "defaultDate": startDate, "useTime": false ]) }
                <% } else if (parameter.name == "endDate") { %>
                ${ ui.includeFragment("uicommons", "field/datetimepicker", [ "id": "endDateField", "label": parameter.label, "formFieldName": "endDate", "defaultDate": endDate, "useTime": false ]) }
                <% } %>
              <% } %>
              <br/>
            <table>
            	<tr>
			       	<td> ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "regularOnArt", "label": "", "formFieldName": "regularOnArt", "checked": "checked" ]) }  ${ ui.message("isanteplusreports.parameters.hiv_status.regular_on_art") }</td>  
              		<td> ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "missingAppointment", "label": "", "formFieldName": "missingAppointment", "checked": "checked" ]) } ${ ui.message("isanteplusreports.parameters.hiv_status.missing_appointment") } </td>
              		<td> ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "lostOfFollowUp", "label": "", "formFieldName": "lostOfFollowUp", "checked": "checked" ]) } ${ ui.message("isanteplusreports.parameters.hiv_status.lost_of_follow_up_on_art") } </td>
			    </tr> 
			    <tr>   
			        <td> ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "deathOnArt", "label": "", "formFieldName": "deathOnArt", "checked": "checked" ]) } ${ ui.message("isanteplusreports.parameters.hiv_status.death_on_art") } </td>
			       	<td> ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "stoppedOnArt", "label": "", "formFieldName": "stoppedOnArt", "checked": "checked" ]) } ${ ui.message("isanteplusreports.parameters.hiv_status.stopped_on_art") } </td>
        			<td> ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "tranferedOnArt", "label": "", "formFieldName": "tranferedOnArt" ]) } ${ ui.message("isanteplusreports.parameters.hiv_status.transfered") } </td>
        		</tr>
        		<tr>	
        			<td> ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "transitionRecent", "label": "", "formFieldName": "transitionRecent" ]) } ${ ui.message("isanteplusreports.parameters.hiv_status.transitionRecent") } </td>
			       	<td> ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "transitionActive", "label": "", "formFieldName": "transitionActive" ]) } ${ ui.message("isanteplusreports.parameters.hiv_status.transitionActive") } </td>
			        <td> ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "transitionLostFollowUp", "label": "", "formFieldName": "transitionLostFollowUp" ]) } ${ ui.message("isanteplusreports.parameters.hiv_status.transitionLostFollowUp") } </td>
			    </tr>
			    <tr>   
			        <td> ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "transitionDeath", "label": "", "formFieldName": "transitionDeath" ]) } ${ ui.message("isanteplusreports.parameters.hiv_status.transitionDeath") } </td>
			       	<td> ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "transitionTranfered", "label": "", "formFieldName": "transitionTranfered" ]) } ${ ui.message("isanteplusreports.parameters.hiv_status.transitionTranfered") } </td>
          			<td></td>
          		<tr>
          </table>		
        <br/>
        <p>
            <button id="submit" type="submit" class="disab">${ ui.message("reportingui.runButtonLabel") }</button>
        </p>
    </fieldset>

</form>
  <div id="divpopup" class="modal"> 
  		 <div class="modal-content">
    	 <span id="close" class="close">&times;</span>    
        <% if (startDate != null || endDate != null) { %>
    <h3>
        ${ ui.message("isanteplusreports.patientStatus", ui.format(date_debut), ui.format(date_fin)) }
    </h3>
    <table>
    	<tr>
    		<td> ${ ui.message("isanteplusreports.parameters.startdate") } </td>
    		<td> ${ ui.format(date_debut) } </td>
    	</tr>
		<tr>
    		<td> ${ ui.message("isanteplusreports.parameters.enddate") } </td>
    		<td> ${ ui.format(date_fin) } </td>
    	</tr>
    	<tr>
    		<td> ${ ui.message("isanteplusreports.patientStatus") } </td>
    		<% if (message != null) { %>
    			<td> ${ ui.format(message) } </td>
    		<% } %>
    	</tr>
	</table>
	
    <table id="non-coded-diagnoses" width="100%" border="1" cellspacing="0" cellpadding="2">
        <thead>
            <tr>
                <th>${ ui.message("isanteplusreports.code_st") }</th>
                <th>${ ui.message("isanteplusreports.code_national") }</th>
                <th>${ ui.message("isanteplusreports.familyName") }</th>
                <th>${ ui.message("isanteplusreports.name") }</th>
                <th>${ ui.message("isanteplusreports.sex") }</th>
                <th>${ ui.message("isanteplusreports.age") }</th>
                <th>${ ui.message("isanteplusreports.patientStatus") }</th>
                <th>${ ui.message("isanteplusreports.address") }</th>
                <th>${ ui.message("isanteplusreports.last_date") }</th>
                <th>${ ui.message("isanteplusreports.phone") }</th>
                <th>${ ui.message("isanteplusreports.contact") }</th>
                 <th>${ ui.message("isanteplusreports.reason") }</th>
            </tr>
        </thead>
        <tbody>
        
        <% if (nonCodedRows == null ) { %>
            <tr>
                <td colspan="3"></td>
            </tr>
        <% } else nonCodedRows.each { %>
            <tr>
			    		<td>
			    			<a target="_blank" href="${ "/" + contextPath + dashboardUrlWithoutQueryParams }?patientId=${ui.format(it.getColumnValue("patient_id"))}">
			    				${ui.format(it.getColumnValue("st_id"))}
			    		    </a>
			    		</td>
			    		<td>
			    			<a target="_blank" href="${ "/" + contextPath + dashboardUrlWithoutQueryParams }?patientId=${ui.format(it.getColumnValue("patient_id"))}">
			    				${ui.format(it.getColumnValue("national_id"))}
			    		    </a>
			    		</td>
			    		<td>
			    			${ui.format(it.getColumnValue("nom"))}
			    		</td>
			    		<td>
			    			${ui.format(it.getColumnValue("prenom"))}
			    		   
			    		</td>
			    		<td>
			    			${ui.format(it.getColumnValue("sexe"))}
			    		</td>
			    		<td>
			    			${ui.format(it.getColumnValue("age"))}
			    		</td>
			    		<td>
			    			${ui.format(it.getColumnValue("statut"))}
			    		</td>
			    		<td>
			    			${ui.format(it.getColumnValue("derniere_date"))}
			    		</td>
			    		<td>
			    			${ui.format(it.getColumnValue("adresse"))}
			    		</td>
			    		<td>
			    			${ui.format(it.getColumnValue("telephone"))}
			    		</td>
			    		<td>
			    			${ui.format(it.getColumnValue("contact"))}
			    		</td>
			    		<td>
			    			${ui.format(it.getColumnValue("raison"))}
			    		</td>
		    	</tr>
		    	<% i = i + 1 %>
        <% } %>
        </tbody>
    </table>

<% } %>
      <% if(i > 0) { %> 
     	Total : ${ui.format(i)}  
     <% } %>   
   </div>
   </div>
</div>
</div>