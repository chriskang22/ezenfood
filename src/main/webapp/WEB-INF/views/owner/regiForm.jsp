<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@	taglib	prefix="c"		uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%	request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>업체등록 페이지</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!-- 주소 찾기 api -->
<script src="http://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>

	function daumZipCode() {
		new daum.Postcode({
			oncomplete:	function(data) {
				// 팝업 창에서 검색한 결과 중 항목을 클릭하였을 경우에 실행할 코드를 이곳에 작성한다.
				
				// 각 주소의 노출규칙에 따라서 주소를 조합한다.
				// 내려오는 변수의 값이 없을 경우에는 공백('') 값을 가지므로, 이름을 참조하여 분기한다.
				var fullAddress = '';	// 최종   주소 변수
				var subAddress  = '';	// 조합형 주소 변수
				
				// 사용자가 선택한 주소의 타입에 따라서 해당 주소값을 가져온다.
				if(data.userSelectedType == 'R') {	// 도로명 주소를 선택한 경우
					fullAddress = data.roadAddress;
				} else {							// 지번 주소를 선택한 경우
					fullAddress = data.jibunAddress;
				}
				
				// 사용자가 선택한 주소가 도로명 타입일 때 주소를 조합한다.
				if(data.userSelecterdType == 'R') {
					// 법정동명이 있을 경우 추가한다.
					if(data.bname != '') {
						subAddress += data.bname;
					}
					// 건물명이 있을 경우 추가한다.
					if(data.buildingName != '') {
						subAddress += (subAddress != '' ? ', ' + data.buildingName : buildingName);
					}
				}
				
				// 조합형 주소의 유무에 따라서 양쪽에 괄호를 추가하여 최종 주소를 만든다.
				fullAddress += (subAddress != '' ? '(' + subAddress + ')' : '');
				
				// 우편 번호와 주소 정보를 해당 필드에 출력시킨다.
				document.getElementById('zipcode').value	= data.zonecode;	// 우편번호
				document.getElementById('address01').value  = fullAddress;		// 주소내용
			
				// 커서를 상세주소 입력 필드에 나타나게 한다.
				document.getElementById('address02').focus();
			}
			
			
		}).open({
			// 우편번호 팝업 창이 여러 개 뜨는 것을 방지하기 위해 popupName을 사용한다.
			popupName:	'postcodePopup'
		});	// open();만 기술을 하면 팝업 창이 여러개 나타나게 된다.
	}

	function fnc_process() { // 업체명 중복 검사
		
       	var store_name 	= document.getElementById('fr_store_name');
        var store_name_RegExpCn = /^[a-zA-Z0-9가-힣]{1,30}$/;
	    var	_store_name		= $("#fr_store_name").val();
	        
				$.ajax({	// 업체명 중복 체크 기능
						type:		"post",
						url:		"${contextPath}/checkStoreName.do",
						dataType:	"json",
						async : 	false,
						data:		{fr_store_name: _store_name},	
						success:	function(data) {
							
							// 서버에서 전송된 결과에 따라 메시지를 표시한다.
						
							if (store_name.value == '' || store_name.value == null) {		// 업체명 입력란이 공백인 경우.
								$("#storeNameChkMsg").html("업체명을 입력해주세요.");
								$("#storeNameChkMsg").css("color","white")
								$("#regiBtn").prop("disabled", true);								
							} else if (!store_name_RegExpCn.test(store_name.value)) {				// 업체명에 특수문자나 공백이 들어간 경우.
								$("#storeNameChkMsg").html("영문&숫자 4~12자리만 가능합니다");
								$("#storeNameChkMsg").css("color","pink")
								$("#regiBtn").prop("disabled", true);
					        } else if ($.trim(data) == 0) {									// 업체명이 DB와 겹치지 않는 경우.
								$("#storeNameChkMsg").html("등록 가능한 업체명 입니다.");	
								$("#storeNameChkMsg").css("color","skyblue")
								$("#regiBtn").prop("disabled", false);
							} else {														// 업체명이 DB와 겹치는 경우.
								$("#storeNameChkMsg").html("이미 사용중인 업체명 입니다.");
								$("#storeNameChkMsg").css("color","pink")
								$("#regiBtn").prop("disabled", true);
							}
						}
				});
	}

	$(document).ready(function() {				
		
		$("#regiBtn").on("click", function() {
			
			var sn_RegExp = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/; // 업체연락처 유효성검사
		    		    		     
		    var objStoreNumber = document.getElementById("fr_store_p_number"); 	// 업체연락처 입력값 받기				    				    	    	   
		    
			// 업체연락처 유효성 검사 영역
			if($("#fr_store_p_number").val() == '') {
				alert("전화번호를 입력하십시오");
				$("#fr_store_p_number").focus();
				return false;
			}
			if(!sn_RegExp.test(objStoreNumber.value)) {
		         alert("올바른 전화번호를 입력해 주세요. ex)010-9999-9999");
		         $("#fr_store_p_number").focus();
		         return false;
		     }
			
			// 업체주소 유효성 검사 영역
			if($("#zipcode").val() == '') {
				alert("우편번호를 입력하십시오");
				$("#zipcode").focus();
				return false;
			}        
			if($("#address02").val() == '') {
				alert("상세주소를 입력하십시오");
				$("#address02").focus();
				return false;
			}
			if($("#zipcode").val() != '' && $("#address02").val() != '') {
				// 주소영역이 다 입력되어있으면 
				document.getElementById('fr_address').value =
					document.getElementById('zipcode').value + "/ " +
					document.getElementById('address01').value + " " +
					document.getElementById('address02').value;
				alert(document.getElementById('fr_address').value);
			}
	        
			document.regiForm.action = "${contextPath}/addregi.do";
			document.regiForm.submit();
		
		}); // submit버튼		
		
	}); // function	
	

