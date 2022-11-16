<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%	request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>

var chkCnt = 0;

$(document).ready(function () { // 셋 인터벌로 반복수행한다.
	 setInterval(function() {
		 cntAjax()
		 }, 2000);		// 2초마다 cntAjax() 호출
});	

function cntAjax(){	// 반복수행되는 Ajax로, 입장시각 기준 DB 갯수와 현재의 DB 갯수를 카운팅하여 비교하기 위한 로직을 구현.
	var shotDate = document.getElementById('enterDate').value; // 입장시간을 가져와서
    $.ajax({
	       type:      "POST",                              
	        url:        "${contextPath}/chatDbCnt.do", 
	        data:		{enterDate : shotDate},
	        async:      false,
	        success:   function(data) {	// 데이터를 가져와
		if(chkCnt!=data){				// 뷰 단에 chkCnt와 data의 갯수가 !!다른 경우!!
		chkCnt = data;					// 뷰 단의 chkCnt를 data 갯수로 입력해주고,
		chat()							// chat() 펑션을 수행한다.
	        }
	        }
	    	  
	    }); 
	}

$(document).on('click', '#gogo', function(){	// 이것은 그냥 글 등록용. 새로고침 방지를 위해 Ajax로 입력.
	
	var tfr_id = '${loginId}';
	var tword = document.getElementById('wordEx').value;
	
	$.ajax({
	       type:      "POST",                              
	        url:        "${contextPath}/chatInput.do",       
	        dataType:   "json",
	        data:		{fr_id : tfr_id, word : tword},
	        async:      false,
	        success:   function(data) {
	        }
	    });
	chat()										// 입력이 완료되면 chat() 펑션으로 리스트를 다시 불러오고
	document.getElementById("wordEx").value = "";	// 인풋칸을 공백으로 바꾼 뒤
	document.getElementById("wordEx").focus();		// 포커스를 가져간다.
});

function inputChat(e){	// 이것은 그냥 글 등록용. 엔터키로 받아서 수행할 영역
	 if(e.keyCode == 13){
	var tfr_id = '${loginId}';
	var tword = document.getElementById('wordEx').value;
	
	$.ajax({
	       type:      "POST",                              
	        url:        "${contextPath}/chatInput.do",       
	        dataType:   "json",
	        data:		{fr_id : tfr_id, word : tword},
	        async:      false,
	        success:   function(data) {
	        }
	    });
	chat()										// 입력이 완료되면 chat() 펑션으로 리스트를 다시 불러오고
	document.getElementById("wordEx").value = "";	// 인풋칸을 공백으로 바꾼 뒤
	document.getElementById("wordEx").focus();		// 포커스를 가져간다.
}
}

function chat(){							// 입장시간을 기준으로 해서, 이 후 작성된 DB 데이터를 불러오기 위한 Ajax
	
	var shotDate = document.getElementById('enterDate').value;
	var chkId = document.getElementById('fr_id').value;
	var tr='';

    $.ajax({
	       type:      "POST",                              
	        url:        "${contextPath}/chatOutput.do", 
	        data:		{enterDate : shotDate},
	        async:      false,
	        success:   function(data) {	        	
	            $(data).each(function(r, item){
	            	 if(data[r].fr_id==chkId){	// 데이터에 등록된 아이디와, 로그인한 아이디가 일치하는 경우 우측으로
			              tr+='<div style="text-align:right;">'+ data[r].fr_id +' : ' + data[r].word + '</div>';
	            	 }
	            	 else {						// 나머지는 좌측으로
	            		  tr+='<div style="text-align:left;">'+ data[r].fr_id +' : ' + data[r].word + '</div>';
	            	 }
	            	 
	            });
	           $("#chatList").html(tr);	// ajax에서 구성한 tr값을 html로 전송.
	           $("#field").scrollTop($("#field")[0].scrollHeight);	// 해당 필드의 스크롤을 하단으로 고정.
	        }
	    }); 
}
</script>

<body style="text-alignn:center;">
<span style="text-alignn:center;">로그인 아이디 : ${loginId}</span>
<span style="text-alignn:center;">입장 시각 : ${enterDate}</span>


<div id="field" style="width:1200px; height:500px; border:2px solid black; margin: 0 auto 0 auto; overflow:scroll;">

<div id="chatList">채팅방 입장을 환영합니다.</div>


</div>

<input type="text" id="enterDate" value="${enterDate}" style="display:none;">
<div id="input" style="width:1200px; height:100px; margin: 0 auto 0 auto; text-algin:center;">
<form>
<input type="text" name="fr_id" id="fr_id" value="${loginId}" style="display:none;">
<input type="text" name="word" id="wordEx" maxlength="200" placeholder="채팅 내용 입력" style="width:94%;" onkeypress="return inputChat(event);"/>
<button type="button" style="width:4%;" id="gogo">전송</button>
</form>
</div>
</body>
</html>