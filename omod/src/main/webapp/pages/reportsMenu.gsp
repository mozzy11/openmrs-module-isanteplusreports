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

<b>Liste des rapports</b>
<table>
	<tr><th>Gestion de programme</th><th>Qualité des soins</th><th>Soins de santé primaire</th></tr>
	<tr><td><a href="nombrePatients.page">Nombre de patients</a></td><td></td><td></td><tr>
	<tr><td><a href="rendezvousSeptJours.page">Rendez-vous programmé dans les 7 jours prochains</a></td><td></td><td></td><tr>
	<tr><td><a href="rendezVousQuatorzeJours.page">Rendez-vous programmé dans les 14 jours prochains</a></td><td></td><td></td><tr>
	<tr><td><a href="reportsList.page">Liste des rapports sur reporting module</a></td><td></td><td></td><tr>
</table>




