<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<title><fmt:message key="screen.movie.title"/></title>
<body>
	<div id="search">
		<form:form action="${pageContext.request.contextPath}/movie/find" method="POST">
			<label for="search"><fmt:message key="screen.movie.label.searchName"/></label>
			<input type="text" name="searchName" />
			<input type="submit" value="<fmt:message key="screen.movie.input.search"/>" />
		</form:form>
	</div>
	<c:if test="${movieDTO != null && movieDTO.searchResult != null}">
	<div id="searchResult">
		<br/>
		<label for="search"><fmt:message key="screen.movie.label.selectedMovie"/></label><br/>
		<form:errors path="movieDTO.selectedMovie" cssClass="errors"/>
		<table>
			<thead>
				<tr>
					<th><fmt:message key="screen.movie.info.title"/></th>
					<th><fmt:message key="screen.movie.info.release"/></th>
					<th><fmt:message key="screen.movie.info.imdb"/></th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty movieDTO.searchResult}">
					<div class="message"><fmt:message key="screen.movie.info.emptyList"/></div>
				</c:if>
				<c:if test="${!empty movieDTO.searchResult}">
				<form:form  modelAttribute="movieDTO" action="${pageContext.request.contextPath}/movie/add" method="POST">
					<c:forEach var="searchResult" items="${movieDTO.searchResult}">
					<tr>
						<td><form:radiobutton path="selectedMovie" value="${searchResult.id}"/><span class="title" title="${searchResult.plot}">${searchResult.title}</span></td>
						<td><fmt:formatDate pattern="dd.MM.yyyy" value="${searchResult.release}" /></td>
						<td>
							<c:if test="${searchResult.imdbId != null}">
							<a href="<fmt:message key="imdb.url"/>/${searchResult.imdbId}">${searchResult.imdbId}</a>
							</c:if>
						</td>
					</tr>
					</c:forEach>
					<tr>
						<td colspan="3">
						<label for="movieQuality"><fmt:message key="screen.movie.label.movieQuality"/></label><br/>
						<form:errors path="movieQuality" cssClass="errors"/>
						<form:select path="movieQuality">
							<option value=""><fmt:message key="screen.movie.input.selectQuality"/></option>
							<form:options items="${movieDTO.movieQualityList}" itemValue="code" itemLabel="description" />
						</form:select>
						<input type="submit" value="<fmt:message key="screen.movie.input.add"/>" />
						</td>
					</tr>
				</form:form>
				</c:if>
			</tbody>
		</table>
	</div>
	</c:if>
</body>
</html>
