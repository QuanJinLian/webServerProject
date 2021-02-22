<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
 <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결과</title>
</head>
<body>
	<c:set var="user" value="${requestScope.userInfo}" />
	<ol class="user">
		<li class="result">${requestScope.result}</li>
		<li class="id">${user.id }</li>
		<li class="pwd">${user.pwd }</li>
		<li class="nikName">${user.nikName }</li>
		<li class="photo">${user.photo }</li>
	</ol>

</body>
</html>