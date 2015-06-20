<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="/static/js/task.form.js"></script>
    <script>
	$(function() {
		var $select = $(".status_selector");
		var current_status = $(".status_value").val();
		var index = current_status / 10;
		for (i = index; i <= 9; i++) {
			var value = i * 10;
				$select.append($('<option></option>').val(value).html(value));
		}
		$(".status_selector").val(current_status);
		 var s = $(".status_value").detach();
	});
</script>
</head>
<body>
    <h1><spring:message code="label.update.task.title"/></h1>
    <div class="well page-content">
        <form:form action="/task/update" commandName="task" method="POST" enctype="utf8">
            <form:hidden path="id"/>
            <div id="control-group-title" class="control-group">
                <label for="task-title"><spring:message code="label.task.title"/>:</label>

                <div class="controls">
                    <form:input id="task-title" path="title"/>
                    <form:errors id="error-title" path="title" cssClass="help-inline"/>
                </div>
            </div>
            <div id="control-group-description" class="control-group">
                <label for="task-description"><spring:message code="label.task.description"/>:</label>
				
                <div class="controls">
                    <form:textarea id="task-description" path="description"/>
                    <form:errors id="error-description" path="description" cssClass="help-inline"/>
                </div>
            </div>
            <div id="control-group-status" class="control-group">
                <label for="task-status"><spring:message code="label.task.status"/>:</label>
                <form:input class="status_value" style="display:none;" id="task-status" path="status"/>
                <div class="controls" >                 
<%--                     <form:textarea id="task-status" path="status"/> --%>
					<form:select class="status_selector" id="task-status" path="status" />
                    <form:errors id="error-status" path="status" cssClass="help-inline"/>
                </div>
            </div>
            <div class="action-buttons">
                <a href="/task/${task.id}" class="btn"><spring:message code="label.cancel"/></a>
                <button id="update-task-button" type="submit" class="btn btn-primary"><spring:message
                        code="label.update.task.button"/></button>
            </div>
        </form:form>
    </div>
</body>
</html>