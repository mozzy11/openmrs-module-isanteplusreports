<%
    config.require("label")
    config.require("formFieldName")
%>

<label for="resultStatus-field">
        ${ ui.message("isanteplusreports.parameters.order_result") }
</label>
<select id="resultStatus-field" name="${ config.formFieldName }" >
    <option value="ALL">ALL</option>
    <option value="COMPLETE">COMPLETE</option>
    <option value="INCOMPLETE">INCOMPLETE</option>
</select>