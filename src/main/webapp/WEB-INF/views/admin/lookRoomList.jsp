<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%	request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>승인된 업체관리</title>
		<link href="${contextPath}/css/foodroom.css" rel="stylesheet">   
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>

<script>
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
	
	$(".addRoomBtn").on("click", function() {
		$('#addRoomInfoFormField').show();
		
	});
	
	
	// 기입한 룸정보에 대한 유효성 검사
	$("#addBtn").on("click", function() {
	   
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
		 
	   document.addRoomInfoForm.action="${contextPath}/addRoomInfoGo.do";
	   document.addRoomInfoForm.submit();   
	   
	});
	   
});



</script>

<body>
<div id="lookRoomListField">
	<div class="addRoomInfoField">
		<button type="button" class="addRoomBtn">룸 추가</button>
	</div>
	<div class="roomListField">          		
		<c:forEach var="roomList" items="${roomList}" varStatus="status">
			<div class="lookRoomView1Field">
			<c:if test="${status.index % 2 == 0}">
				<div class="lookRoomView1">
					<c:if test="${roomList.fr_room_image != null}">
						<img src="${contextPath}/roomImg/${roomList.fr_no}/${roomList.fr_room_image}" width="300" height="120" style="float:left;">
					</c:if>
					<c:if test="${roomList.fr_room_image == null}">
						<img src="${contextPath}/roomImg/imsi/logo.png" width="300" height="120" style="float:left;">
					</c:if>
					<br/>
					<span>룸 이름  </span>
					<br/>
					${roomList.fr_room_name}
					<br/>
					<br/>
					<span>룸 정원 : </span>${roomList.fr_room_person_no}
					<div class="roomListBtnField">
						<button type="button" class="roomListBtn">수정</button>
						<a href="${contextPath}/roomDelete.do?fr_room_no=${roomList.fr_room_no}&fr_no=${roomList.fr_no}&fr_room_name=${roomList.fr_room_name}"
									  			 onclick="return confirm('[${roomList.fr_room_name}] 룸을 삭제 하시겠습니까?');">	
						<button type="button" class="deleteRoom roomListBtn">삭제</button></a>
					</div>
				</div>
			</c:if>
			</div>
			<div class="lookRoomView2Field">
			<c:if test="${status.index % 2 == 1}">
				<div class="lookRoomView2">
					<c:if test="${roomList.fr_room_image != null}">
						<img src="${contextPath}/roomImg/${roomList.fr_no}/${roomList.fr_room_image}" width="300" height="120" style="float:left;">
					</c:if>
					<c:if test="${roomList.fr_room_image == null}">
						<img src="${contextPath}/roomImg/imsi/logo.png" width="300" height="120" style="float:left;">
					</c:if>
					<br/>
					<span style="border-bottom: 1px solid silver;">룸 이름 </span>
					<br/>
					${roomList.fr_room_name}
					<br/>
					<br/>
					<span>룸 정원 : </span>${roomList.fr_room_person_no}
					<div class="roomListBtnField">
						<button type="button" class="roomListBtn">수정</button>
						<a href="${contextPath}/roomDelete.do?fr_room_no=${roomList.fr_room_no}&fr_no=${roomList.fr_no}&fr_room_name=${roomList.fr_room_name}"
									  			 onclick="return confirm('[${roomList.fr_room_name}] 룸을 삭제 하시겠습니까?');">	
						<button type="button" class="deleteRoom roomListBtn">삭제</button></a>
					</div>
				</div>
			</c:if>
			</div>
		</c:forEach>
	</div>	
</div>

	<div id="addRoomInfoFormField" style="display:none;">
		<form name="addRoomInfoForm" method="post" enctype="multipart/form-data">
			<input class="" type="text" name="fr_no" value="${room_no}" readonly style="display:none;"/>
			<table class="addRoomInfoFormTable">	
				<tr style="float:right;">
					<td class="roomInfoTd">
						<br>
						<br>
						<span>룸 이름</span>
						<input class="roomNameNo" type="text" name="fr_room_name" id="fr_room_name" placeholder="룸 이름을 입력해 주세요."/>	
						<br>
						<br>
						<span>룸 정원 </span>
						<input class="roomNameNo" type="number" min="1" max="100" name="fr_room_person_no" id="fr_personNo" value="1"/>
					</td>
				</tr>
				<tr style="float:left;">
					<td colspan=2>
						<span class="">[미리보기]</span><br/>
						<div class="">
						<input id="asd" type="file" name="fr_room_image" onchange="readURL(this);" style="display:none;"/>
						</div>	
						<img id="addd" width="220" height="120" style="float:left;"/>
						<div>
							<label for="asd" class="">▶룸 이미지 첨부</label>
						</div>
					</td>
				</tr>	
				<tr>
					<td colspan=2>	
						<button type="button" id="addBtn" class="roomInfoBtn">룸 추가하기</button>
						<button type="button" class="roomInfoBtn" onclick="location.href=''">취소하기</button>
					</td>
				</tr>	
			</table>	
		</form>
	</div>


</body>
</html>