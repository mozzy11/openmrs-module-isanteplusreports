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

<h2>${ ui.message("isanteplusreports.patByAge") }</h2>
 <table>
    <div class="info-body">
    	<% columnsvalues.each { %>
    		
    			<% columns.each { colName -> %>
    			<tr>
	    			<td>${ui.format(colName)}</td>
		    		<td>${ui.format(it.columnValues[colName])}</td>
	    		</tr>
	    		<% } %>
	    	
    	<% } %>
    </div>
</table>