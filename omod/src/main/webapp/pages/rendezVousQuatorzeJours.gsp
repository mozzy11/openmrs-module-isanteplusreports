<% ui.includeJavascript("uicommons", "jquery-1.8.3.min.js") %>
<% ui.includeJavascript("uicommons", "angular.js") %>

<%
    ui.decorateWith("appui", "standardEmrPage")
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("isanteplusreports.title") }" }
    ];
</script>

<h2>${ ui.message("isanteplusreports.quatorzeJoursLibelle") }</h2>
 <table>
    <tr><th>${ ui.message("isanteplusreports.identifier") }</th>
    	<th>${ ui.message("isanteplusreports.name") }</th>
    	<th>${ ui.message("isanteplusreports.familyName") }</th>
    	<th>${ ui.message("isanteplusreports.gender") }</th>
    	<th>${ ui.message("isanteplusreports.birthdate") }</th>
    	<th>${ ui.message("isanteplusreports.phone") }</th>
    	<th>${ ui.message("isanteplusreports.form") }</th>
    	<th>${ ui.message("isanteplusreports.nextVisit") }</th>
    </tr>
    <div class="info-body">
    	<% columnsvalues.each { %>
    		<tr>
    			<% columns.each { colName -> %>
    			
		    		<td>${ui.format(it.columnValues[colName])}</td>
	    		
	    		<% } %>
	    	</tr>
    	<% } %>
    </div>
</table>