package AdminPackage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vo.RoomVO;
import vo.StoreVO;

//-----------------------------------------------------------------------------------------------------------
// public interface AdminController
//-----------------------------------------------------------------------------------------------------------
public interface AdminController {
	
	//-----------------------------------------------------------------------------------------------------------
	// 승인 요청 List 페이지 폼
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView approveForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

	//-----------------------------------------------------------------------------------------------------------
	// 승인하기 (미등록 업체 승인하기, 업체 승인시 fr_class를 12로 변경)
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView approveGo(@ModelAttribute("storeVO") StoreVO storeVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 승인된 업체 관리 List 페이지 폼
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView manageFoodRoom(@RequestParam(value="page", required = false) String page, @RequestParam(value="selectText", required = false) String selectText, 
			@RequestParam(value="selectChk", required = false) String selectChk, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 룸 정보 List 페이지 폼
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView lookRoomList(@ModelAttribute("roomVO") RoomVO roomVO,@RequestParam("fr_no") int fr_no, 
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 룸추가 페이지 폼
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView addRoomInfo(@RequestParam("fr_no") int fr_no, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 룸 정보 추가
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView addRoomInfo(@ModelAttribute("RoomVO") RoomVO roomVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 룸 삭제하기
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView roomDelete(@RequestParam("fr_room_no") int fr_room_no, @RequestParam("fr_no") int fr_no, @RequestParam("fr_room_name") String fr_room_name,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}// End - public interface AdminController



