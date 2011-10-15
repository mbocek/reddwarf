<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<html>
	<title>Sign in</title>
<body>
	<form:form modelAttribute="users" action="${pageContext.request.contextPath}/users/new" method="POST">
		<table>
			<caption>Register</caption>
			<tr>
				<td><label for="userName">User Name:</label><br/><form:errors path="userName" cssClass="errors"/></td>
				<td><input id="userName" name="userName" type="text" value=""/></td>
			</tr>
			<tr>
				<td><label for="password">Password:</label><br/><form:errors path="password" cssClass="errors"/></td>
				<td><input id="password" name="password" value="" type="password" /></td>
			</tr>
			<tr>
				<td><label for="confirmationPassword">Password confirmation:</label></td>
				<td><input id="confirmationPassword" name="confirmationPassword" value="" type="password" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" id="register" value="Register" name="register" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>
