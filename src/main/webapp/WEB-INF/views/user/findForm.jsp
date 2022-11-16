<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@	taglib	prefix="c"		uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%	request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>아이디/비밀번호 찾기</title>
	<link href="${contextPath}/css/foodroom.css" rel="stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>

<style>
body {
	width:	500px;
	height:	300px;
}
</style>

<script>
function findViewDisplay(){
    if($('input:radio[id=findId]').is(':checked')){
        $('#pwdFindTable').hide();
        $('#idFindTable').show();
    }else{
        $('#pwdFindTable').show();
        $('#idFindTable').hide();
    }
}

$(document).ready(function() {
	
	// 아이디 찾기
	$("#findIdBtn").on("click", function() {
	
		 var name   = $("#find_id_name").val();
		 var email  = $("#find_id_email").val();
		 
		 $.ajax({
			 type:		"post",
			 url:		"${contextPath}/findId.do",
			 dataType:	"json",
			 async : false,
			 data:		{"fr_name": name, "fr_email": email},
			 success:	function(data) {
				
				 $("#findIdView").html("회원님의 아이디는 '" + data.fr_id + "' 입니다." ); 
			}, error: function (){
	        	alert('정보를 다시 입력해주시길 바랍니다.' );
	        }
		 });
	});
	
	// 비밀번호 찾기
	$("#findPwdBtn").on("click", function() {
		var id    = $("#find_pwd_id").val();
	    var email = $("#find_pwd_email").val();
	    
	    $.ajax({
			 type:		"post",
			 url:		"${contextPath}/findPwd.do",
			 dataType:	"json",
			 async : false,
			 data:		{"fr_id": id, "fr_email": email},
			 success:	function(data) {

				 $("#findPwdView").html("회원님의 비밀번호는 '" + data.fr_pwd + "' 입니다." ); 
			}, error: function (){
	       	alert('정보를 다시 입력해주시길 바랍니다.' );
	       }
		 });
	});
	
});

</script>

<body>
	<div id="findField">
		<div id="findFieldSelect">
			<input type="radio" name="findView" id="findId"  onchange="findViewDisplay()" checked/> 아이디 찾기
			<input type="radio" name="findView" id="findPwd" onchange="findViewDisplay()" /> 비밀번호 찾기
		</div>
		<table id="idFindTable">
			<tr>
				<td><input type="text" name="fr_name" id="find_id_name" class="findInfo" placeholder="이름 입력" /></td>
			</tr>
			<tr>
				<td><input type="text" name="fr_email" id="find_id_email" class="findInfo" placeholder="이메일 입력" /></td>
			</tr>
			<tr>
				<td><button type="button" id="findIdBtn">아이디 찾기</button></td>
			</tr>
			<tr>
				<td id="findIdView"></td>
			</tr>
		</table>
		<table id="pwdFindTable" style="display:none;">
			<tr>
				<td><input type="text" name="fr_id" id="find_pwd_id" class="findInfo" placeholder="아이디 입력" /></td>
			</tr>
			<tr>
				<td><input type="text" name="fr_email" id="find_pwd_email" class="findInfo" placeholder="이메일 입력" /></td>
			</tr>
			<tr>
				<td><button type="button" id="findPwdBtn">비밀번호 찾기</button></td>
			</tr>
			<tr>
				<td id="findPwdView"></td>
			</tr>
		</table>
	</div>
</body>
</html>