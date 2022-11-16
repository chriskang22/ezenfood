<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib	prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("UTF-8"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="result"      value="${param.result}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>메인 페이지</title>
	
	<link href="${contextPath}/css/foodroom.css" rel="stylesheet">

	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<c:choose>
		<c:when test="${result =='regiSuccess'}">
			<script>
			window.onload=function() {
				alert("이미 등록을 완료하셨습니다.\n승인을 기다려 주세요.");
			}
			</script>
		</c:when>	
	</c:choose>
</head>
<body>
	메인페이지 입니다.
	
	<a href="${ContextPath}/signUp.do">회원가입페이지</a>
</body>
</html>