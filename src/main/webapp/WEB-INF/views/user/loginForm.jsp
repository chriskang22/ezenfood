<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@	taglib	prefix="c"		uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="result"		 value="${param.result}"/>
<%	request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그인 페이지</title>
	
	<link href="${contextPath}/css/foodroom.css" rel="stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	
	<c:choose>
		<c:when test="${result =='loginFailed'}">
			<script>
			window.onload=function() {
				alert("로그인에 실패 하였습니다.\n아이디 비밀번호를 확인 하십시오.");
			}
			</script>
		</c:when>	
	</c:choose>
</head>



<body>
	<div id="loginField">
		<div><a href="${contextPath}/index.do" class="loginFieldLogo">FOODROOM</a></div>
		<form method="post" name="loginFieldForm" id="loginForm" action="${contextPath}/login.do">
			<table class="loginTable">
				<tr>
					<th colspan=2><span>로그인</span></th>
				</tr>
				<tr>
					<td colspan=2><input type="text" name="fr_id" class="loginInfo" placeholder="ID 입력" /></td>
				</tr>
				<tr>
					<td colspan=2><input type="password" name="fr_pwd" class="loginInfo" placeholder="PWD 입력" /></td>
				</tr>
				<tr>
					<td colspan=2><button type="submit" class="btn-gradient">로그인</button></td>
				</tr>
				<tr>
					<td>
						<a href="${contextPath}/find.do" onclick="window.open(this.href,'_blank','top=200,left=200,width=500,height=300, scrollbars=no, resizable=no');return false;">아이디/비밀번호 찾기</a>
						<a href="${contextPath}/signUp.do" class="">회원가입</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>