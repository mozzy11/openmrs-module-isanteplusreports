<% ui.includeJavascript("uicommons", "jquery-1.8.3.min.js") %>
<% ui.includeJavascript("uicommons", "angular.js") %>

<%
    ui.decorateWith("appui", "standardEmrPage")
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ui.message("isanteplusreports.title")}", link: "/" + OPENMRS_CONTEXT_PATH + "/isanteplusreports/reportsMenu.page"}
    ];
</script>
<h2>${ ui.message("isanteplusreports.numberPat") }</h2>
 <table>
    <tr><th>${ ui.message("isanteplusreports.numberPatSite") }</th><th>${ ui.message("isanteplusreports.numberPatResult") }</th></tr>
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