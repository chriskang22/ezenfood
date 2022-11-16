<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@	taglib	prefix="c"		uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%	request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>예약내역</title>
	<link href="${contextPath}/css/foodroom.css" rel="stylesheet">    
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<script>
//변수 선언부
var today = new Date();			// 오늘 날자 데이터를 변수 today로 저장.
var date = new Date();			// 비교를 하기 위하여 오늘 날자 데이터를 변수 date로 저장.
var curTime = today.getFullYear() +"-"+ (today.getMonth() + 1)  +"-"+ today.getDate();		// 현재 정확한 날짜를 불러오되, 비교를 하기 위해 날짜 사이에 '-' 표시로 균일한 조건을 갖추도록 변수 저장.

var arr = new Array();
<c:forEach items="${SetRevList}" var="SetRevList">
	arr.push({ 
		fr_reservation_date: "${SetRevList.fr_reservation_date}"
	});
</c:forEach>

//function buildCalendar() 달력을 생성하는 기능 선언
function buildCalendar() {
	// 기본 변수 초기화
	var row = null
	var cnt = 0;		// 칸의 고유 주소값을 지정하기 위한 변수.
	
	// 달력을 만들어서 출력할 table 및 tableTitle을 참조하기 위한 변수 설정.
	var calendarTable = document.getElementById("calendar");				// 달력을 생성할 위치참조
  	var calendarTableTitle = document.getElementById("calendarTitle");	// 생성한 달력의 타이틀 주소 위치참조
  	calendarTableTitle.innerHTML = today.getFullYear()+"년"+(today.getMonth()+1)+"월";	// 생성한 달력의 연,월 표기를 위한 날짜 로딩 및 분배.

  	// 달력의 첫 날과 마지막 날에 대한 변수 설정.
  	var firstDate = new Date(today.getFullYear(), today.getMonth(), 1);	// 1일 부터.
  	var lastDate = new Date(today.getFullYear(), today.getMonth()+1, 0);	// 그 다음 달의 0일 까지.

 	// 작성할 테이블을 초기화.
	while(calendarTable.rows.length > 2) {							//생성한 테이블의 열 갯수가 2줄이 넘으면
  		calendarTable.deleteRow(calendarTable.rows.length-1);		//해당하는 테이블의 열을 (줄 갯수-1) 삭제한다. 즉, 3개의 열이 생성되었다면 3-2 하여, 0번째 줄, 1번재 쭐, 2번째 줄 << 2번 값을 같은 열을 삭제한다.
	}

	// 달의 첫 날까지 빈 셀을 생성한다.
	row = calendarTable.insertRow();
  	for(i = 0; i < firstDate.getDay(); i++){
	  	cell = row.insertCell();
	  	cell.style.backgroundColor="#eeeeee";
	  	cell.width = 100; // 셀 생성시 높이 고정값.
	  	cnt += 1;
  	}

	// 달의 첫 번째 칸에 올 숫자를 설정한다.
	for(i = 1; i <= lastDate.getDate(); i++) {		// 1일부터 그 다음 달의 0일 까지(즉, 현재 달의 마지막 날 까지) 변수값을 증가하며 반복수행.
	  	cell = row.insertCell();
	  	cnt += 1;									// 각 칸의 고유 주소값을 1부터 세기 위하여 1 증가.
	  	todayDate=today.getFullYear()+"-"+(1+today.getMonth())+"-"+i;		// 오늘 날짜를 저장하기 위한 변수.	
	  	
	  	// 각 셀의 고유 주소값에 대한 정보를 하기의 구분기호로 구분하여 표기한다.
	  	if (i<10 && 1+today.getMonth() <10) {		// 일이 10보다 작고, 월이 10보다 작은 경우(둘 다 10보다 작은 경우) 구분기호 뒤에 0을 더한다.
	  		cellDate=today.getFullYear()+"-0"+(1+today.getMonth())+"-0"+i;
	  	
	  	} else if (1+today.getMonth() <10) {		// 월이 10보다 작은 경우에도 구분기호 뒤에 0을 더한다.
			cellDate=today.getFullYear()+"-0"+(1+today.getMonth())+"-"+i;
		
	  	} else if (i <10) {							// 일이 10보다 작은 경우에도 구분기호 뒤에 0을 더한다.
			cellDate=today.getFullYear()+"-"+(1+today.getMonth())+"-0"+i;
		
	  	} else {										// 그 외에는 구분기호 -만 표기한다.
			cellDate=today.getFullYear()+"-"+(1+today.getMonth())+"-"+i;
		
	  	}
	  	
		// DB와의 균일화를 위해 상동 원리로 구분기호 뒤 0을 구분한다.
	  	if(i < 10 && (1+today.getMonth() < 10)  ) {	
			cell.setAttribute('id',today.getFullYear()+"-0"+(1+today.getMonth())+"-0"+i); // 셀의 주소 정보.
	  	
	  	} else if((1+today.getMonth() < 10)) {
	    	cell.setAttribute('id',today.getFullYear()+"-0"+(1+today.getMonth())+"-"+i); // 셀의 주소 정보.
	  	
	  	} else if(i < 10) {
			cell.setAttribute('id',today.getFullYear()+"-"+(1+today.getMonth())+"-0"+i); // 셀의 주소 정보.
		
	  	} else {
	  		cell.setAttribute('id',today.getFullYear()+"-"+(1+today.getMonth())+"-"+i); // 셀의 주소 정보.
	  	}
	    
	  	cell.innerHTML = i;		// 달력에 표기될 일자의 카운트.
	  	cell.align = "center"; // 각 셀 정렬 방법.
	  	
		// 카운트 숫자(각 셀의 고유 카운트)가 7로 나누었을 때 1이 남는 경우 일요일로 구분한다. 즉, 각 1번칸, 8번칸, 15번칸 ---- 순.
	    if (cnt % 7 == 1) {
	    	cell.innerHTML = "<font color=red>" + i + "</font>";
	    }
		
		// 카운트 숫자가 7로 나누어 딱 떨어지는 경우 토요일로 처리한다. 즉, 각 7번, 14번, 21번 칸 ----순.
	    if (cnt % 7 == 0) {
	    	cell.innerHTML = "<font color=blue>" + i + "</font>";
	    	row = calendar.insertRow();
	    }
		
		// 각 셀의 고유 날짜 정보가 현재의 날짜와 일치하는 경우 오늘로 구분한다.
	    if (todayDate==curTime){
	    	cell.innerHTML = "<font color=orange class=\"textShadow\">"+i+"</font>";
	    }
		
	  	// cell안에 데이터 입력
	  	(function(i) {
	  		// 예약 완료 건수
	  		var cnt = 0;
	  		$.ajax({
	  			type:		"POST",						
 				url:		"${contextPath}/revAllList.do",		
 				dataType:	"json",						
 				async:		false,
 				success:	function(data) {
 					
 					$(data).each(function(c, item) {
 						if(data[c].fr_reservation_date == cellDate) {
 							cnt++;	
 						}
 					});
 					
	 				if(cnt > 0) {	  			
	 			  		cell.innerHTML += "<br/><span>●예약완료 : " + cnt + "</span>";
	 		  		}
 				}
	  		}); // end $.ajax	
	  	})(i); //end function(i)
	  	
	  	// 휴일 거르는 거
        for(k = 0; k < arr.length; k++) {
            if(arr[k].fr_reservation_date == cellDate) {
               cell.innerHTML += "<br>휴무";
               cell.style.backgroundColor="pink";
            }
           }
	  	
		// 셀 클릭시 수행할 기능에 대하여 설정한다.
	  	cell.onclick = function() {
	  		clickedDate = this.getAttribute('id'); // 상기 설정한 각 셀의 정보를 가져온다.
	  		// 선택한 날짜의 예약페이지로 이동
	  		location.href = "${contextPath}/reservationForm.do?fr_reservation_date=" + clickedDate;
	  	} // End - cell.onclick = function()
		
	} // for문 끝
	
	
 	// 날짜가 유효한 달력칸이 7칸 미만인 경우 나머지는 빈 셀로 채운다.
 	if(cnt % 7 != 0) {
 		for(i = 0; i < 7 - (cnt % 7); i++) {
 			cell = row.insertCell();
 			cell.style.backgroundColor="#eeeeee";
 		}
 	}
 	
 	if(cnt % 7 == 0) {							
		calendarTable.deleteRow(calendarTable.rows.length-1);
	}
} //END - function buildCalendar()

