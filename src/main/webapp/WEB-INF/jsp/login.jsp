<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	$(function() {
		document.loginForm.username.focus();
	});
</script>

<c:url value="/login" var="loginUrl"/>
<form action="${loginUrl}" method="post" name="loginForm">
	<c:if test="${param.error != null}">
		<p>
			Invalid username and password.
		</p>
	</c:if>
	<c:if test="${param.logout != null}">
		<p>
			You have been logged out.
		</p>
	</c:if>
	<p>
		<label for="username">Username</label><br/>
		<input type="text" id="username" name="username"/>
	</p>
	<p>
		<label for="password">Password</label><br/>
		<input type="password" id="password" name="password"/>
	</p>
	<input type="hidden"
		name="${_csrf.parameterName}"
		value="${_csrf.token}"/>
	<button type="submit" class="btn">Log in</button>
</form>