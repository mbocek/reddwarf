<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="menu">
	<span id="menuItem"><a href="<c:url value="/" />">Home</a></span>
	<sec:authorize ifNotGranted="ROLE_USER,ROLE_ADMINISTRATOR">
		<span id="menuItem"><a href="<c:url value="/users/new" />">Register</a></span>
		<span id="menuItem"><a href="<c:url value="/users/signin" />">Sign in</a></span>
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_USER,ROLE_ADMINISTRATOR">
		<span id="menuItem"><a href="<c:url value="/j_spring_security_logout"/>">Sign out</a></span>
	</sec:authorize>
</div>
