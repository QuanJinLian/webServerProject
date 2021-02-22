<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
 <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>updateUserInfoResult Result</title>
</head>
<body>
	
	<c:choose>
		<c:when test="${requestScope.result >= 0}">
			<p class="result">true</p>
				
		</c:when>
		<c:when test="${requestScope.result == -1}">
			<p class="result">false</p>
		</c:when>
	
	</c:choose>

</body>
</html>