package MemberPackage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vo.Criteria;
import vo.ReservationVO;
import vo.SelectVO;

//-----------------------------------------------------------------------------------------------------------
// public interface MemberController
//-----------------------------------------------------------------------------------------------------------
public interface MemberController {

	//-----------------------------------------------------------------------------------------------------------
	// 이용 가능한 업체 리스트 페이지
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView storeViewList(Criteria cri, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 이용 가능한 업체 지역별 검색
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView findStoreList(Criteria cri, @RequestParam("findBigStore") String findBigStore, @RequestParam("findSmallStore") String findSmallStore,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 지역검색 이후 메뉴 및 인원으로 검색
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView findMenuList(Criteria cri, SelectVO selectVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 선택한 업체의 방 리스트 페이지 (예약 가능/ 예약 완료)
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView storeViewRoomList(@RequestParam("fr_no") int fr_no, @RequestParam("personNo") int personNo, 
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 선택한 날짜와 업체의 방 리스트 페이지 (예약 가능/ 예약 완료)
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView storeDayRoomList(@RequestParam("fr_no") int fr_no, @RequestParam("personNo") int personNo, 
			@RequestParam("fr_reservation_date") String fr_reservation_date, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원 온라인 예약하기
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView onlineReservation(@ModelAttribute("reservationVO") ReservationVO reservationVO, 
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원 예약내역 확인하기 페이지
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView myReservation(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원 예약 취소하기
	//-----------------------------------------------------------------------------------------------------------
	public int myReservationCancle(@RequestParam("fr_room_no") int fr_room_no, @RequestParam("fr_reservation_date") String fr_reservation_date,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}// End - public interface MemberController