</script>

<body>
	<div id="regiField">
		<span>업체등록</span>
		<form id="regiForm" name="regiForm" method="post">	
			<table id="regiTable">
				<tr style="display:none">
					<td>아이디</td>
					<td><input type="text" name="fr_id" id="fr_id" value=""/></td>	
				</tr>
				<tr>
					<td>업체명</td>
					<td><input type="text" name="fr_store_name" id="fr_store_name" class="regiInput" maxlength="30" placeholder="업체명 입력" oninput="fnc_process();"/></td>	
				</tr>
				<tr>
					<td><span class="">√ 업체명 중복 체크 : </span></td>
					<td id="storeNameChkMsg">업체명 입력해주세요.</td>
				</tr>			
				<tr>
					<td>업체연락처</td>
					<td><input type="text" name="fr_store_p_number" id="fr_store_p_number" /></td>
				</tr>
				<tr>
					<td>우편번호</td>
					<td>	
						<input type="text"   class="" name="zipcode" id="zipcode" readonly/>
					</td>
					<td>	
						<input type="button" class="" onclick="daumZipCode()" value="우편번호검색"/>
					</td>
				</tr>
				<tr>
					<td>주  소</td>
					<td>	
						<input type="text" class="" name="address01" id="address01" readonly/>
					</td>
				</tr>
				<tr>
					<td>상세주소</td>
					<td>
						<input type="text" name="address02" id="address02"/>
					</td>
					<td>
						<input type="hidden" name="fr_address" id="fr_address" />
					</td>
				</tr>					
				<tr>
					<td>
						<span class="">업체 주메뉴</span>
					</td>
					<td>
						<select name="fr_menu" id="fr_menu">
							<option value="한식" selected>한식</option>
							<option value="중식">중식</option>
							<option value="일식">일식</option>
							<option value="양식">양식</option>	
							<option value="기타">기타</option>
						</select>			
					</td>
				</tr>

				<tr>					
					<td><button type="button" id="regiBtn" >등록요청</button></td>
					<td><button type="button" id="" onclick="location.href='${contextPath}/index.do'">등록취소</button></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>