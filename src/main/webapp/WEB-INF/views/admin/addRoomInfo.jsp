<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set  var="contextPath" value="${pageContext.request.contextPath}"/>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>룸정보 추가</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script> 
	// jQuery를 이용하여 이미지 파일을 첨부할 때 미리보기 기능을 구현한다. 		
	function readURL(input) {
		if(input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#addd').attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	$(document).ready(function() {
		
		   // 기입한 룸정보에 대한 유효성 검사
		   $("#addRoomInfoBtn").on("click", function() {    
			   
			  var roomNameRegExp 	= /^[a-zA-Z0-9가-힣]{1,12}$/; // 룸이름 유효성검사
			  var roomPersonRegExp	= /^[0-9]{1,4}$/;  			 // 룸정원 유효성검사
			  
			  var objRoomName    = document.getElementById("fr_room_name"); 	 // 룸이름 입력값 받기
			  var objRoomPerson  = document.getElementById("fr_room_person_no"); // 룸정원 입력값 받기
			  
			  // 룸이름 유효성 검사 영역
			  if($("#fr_room_name").val() == '') {
				 alert("룸이름을 입력하십시오");
				 $("#fr_room_name").focus();
				 return false;
			  }
			  if(!roomNameRegExp.test(objRoomName.value)) {
	             alert("이름에 특수문자,영어,숫자는 사용할수 없습니다. 한글만 입력하여주세요.");
	             $("#fr_room_name").focus();
	             return false;
	          }
			
			  // 룸정원 유효성 검사 영역
			  if($("#fr_room_person_no").val() == '') {
				 alert("룸정원을 입력하십시오");
				 $("#fr_room_person_no").focus();
				 return false;
			  }
			  if(!roomPersonRegExp.test(objRoomPerson.value)) {
	             alert("룸정원에 숫자만 입력하여주세요.");
	             $("#fr_room_person_no").focus();
	             return false;
	          }
			  
		      document.addRoomInfoForm.action="${contextPath}/addRoomInfoGo.do";
		      document.addRoomInfoForm.submit();   
		      
		   });
		   
		});
	</script>
</head>

<body>
	<div class="">
		<p/>
		<span class="">룸정보 추가</span><p/>
		<div id="addRoomInfoFormField">
			<form name="addRoomInfoForm" method="post" enctype="multipart/form-data">
				<div style="display:none;"><input type="text" name="" value="">업체번호</div>
						<input class="" type="text" name="fr_no" value="${fr_no}" readonly style="display:none;"/>
				<table class="">
					<tr>
						<td class="">룸 이름</td>
						<td><input class="" type="text" name="fr_room_name" id="fr_room_name" placeholder="룸 이름을 입력해 주세요."/></td>
					</tr>
					<tr>	
						<td class="">룸 정원</td>
						<td><input class="" type="number" min="1" max="100" name="fr_room_person_no" id="fr_personNo" value="1"/></td>
					</tr>		
					<tr>
						<td class="">
								<span class="">[미리보기]</span><br/>
								<div class="">
								<input id="asd" type="file" name="fr_room_image" onchange="readURL(this);" style="display:none;"/>
								</div>	
								<img id="addd" width="120" height="120"/>
								<label for="asd" class="">▶파일 첨부</label>
						</td>
					</tr>
					<tr>
						<td class="">
								<div class="">
									<br/>
									<button id="addBtn" class="">룸 추가하기</button>
									<button type="button" id="" onclick="location.href=''">취소하기</button>
								</div>
						</td>
					</tr>	
				</table>	
			</form>
		</div>
	</div>
</body>
</html>