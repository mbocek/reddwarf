<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div id="menu">
	<span id="menuItem"><a href="<c:url value="/" />"><fmt:message key="header.menu.home"/></a></span>
	<span id="menuItem"><a href="<c:url value="/movie/list" />"><fmt:message key="header.menu.movie"/></a></span>
	<sec:authorize ifNotGranted="ROLE_USER,ROLE_ADMINISTRATOR">
		<span id="menuItem"><a href="<c:url value="/users/new" />"><fmt:message key="header.menu.register"/></a></span>
		<span id="menuItem"><a href="<c:url value="/users/signin" />"><fmt:message key="header.menu.signin"/></a></span>
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_USER,ROLE_ADMINISTRATOR">
		<span id="menuItem"><a href="<c:url value="/j_spring_security_logout"/>"><fmt:message key="header.menu.signout"/></a></span>
	</sec:authorize>
</div>
