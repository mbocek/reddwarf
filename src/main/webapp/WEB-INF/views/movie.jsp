<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<title><fmt:message key="screen.movie.title"/></title>
<body>
	<div id="search">
		<form:form modelAttribute="movies" action="${pageContext.request.contextPath}/movie/find" method="POST">
			<form:label path="searchName"><fmt:message key="screen.movie.label.searchName"/></form:label>
			<form:input type="text" path="searchName" />
			<input type="submit" value="<fmt:message key="screen.movie.input.search"/>" />
		</form:form>
	</div>
	<c:if test="${movies != null && movies.searchResult != null}">
	<div id="searchResult">
		<table>
			<thead>
				<tr>
					<th><fmt:message key="screen.movie.info.title"/></th>
					<th><fmt:message key="screen.movie.info.release"/></th>
					<th><fmt:message key="screen.movie.info.imdb"/></th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty movies.searchResult}">
					<div class="message"><fmt:message key="screen.movie.info.emptyList"/></div>
				</c:if>
				<c:forEach var="movie" items="${movies.searchResult}">
					<tr>
						<td><span class="title" title="${movie.plot}">${movie.title}</span></td>
						<td><fmt:formatDate pattern="dd.MM.yyyy" value="${movie.release}" /></td>
						<td>
							<c:if test="${movie.imdbId != null}">
							<a href="<fmt:message key="imdb.url"/>/${movie.imdbId}">${movie.imdbId}</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</c:if>
</body>
</html>
