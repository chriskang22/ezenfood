package OwnerPackage.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import OwnerPackage.DAO.OwnerDAO;
import vo.ReservationVO;
import vo.RoomVO;
import vo.SetRevVO;
import vo.StoreVO;

//-----------------------------------------------------------------------------------------------------------
// public class OwnerControllerImpl implements OwnerController
//-----------------------------------------------------------------------------------------------------------
@Controller("OwnerController")
public class OwnerControllerImpl implements OwnerController {

	@Autowired
	private OwnerDAO ownerDAO;
	
    //-----------------------------------------------------------------------------------------------------------
    // 업체정보 등록 페이지 폼 (업체 중복 등록 방지)
    //-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/regiForm.do", method=RequestMethod.GET)
	public ModelAndView regiForm(RedirectAttributes rAttr,HttpServletRequest request, HttpServletResponse response) throws Exception { 	   
	   
		// 세션 가져오기
		HttpSession session = request.getSession();
		// 세션에 담긴 아이디 저장
		String sfr_id  = (String) session.getAttribute("fr_id");
		System.out.println("controllerFr_id ==>" + sfr_id );
		
		// 세션으로 가져온 아이디가 업체등록한 아이디인지 비교
		int idCount = ownerDAO.regiForm(sfr_id);
		
		ModelAndView mav = new ModelAndView();
		
		// 등록한 업체가 없는 경우
		if(idCount == 0) {
			mav.setViewName("/owner/regiForm"); // 업체 등록 페이지로 이동
		}
		// 등록한 업체가 있는 경우
		else {
			mav = new ModelAndView("redirect:/index.do"); // 메인 페이지로 이동
			rAttr.addAttribute("result", "regiSuccess");
		}	
		return mav;
	 }

	//-----------------------------------------------------------------------------------------------------------
	// 업체명 중복 체크
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@ResponseBody
	@RequestMapping(value="/checkStoreName.do", method=RequestMethod.POST)
	public int checkStore(@RequestParam("fr_store_name") String fr_store_name) {
		
		System.out.println("controller checkStore ==> " + fr_store_name);
		int cnt = ownerDAO.checkStore(fr_store_name);
		System.out.println("controller checkStore 결과 ==> " + cnt);
		return cnt;	
	}	
	
	//-----------------------------------------------------------------------------------------------------------
	// 업체 등록 처리
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/addregi.do", method=RequestMethod.POST)
	public ModelAndView addregi(@ModelAttribute("store") StoreVO storeVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 세션 가져오기
		HttpSession session = request.getSession();
		// 세션에 담긴 아이디 저장
		String sfr_id  = (String) session.getAttribute("fr_id");
		// storeVO에 세션에 담긴 아이디 값 저장
		storeVO.setFr_id(sfr_id);
		System.out.println("controllerVO ==>" + storeVO);
		
		ModelAndView mav = new ModelAndView();
		
		int result = ownerDAO.addregi(storeVO); // 업체 등록
		System.out.println("dao 업체등록 결과 ==>" + result);
		mav = new ModelAndView("redirect:/index.do");
		
		return mav;
	}
   
    //-----------------------------------------------------------------------------------------------------------
    // 업체 예약관리 달력 페이지 (달력 불러올 때 예약 리스트 가져오기)
    //-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/reservation.do", method=RequestMethod.GET)
    public ModelAndView reservation(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	HttpSession session = request.getSession();
		String fr_id = (String)session.getAttribute("fr_id");
		
		int fr_no = ownerDAO.findFr_no(fr_id);
    	
    	List<SetRevVO> SetRevList = ownerDAO.SetRevList(fr_no);
    	ModelAndView mav = new ModelAndView();
        mav.setViewName("/owner/reservation");
        mav.addObject("SetRevList", SetRevList);
        return mav;
    }
    
	//-----------------------------------------------------------------------------------------------------------
	// 달력 불러올 때 예약 리스트 가져오기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@ResponseBody
	@RequestMapping(value="/revAllList.do", method=RequestMethod.POST)
	public List<ReservationVO> revAllList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// System.out.println("달력 페이지 ajax...");
		HttpSession session = request.getSession();
		String fr_id = (String)session.getAttribute("fr_id");
		
