<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%	request.setCharacterEncoding("UTF-8"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>룸정보 수정하기</title>
	<link href="${contextPath}/css/foodroom.css" rel="stylesheet"> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script>
	
	function readURL(input) {
		$('#originalImg').hide();
		$('#previewImg').show();
		
		if(input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#modImage').attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	$(document).ready(function() {
		
		// 게시글 수정
		$("#modBtn").on("click", function() {
			if (confirm("게시글을 수정 하시겠습니까??") == true) {
				document.freeboard.action = "${contextPah}/freeboard/modPosts.do";
				document.freeboard.submit();
			} else {
				return;
			}
		});
		
		// 룸정보 삭제
		$(".deleteRoomInfo").on("click", function() {		
			if (confirm("게시글을 삭제 하시겠습니까??") == true) {
				location = "";
			} else {
				return;
			}
		});		
		
	});
	
	</script>
</head>

<body>
	<div class="">
		<p/>
		<span class="">룸정보 보기</span><p/>
		<div class="">
			<form name="" method="post" enctype="multipart/form-data">			
						
				<table class="">
					<tr>
						<td class="">
							<input type="hidden" name="fr_no" value="${fr_no}"/>	
							<span>룸 이름</span>
						</td>
						<td>
							<span>${fr_room_name}</span>
						</td>
						<td class="">
							<span>룸 정원</span>
						</td>
						<td>
							<span>${fr_personNo}</span>
						</td>
					</tr>
					<tr>
					    <td class="">
					    <p/><br/>
					    	<div id="originalImg">
								<c:if test="${not empty fr_imageFile && fr_imageFile != 'null'}">		
									<input type="hidden" name="originalImageFileName" value="${fr_imageFile}"/>
									<!-- 외부 경로 이미지 불러오기 -->
									<a href="${contextPath}/img/${fr_no}/${fr_imageFile}" target="_blank" class="">[확대보기]</a>
									<img id="originalImage" usemap="#test" width="120" height="120" src="${contextPath}/img/${fr_no}/${fr_imageFile}"/><br/>
									
									<map name="test">
									<area shape="default" coords="10,8,150,292" href="${contextPath}/img/${fr_no}/${fr_imageFile}" target="_blank"/>
									</map>
								</c:if>
							</div>
							<div id="previewImg" style="display:none;">	
							<span class="imgLinkText">[미리보기]</span>
								<img id="modImage" width="120" height="120"/>
							</div>
							<input type="file" name="fr_imageFile" id="fr_imageFile" onchange="readURL(this);" style="display:none;" disabled/>
							<label for="fr_imageFile" class="addImage" style="display:none;">▶파일 첨부</label>
					    </td>
				    </tr>
				</table>
						
				<p/>
				<div id="div_btn" class="div_btnMenu">
					<input class="" type="button" value="이전" onclick="location.href=''"/>						
					<input class="" type="button" value="수정" onclick="fn_enable(this.form)"/>
					<input class="" type="button" value="삭제" onclick="#"/>
				
				</div>	
				<div id="" class="" style="display:none;">
					<input class="" type="button" id="modBtn" value="수정완료"/>
					<input class="" type="button" value="취소"	onclick="fn_enable1(this.form)"/>
				</div>
			</form>

		</div>
	</div>
</body>
</html>