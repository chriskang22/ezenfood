<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="date"		    value="${date}"/>
<%	request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>룸 목록</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
   		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<link href="${contextPath}/css/ezen.css" rel="stylesheet">    		
</head>
<script>
var fr_no	 = "<c:out value='${fr_no}'/>";     // 업체 번호 가져오기
var personNo = "<c:out value='${personNo}'/>";  // 회원이 선택한 인원 수 가져오기

$(function() {

	
	// 예약 원하는 일자
	$("#SelectDate").datepicker({
		defaultDate: new Date(),
		minDate: 0, // 데이터 피커에서 오늘이전 선택 불가
		onSelect: function(date)	 {
			//alert(date)
			//alert(fr_no)
			//alert(personNo)
			var fr_reservation_date = date
			//alert(fr_reservation_date)
			location.href="${contextPath}/selectDayStoreRoomListForm.do?fr_no=" + fr_no + "&personNo=" + personNo + "&fr_reservation_date=" + fr_reservation_date
		}
	});

});

// 데이트피커의 기본설정
$(function() {
	$.datepicker.setDefaults({
		changeYear:		true,		// 년도를 바꿀 수 있는 셀렉트 박스를 보여준다.
		changeMonth:	true,		// 월을 바꿀 수 있는 셀렉트 박스를 보여준다.
		showAnim:		"slide",	// 애니메이션을 적용한다.
		dateFormat:		'yy-mm-dd',	// 날짜 포맷. 보통 yy-mm-dd를 많이 사용한다.
		prevText:		'이전 달',	// ◀ 버튼에 마우스 오버시 이전달 텍스트를 보여준다.
		nextText:		'다음 달',	// ▶ 버튼에 마우스 오버시 다음달 텍스트를 보여준다.
		closeText:		'닫기',		// 닫기 버튼의 텍스트 변경
		currentText:	'오늘',		// 오늘 텍스트 변경
		// 한들 캘린더 중 월 표시를 위한 부분
		monthNames:		['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort:['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames:		['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort:	['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin:	['일', '월', '화', '수', '목', '금', '토'],
		showMonthAfterYear:	true,	// true : 년 월, false : 월 년 순으로 보여준다.
		yearSuffix:		'년',		// 년도 뒤에 글자
		showButtonPanel:	true,	// 오늘로 가는 버튼과 달력의 닫기 버튼 보기 옵션
		// buttonImageOnly:		true,	// input 옆에 조그마한 아이콘으로 캘린더 선택가능하게 하기
		// buttonImage:			"images/calendar.gif"	// 조그마한 아이콘 이밎
		// buttonText:			"Select Date"			// 조그마한 아이콘 툴팁
	});
});


</script>
<body>
<div class="" style="width=100%; height:100%; overflow:scroll;">


	<div style="width=100%; height:4%;">
		<form method="post" name="ef_alter">
			<table id="">
				<tr>
					<td>예약날짜 : </td>
		      		<td><input type="text" id="SelectDate" value="" placeholder="날짜선택" readonly/></td>
		      		<td> 선택한 날짜 : ${date}</td>
				</tr>
			</table>
		</form>
	</div>
	<c:if test="${restCheck==0 }">
	<div>룸 목록</div>
	
	
	<div>	
		<div class="reservationPossible">
			<span>예약 가능</span><hr/>
			<!-- for문 조건:예약가능한 -->
			<c:forEach var="selectStoreRoomList" items="${selectStoreRoomList}" varStatus="status">
			<div style="min-height:120px;">
				<c:if test="${selectStoreRoomList.fr_room_image != null}">
					<img id="loomImage" usemap="" src="${contextPath}/roomImg/${selectStoreRoomList.fr_no}/${selectStoreRoomList.fr_room_image}" width="300" height="120" style="float:left;"/>
				</c:if>
				<c:if test="${selectStoreRoomList.fr_room_image == null}">
					<img id="loomImage" usemap="" src="${contextPath}/roomImg/imsi/logo.png" width="300" height="100" style="float:left;"/>
				</c:if>
				<span class="">룸이름 : </span>
				<span class="">${selectStoreRoomList.fr_room_name}</span><br/>							
				<span class="">룸정원 : </span>
				<span class="">${selectStoreRoomList.fr_room_person_no}</span>
				<form action="${contextPath}/onlineReservationForm.do" method="POST">
					예약인원<input type="number"  name="fr_reservation_person_no" min="1" max="${selectStoreRoomList.fr_room_person_no}"> <!-- 예약인원 / max는 룸 정원인원 -->
					<input type="hidden" name="fr_room_no" value="${selectStoreRoomList.fr_room_no}">			<!-- 룸 번호 -->
					<input type="hidden" name="fr_reservation_date" id="fr_reservation_date" value="${date}">	<!-- 예약일자 -->
					<button type="submit" id="reservationBtn">예약하기</button>				
				</form>
			</div>
			<br/>
			<br/>	
			</c:forEach>	
		</div>
					
		<div class="reservatioinImpossible">
			<span>예약 불가</span><hr/>
			<!-- for문 조건:예약불가능한 -->
			<c:forEach var="completionRoomList" items="${completionRoomList}" varStatus="status">
				<div style="min-height:120px;"> 
				    <c:if test="${completionRoomList.fr_room_image != null}">
                  		<img id="loomImage" usemap="" width="300" height="120" src="${contextPath}/roomImg/${completionRoomList.fr_no}/${completionRoomList.fr_room_image}" style="float:left;"/>
               		</c:if>
               		<c:if test="${completionRoomList.fr_room_image == null}">
                 		<img id="loomImage" usemap="" width="300" height="120" src="${contextPath}/roomImg/imsi/logo.png" style="float:left;"/>
               		</c:if>
					<span class="">룸이름 : </span>
					<span class="">${completionRoomList.fr_room_name}</span><br/>	
					<span class="">룸정원 : </span>
					<span class="">${completionRoomList.fr_room_person_no}</span>
					<div>
						<button type="button" id="">예약대기</button>
					</div>
				</div>
			</c:forEach>
		</div>
				
	</div>	
	</c:if>
	<c:if test="${restCheck!=0 }">
	<span style="text-align:center; font-size:50px;">업체 휴무일 입니다. 다른 일자를 선택해 주세요.</span>
	</c:if>
</div>
</body>
</html>