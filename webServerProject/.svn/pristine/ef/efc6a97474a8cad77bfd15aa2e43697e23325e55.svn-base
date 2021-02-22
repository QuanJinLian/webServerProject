<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
 <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>getList Result</title>
</head>
<body>
	<c:choose>
		<c:when test="${requestScope.result == 1}">
			<p class="result">친구있음</p>
			<c:forEach var="list" items="${requestScope.list}">
				<ol>	
					<li class="id">${list.id }</li>
					<li class="nikName">${list.nikName }</li>
					<li class="photo">${list.photo }</li>
				</ol>
			</c:forEach>
		</c:when>
		<c:when test="${requestScope.result == -1}">
			<p class="result">친구없음</p>
		</c:when>
	
	</c:choose>

</body>
</html>