<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="js/react.js"></script>
<script type="text/javascript" src="js/react-dom.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-standalone/6.10.3/babel.min.js"></script>

<script type="text/babel" src="js/page_jsx/newResume.js"></script>

<div>
	<h2>Add resume</h2>

	<%-- <c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<strong>${msg}</strong>
		</div>
	</c:if>
 	--%>
 	<div id="newResume-alert-single-msg" class="alert alert-${css} alert-dismissible" role="alert">
	</div>

	<sf:form name="newResume" method="POST" modelAttribute="resume"
		enctype="multipart/form-data" action="/rubrics/newResume">
		<fieldset>
			<sf:input type="hidden" path="rubricId" />
			<table cellspacing="0">
				<tr>
					<td><sf:errors path="*" cssClass="formError" /><div id="newResume-error-rubricId"></div></td>
				</tr>
				<tr>
					<th><div id="newResume-rubricId">
							Rubric id: 
						</nobr></th>
				</tr>
				<tr>
					<td><sf:errors path="e1Id" cssClass="formError" /><div id="newResume-error-e1Id"></div></td>
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
					<td><sf:errors path="name" cssClass="formError" /><div id="newResume-error-name"></div></td>
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
					<td><sf:errors path="header" cssClass="formError" /><div id="newResume-error-header"></div></td>
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
					<td><sf:errors path="wantedSalaryRub" cssClass="formError" /><div id="newResume-error-wantedSalaryRub"></div></td>
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
					<!-- <td><input name="commit" type="submit" value="Add" /></td> -->
					<td><input name="commit" type="button" onclick="submit_form()" value="Add" /></td>
				</tr>
			</table>
		</fieldset>
	</sf:form>
</div>
