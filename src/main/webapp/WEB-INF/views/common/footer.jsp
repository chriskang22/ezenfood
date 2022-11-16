<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("UTF-8"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>footer</title>
	<link href="${contextPath}/css/foodroom.css" rel="stylesheet">
</head>
<body>
	<table class="footTable">
		<tr>
			<td>
			 	<a href="" class="footMenuBtn">회사소개 ｜</a>
			 	<a href="" class="footMenuBtn">이용약관 ｜</a>
			 	<a href="" class="footMenuBtn">개인정보처리방침 ｜</a>
			 	<a href="" class="footMenuBtn">소비자 분쟁해결 기준</a>
			</td>
		</tr>
		<tr>
			<td>
				<div id="footerField">
					고객행복센터 1004-8282 오전 10시 - 새벽 2시
					
					(주)컴퍼니
					주소 : 서울특별시 종로구 관철동 좋은 빌딩 304호 <br>
					팀리더 : 강지훈 
					팀원 : 최종인 조영석 정보성 장종문
				</div>
			</td>
		</tr>
	</table>
</body>
</html>