		int fr_no = ownerDAO.findFr_no(fr_id);
		// System.out.println("ajax 찾아온 fr_no ==> " + fr_no);
		List<ReservationVO> revAllList = ownerDAO.revAllList(fr_no);
		// System.out.println("달력 예약 리스트 ==> " + revAllList);
			return revAllList;
	}
		
    //-----------------------------------------------------------------------------------------------------------
    // 예약 관리 업체별 룸 정보 뿌리기
    //-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/reservationForm.do", method= {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView reservationForm(@RequestParam("fr_reservation_date") String fr_reservation_date, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String fr_id = (String)session.getAttribute("fr_id");
		
		System.out.println("fr_reservation_date ==>" + fr_reservation_date);		
		int fr_no = ownerDAO.findFr_no(fr_id);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fr_reservation_date", fr_reservation_date);
		map.put("fr_no", fr_no);

		//예약 완료된 룸 리스트
		List<RoomVO> revRoomList = ownerDAO.revRoomList(map);
		
		//예약 가능한 룸 리스트
		List<RoomVO> frRoomNoList = ownerDAO.frRoomNoList(map);
		
		//휴무 지정 판단 --- count 0 = 정상영업중 , count >=1 휴무
		int revCnt = ownerDAO.revCnt(map);

		ModelAndView mav = new ModelAndView("/owner/reservationForm");
		
		mav.addObject("revRoomList", revRoomList);
		mav.addObject("frRoomNoList", frRoomNoList);
		mav.addObject("revCnt", revCnt);
		mav.addObject("fr_reservation_date", fr_reservation_date);
		
		
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 오프라인 예약 완료 처리
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/ownerRevOk.do", method=RequestMethod.GET)
	public ModelAndView ownerRevOk(@RequestParam("fr_room_no") int fr_room_no, @RequestParam("fr_reservation_date") String fr_reservation_date,
		   HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fr_reservation_date", fr_reservation_date);
		map.put("fr_room_no", fr_room_no);
		
		ownerDAO.ownerRevOk(map);
		
		ModelAndView mav = new ModelAndView("redirect:/reservationForm.do");
		mav.addObject("fr_reservation_date", fr_reservation_date);
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 예약 취소 처리 ( DEL )
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/cancleRev.do", method=RequestMethod.GET)
	public ModelAndView cancleRev(@RequestParam("fr_room_no") int fr_room_no, @RequestParam("fr_reservation_date") String fr_reservation_date,
		   HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fr_reservation_date", fr_reservation_date);
		map.put("fr_room_no", fr_room_no);
		
		ownerDAO.cancleRev(map);
		
		ModelAndView mav = new ModelAndView("redirect:/reservationForm.do");
		mav.addObject("fr_reservation_date", fr_reservation_date);
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 예약 현황 보기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@ResponseBody
	@RequestMapping(value="/revUserList.do", method=RequestMethod.POST)
	public List<ReservationVO> revUserList(@RequestParam("fr_room_no") int fr_room_no, @RequestParam("fr_reservation_date") String fr_reservation_date,
		   HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fr_reservation_date", fr_reservation_date);
		map.put("fr_room_no", fr_room_no);
		
		//예약 정보(회원 리스트)
		List<ReservationVO> revUserList = ownerDAO.revUserList(map); 
		System.out.println("가져온 리스트" + revUserList);
		return revUserList;
	}

	//-----------------------------------------------------------------------------------------------------------
	// 업체 휴무일 등록
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/restDay.do", method=RequestMethod.GET)
	public ModelAndView restDay(@RequestParam("fr_reservation_date") String fr_reservation_date,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String fr_id = (String)session.getAttribute("fr_id");
		
		System.out.println("fr_reservation_date ==>" + fr_reservation_date);		
		int fr_no = ownerDAO.findFr_no(fr_id);
		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fr_reservation_date", fr_reservation_date);
		map.put("fr_no", fr_no);
		
		ownerDAO.restDay(map);
		
		ModelAndView mav = new ModelAndView("redirect:/reservationForm.do");
		mav.addObject("fr_reservation_date", fr_reservation_date);
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 업체 휴무일 취소
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/restDeleteDay.do", method=RequestMethod.GET)
	public ModelAndView restDeleteDay(@RequestParam("fr_reservation_date") String fr_reservation_date,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String fr_id = (String)session.getAttribute("fr_id");
		
		System.out.println("fr_reservation_date ==>" + fr_reservation_date);		
		int fr_no = ownerDAO.findFr_no(fr_id);
		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fr_reservation_date", fr_reservation_date);
		map.put("fr_no", fr_no);
		
		ownerDAO.restDeleteDay(map);
		
		ModelAndView mav = new ModelAndView("redirect:/reservationForm.do");
		mav.addObject("fr_reservation_date", fr_reservation_date);
		return mav;
	}
 
}// End - public class OwnerControllerImpl implements OwnerController
