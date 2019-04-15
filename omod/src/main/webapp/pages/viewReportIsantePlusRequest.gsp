<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeJavascript("uicommons", "angular.min.js")
%>
<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.escapeJs(ui.message("reportingui.reportsapp.home.title")) }", link: emr.pageLink("reportingui", "reportsapp/home") },
        { label: "${ ui.message("reportingui.reportHistory.title") }", link: "${ ui.escapeJs(ui.thisUrl()) }" }
    ];

    var highlight = <%= param.request ? """ "${ ui.escapeJs(param.request[0])}" """ : "null" %>
    
    
</script>

<div class="result">
  	<% if(columnsvalues != null) { %>
  	   <% if(reportName != null) { %>
  	   		${ui.format(reportName)}
  	   <% } %>
  		<br/>
  		<table>
  			
	    	<% parameter.each { %>
	    			<tr>
			    		<td><b> ${ui.format(it.getName())} </b></td>
			    		<td> ${ui.format(dataset.getContext().getParameterValue(it.getName()))} </td>
			    		
		    		</tr>
		    <% } %>
		    
		</table>
  		<table>
  			<tr>
	    			<% columns.each { %>
	    			
			    		<th>${ui.format(it)}</th>
		    		
		    		<% } %>
		    </tr>
	    	<% columnsvalues.each { %>
	    		<% i = i + 1 %>
	    		<tr>
	    			<% columns.each { colName -> %>
	    			
			    		<td>${ui.format(it.columnValues[colName])}</td>
		    		
		    		<% } %>
		    	</tr>
	    	<% } %>
    	</table>
     <% } %> 
     <br/>
     <% if(i > 15) { %>
     Total : ${ui.format(i)}
     <% } %>
    </div>