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
	<c:if test="${movieSearchDTO != null && movieSearchDTO.searchResult != null}">
		<c:if test="${empty movieSearchDTO.searchResult}">
			<div class="message"><fmt:message key="screen.movie.info.emptyList"/></div>
		</c:if>
		<c:if test="${!empty movieSearchDTO.searchResult}">
			<div id="searchResult">
				<form:form  modelAttribute="movieSearchDTO" action="${pageContext.request.contextPath}/movie/add" method="POST">
					<label for="search"><fmt:message key="screen.movie.label.selectedMovie"/></label><br/>
					<form:errors cssClass="errors"/><br/>
					<form:errors path="selectedMovie" cssClass="errors"/>
					<table>
						<thead>
							<tr>
								<th><fmt:message key="screen.movie.info.title"/></th>
								<th><fmt:message key="screen.movie.info.release"/></th>
								<th><fmt:message key="screen.movie.info.imdb"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="searchResult" items="${movieSearchDTO.searchResult}">
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
									<form:options items="${movieSearchDTO.movieQualityList}" itemValue="code" itemLabel="description" />
								</form:select>
								<input type="submit" value="<fmt:message key="screen.movie.input.add"/>" />
								</td>
							</tr>
						</tbody>
					</table>
				</form:form>
			</div>
		</c:if>
	</c:if>
	<c:if test="${movieDTO != null}">
	<div id="movieList">
		<table>
			<tbody>
				<c:forEach var="movie" items="${movieDTO}">
				<tr>
					<td class="title">${movie.title}</td>
					<td class="release"><fmt:formatDate pattern="dd.MM.yyyy" value="${movie.release}" /></td>
					<td class="plot">${movie.plot}</td>
				</tr>
				</c:forEach>
			</tbody>	
		</table>
	</div>
	</c:if>	
</body>
</html>
