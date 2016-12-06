<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<h2>Add resume</h2>

	<c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<strong>${msg}</strong>
		</div>
	</c:if>

	<sf:form name="newResume" method="POST" modelAttribute="resume"
		enctype="multipart/form-data" action="/rubrics/newResume">
		<fieldset>
			<sf:input type="hidden" path="rubricId" />
			<table cellspacing="0">
				<tr>
					<td><sf:errors path="*" cssClass="formError" /></td>
				</tr>
				<tr>
					<td><sf:errors path="e1Id" cssClass="formError" /></td>
				</tr>
				<tr>
					<th><nobr>
							<sf:label path="e1Id">
								<spring:message code="new_resume_e1_id" />
							</sf:label>
						</nobr></th>
				</tr>
				<tr>
					<td><sf:input path="e1Id" size="6" /> <br /> <sf:errors
							path="e1Id" cssClass="error" /></td>
				</tr>
				<tr>
					<td><sf:errors path="name" cssClass="formError" /></td>
				</tr>
				<tr>
					<th><nobr>
							<sf:label path="name">
								<spring:message code="new_resume_name" />
							</sf:label>
						</nobr></th>
				</tr>
				<tr>
					<td><sf:input path="name" size="20" /> <br /> <sf:errors
							path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td><sf:errors path="header" cssClass="formError" /></td>
				</tr>
				<tr>
					<th><nobr>
							<sf:label path="header">
								<spring:message code="new_resume_header" />
							</sf:label>
						</nobr></th>
				</tr>
				<tr>
					<td><sf:input path="header" size="20" /> <br /> <sf:errors
							path="header" cssClass="error" /></td>
				</tr>
				<tr>
					<td><sf:errors path="wantedSalaryRub" cssClass="formError" /></td>
				</tr>
				<tr>
					<th><nobr>
							<sf:label path="wantedSalaryRub">
								<spring:message code="new_resume_wanted_salary_rub" />
							</sf:label>
						</nobr></th>
				</tr>
				<tr>
					<td><sf:input path="wantedSalaryRub" size="8" /> <br /> <sf:errors
							path="wantedSalaryRub" cssClass="error" /></td>
				</tr>
				<tr>
					<th></th>
					<td><input name="commit" type="submit" value="Add" /></td>
				</tr>
			</table>
		</fieldset>
	</sf:form>
</div>
