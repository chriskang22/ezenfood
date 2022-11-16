<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib	prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("UTF-8"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>Home</title>
</head>
<body>
<h1>Hello world!</h1>
<table>
<thead>
<tr>
<th>아이디</th>
<th>비밀번호</th>
<th>이름</th>
</tr>
</thead>
<tbody>
<c:forEach items="${userList}" var="userList">
<tr>
<td>${userList.fr_id}</td>
<td>${userList.fr_pwd}</td>
<td>${userList.fr_name}</td>
</tr>
</c:forEach>
</tbody>
</table>
</body>
</html>
