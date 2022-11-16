<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="page_num" value="${page_num}"/>
<%	request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>승인된 업체관리</title>
		<link href="${contextPath}/css/ezen.css" rel="stylesheet">    
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
<div id="manageFoodRoomField">
	<div class="manageFoodRoomSpan">
		<span>업체관리</span>
	</div>
	<div class="manageFoodRoomTableField">
	    <table class="manageFoodRoomTable">           		
			<tr>
				<th><b>아이디</b></th>
				<th><b>업체명</b></th>
				<th><b>업체주소</b></th>
				<th><b>업체연락처</b></th>
				<th><b>업체주메뉴</b></th>
				<th><b>룸정보</b></th>
			</tr>
			<c:forEach var="approveOk" items="${approveOk}">
				<tr>
					<td>${approveOk.fr_id}</td>
					<td>${approveOk.fr_store_name}</td>
					<td>${approveOk.fr_address.substring(7)}</td>
					<td>${approveOk.fr_store_p_number}</td>
					<td>${approveOk.fr_menu}</td>
					<td><a href="${contextPath}/lookRoomList.do?fr_no=${approveOk.fr_no}" 
						   onclick="return confirm('[${approveOk.fr_store_name}] 룸 정보를 보겠습니까?');" class="">룸정보</a></td>
				</tr>
			</c:forEach>
		</table>
		<div id="page">
			<ul>
				<c:if test="${startPage > 1}">
			    <li style="float:left; margin: 0px 5px 0px 5px;"><a href="${contextPath}/manageFoodRoom.do?page=${startPage-1}&selectChk=${selectChk}&selectText=${selectText}">◀</a></li>
			    </c:if>
			    <c:if test="${endPage!=null}">
			    <c:forEach begin="${startPage}" end="${endPage}" varStatus="i">
			    	<li style="float:left; margin: 0px 5px 0px 5px;"><a href="${contextPath}/manageFoodRoom.do?page=${i.index}&selectChk=${selectChk}&selectText=${selectText}">${i.index}</a></li>
			    </c:forEach>
			    </c:if>
			    <c:if test="${endPage != fullPage}">
			    <li style="float:left; margin: 0px 5px 0px 5px;"><a href="${contextPath}/manageFoodRoom.do?page=${endPage+1}&selectChk=${selectChk}&selectText=${selectText}">▶</a></li>
			    </c:if>
			</ul>
		</div>
	</div>
	<div class="manageFoodRoomFormField">
		<form id="manageFoodRoomForm" method="GET" action="${contextPath}/manageFoodRoom.do">
			<select name="selectChk">
				<option value="fr_store_name" selected>업체명</option>
				<option value="fr_address">업체주소</option>
			</select>
			<input class="selectText" type="text" name="selectText" placeholder="검색어 입력..."/>
			<input type="submit" class="findStoreListBtn" value="검색">			
		</form>
	</div>
</div>
</body>
</html>