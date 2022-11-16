package OwnerPackage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vo.ReservationVO;
import vo.StoreVO;

//-----------------------------------------------------------------------------------------------------------
// public interface OwnerController
//-----------------------------------------------------------------------------------------------------------
public interface OwnerController {

    //-----------------------------------------------------------------------------------------------------------
    // 업체정보 등록 페이지 폼 (업체 중복 등록 방지)
    //-----------------------------------------------------------------------------------------------------------
	public ModelAndView regiForm(RedirectAttributes rAttr,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 업체명 중복 체크
	//-----------------------------------------------------------------------------------------------------------
	public int checkStore(@RequestParam("fr_store_name") String fr_store_name);
	
	//-----------------------------------------------------------------------------------------------------------
	// 업체 등록 처리
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView addregi(@ModelAttribute("regi") StoreVO memberVO,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
    //-----------------------------------------------------------------------------------------------------------
    // 업체 예약관리 달력 페이지 (달력 불러올 때 예약 리스트 가져오기)
    //-----------------------------------------------------------------------------------------------------------
	public ModelAndView reservation(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 달력 불러올 때 예약 리스트 가져오기
	//-----------------------------------------------------------------------------------------------------------
	public List<ReservationVO> revAllList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
    //-----------------------------------------------------------------------------------------------------------
    // 예약 관리 업체별 룸 정보 뿌리기
    //-----------------------------------------------------------------------------------------------------------
	public ModelAndView reservationForm(@RequestParam("fr_reservation_date") String fr_reservation_date, 
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 오프라인 예약 완료 처리
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView ownerRevOk(@RequestParam("fr_room_no") int fr_room_no, @RequestParam("fr_reservation_date") String fr_reservation_date,
			   HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 예약 취소 처리 ( DEL )
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView cancleRev(@RequestParam("fr_room_no") int fr_room_no, @RequestParam("fr_reservation_date") String fr_reservation_date,
			   HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 예약 현황 보기
	//-----------------------------------------------------------------------------------------------------------
	public List<ReservationVO> revUserList(@RequestParam("fr_room_no") int fr_room_no, @RequestParam("fr_reservation_date") String fr_reservation_date,
			   HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 업체 휴무일 등록
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView restDay(@RequestParam("fr_reservation_date") String fr_reservation_date,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 업체 휴무일 취소
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView restDeleteDay(@RequestParam("fr_reservation_date") String fr_reservation_date,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}// End - public interface OwnerController
