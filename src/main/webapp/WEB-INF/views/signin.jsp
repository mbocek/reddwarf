<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<html>
	<title>Sign in</title>
<body>
	<form method="POST" action="${pageContext.request.contextPath}/j_spring_security_check">
		<c:if test="${not empty param.login_error}">
			Can not login user!
		</c:if>
		<table>
			<caption>Login</caption>
			<tr>
				<td><label for="j_username">Login</label></td>
				<td><input id="j_username" name="j_username" type="text" <c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY) %>'</c:if>/></td>
			</tr>
			<tr>
				<td><label for="j_password">Password:</label></td>
				<td><input id="j_password" name="j_password" value="" type="password" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" id="login" value="Login" name="login" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