function todayCalendar() {	// 오늘 날짜 찾아가기 기능.
	today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	buildCalendar();
}	

function prevCalendar() {	// 달 뒤로 가기 기능.
	today = new Date(today.getFullYear(), today.getMonth()-1);
	buildCalendar();
}

function nextCalendar() {	// 달 앞으로 가기 기능.
	today = new Date(today.getFullYear(), today.getMonth()+1);
	buildCalendar();
}
</script>

<body>
<div id="reservationField">
	<table class="ownerCalenderLayout">
		<tr>
			<td>
				<table id="calendar" class="calendarList">
					<tr class="calendarListTop">
						<td onclick="prevCalendar()" class="calendarListTop"><span> ◀ 이전 달 </span></td>
						<td colspan="4" id="calendarTitle" class="calendarListTop">yyyy년 m월</td>
						<td onclick="todayCalendar()" class="calendarListTop"><span>오늘로 가기</span></td> 
						<td onclick="nextCalendar()" class="calendarListTop"><span> 다음 달 ▶ </span></td>
					</tr>
					<tr class="calendarDayText">
						<td class="calendarDayText" style="color:red; background:#ffdddd;">일</td>
						<td class="calendarDayText">월</td>
						<td class="calendarDayText">화</td>
						<td class="calendarDayText">수</td>
						<td class="calendarDayText">목</td>
						<td class="calendarDayText">금</td>
						<td class="calendarDayText" style="color:blue; background:#c8e3ff;">토</td>
					</tr>
					<tr>
						<td><script type="text/javascript">buildCalendar();</script></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>	
</div>
			
</body>
</html>