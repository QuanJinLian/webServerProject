<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
 <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert Result</title>
</head>
<body>
	<ol>
	 <li class="result">${requestScope.result}</li>
	</ol>
</body>
</html>