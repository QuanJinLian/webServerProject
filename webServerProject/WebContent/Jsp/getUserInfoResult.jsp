<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
 <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>getUserInfo Result</title>
</head>
<body>
	<c:choose>
		<c:when test="${requestScope.result == 1}">
			<p class="result">true</p>
				<ol>	
					<li class="id">${requestScope.userInfo.id }</li>
					<li class="nikName">${requestScope.userInfo.nikName }</li>
					<li class="photo">${requestScope.userInfo.photo }</li>
				</ol>
		</c:when>
		<c:when test="${requestScope.result == -1}">
			<p class="result">false</p>
		</c:when>
	
	</c:choose>

</body>
</html>