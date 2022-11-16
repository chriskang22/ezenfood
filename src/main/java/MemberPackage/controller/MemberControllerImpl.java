package MemberPackage.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import MemberPackage.DAO.MemberDAO;
import vo.Criteria;
import vo.PageMaker;
import vo.ReservationVO;
import vo.RoomVO;
import vo.SelectVO;
import vo.StoreVO;

//-----------------------------------------------------------------------------------------------------------
// public class MemberControllerImpl implements MemberController
//-----------------------------------------------------------------------------------------------------------
@Controller("memberController")
public class MemberControllerImpl implements MemberController {
	
	@Autowired
	private MemberDAO memberDAO;

	//-----------------------------------------------------------------------------------------------------------
	// 이용 가능한 업체 리스트 페이지
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/storeViewListForm.do", method=RequestMethod.GET)
	public ModelAndView storeViewList(Criteria cri, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("/member/storeListPage");
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);								// page와 perPageNum을 셋팅해준다.
		pageMaker.setTotalCount(memberDAO.countList());		// 총 가져와야 할 리스트 글의 갯수(count) 
		System.out.println("pageMaker.getTotalCount 값 => "+ pageMaker.getTotalCount());
		System.out.println("pageStart 값, perPageNum값 => "+ cri.getPageStart() + "," + cri.getPerPageNum());
		
		List<StoreVO> storeView = memberDAO.storeViewList(cri);	// 이용 가능한 업체 List로 보여주기	
		
		System.out.println("storeView 값 => "+ storeView);
		
		mav.addObject("storeViewList", storeView);
		mav.addObject("pageMaker", pageMaker); // pageMaker에는 페이징을 위한 버튼의 값들이 들어 있다.
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 이용 가능한 업체 지역별 검색
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/findStoreList.do", method=RequestMethod.GET)
	public ModelAndView findStoreList(Criteria cri, @RequestParam("findBigStore") String findBigStore, @RequestParam("findSmallStore") String findSmallStore,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("화면에서 받아온 findBigStore 값 => " + findBigStore);
		System.out.println("화면에서 받아온 findSmallStore 값 => " + findSmallStore);
		
		// 지역 소분류가 '모두'인경우 '/'로 바꿔준다. 
		// 주소에는 '/'이 모두 들어가 있으므로 서울만 선택하였더라도 서울에 한해서는 모든 업체가 나오도록 해준다.
		SelectVO select = new SelectVO();			// 비어있는 selectVO 생성
		
		select.setFindBigStore(findBigStore);		// findBigStore 값 VO에 담기
		if(findSmallStore.equals("모두")) {			// 소분류가 모두인 경우 findSmallStore값을 / 로 바꿔준다.
			select.setFindSmallStore("/");
			System.out.println("소분류가 모두인 경우 findSmallStore 값 => "+ select);
		}
		// else가 필요한 이유는 모두가 들어오고 else가 없으면 다시 select.setFindSmallStore(findSmallStore);를 통해 모두로 돌아간다. 즉, 모두 -> / -> 모두로 변한다.
		else {											// 소분류가 지정된 경우 가져온 소분류 값을 그대로 VO에 담는다.
			select.setFindSmallStore(findSmallStore);	// findSmallStore 값 VO에 담기
			System.out.println("소분류 확인 findSmallStore 값 => "+ select);
		}
		
		// 페이징 처리를 위한 셋팅
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);										// page와 perPageNum을 셋팅해준다.
		pageMaker.setTotalCount(memberDAO.countList2(select));		// 총 가져와야 할 리스트 글의 갯수(count)
		System.out.println("pageMaker.getTotalCount 값 => "+ pageMaker.getTotalCount());
		
		System.out.println("pageStart 값, perPageNum값 => "+ cri.getPageStart() + "," + cri.getPerPageNum());
		
		HashMap<String, Object> map = new HashMap<String, Object>(); // HashMap 컬렉션 생성 
		// HashMap에 담기 위해서 selectVO에 담긴 값과, cri담긴 값을 빼내서 필요한 값만 담는다.
		// 빼지 않고 selectVO, cri값을 그냥 보내면 HashMap안에 다시 selectVO와 cri안에 각각 파라메터값들이 들어 있기에 사용할 수 없다? (메퍼에서 적용이 안되서 뺀것)
		map.put("findBigStore",select.getFindBigStore());
		map.put("findSmallStore",select.getFindSmallStore());
		map.put("pageStart",cri.getPageStart());
		map.put("perPageNum",cri.getPerPageNum());
		System.out.println("map 값 => " + map);
		
		List<StoreVO> FindStore = new ArrayList<> (); 	// DB에서 가져온 결과 값을 담기위해 List생성
		FindStore = memberDAO.findStoreList(map);
		System.out.println("FindStore 가져온 값 => "+ FindStore);
		
		ModelAndView mav = new ModelAndView("/member/storeListPage");
		mav.addObject("storeViewList", FindStore);
		mav.addObject("findBigStore", findBigStore);		// 지역검색 후 메뉴 및 인원 검색 시 주소 재사용을 위하여 다시 보내준다.
		mav.addObject("findSmallStore", findSmallStore);
		mav.addObject("pageMaker", pageMaker); // pageMaker에는 페이징을 위한 버튼의 값들이 들어 있다.
		return mav;		
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 지역검색 이후 메뉴 및 인원으로 검색
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/findStoreList2.do", method=RequestMethod.GET)
	public ModelAndView findMenuList(Criteria cri, SelectVO selectVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("화면에서 받아온 selectVO 값 => " + selectVO);

		String findBigStore = selectVO.getFindBigStore(); // 다음 페이지에 보내주기 위해 selectVO에서 꺼낸다
		// findBigStore가 모두인 경우 /로 바꿔 준다.
		// /로 바꿔주어야 지역을 대분류만 선택 했을 경우에도 검색이 가능하게 된다.
		if(selectVO.getFindSmallStore().equals("모두")) {
			selectVO.setFindSmallStore("/");
		}
		System.out.println("화면에서 받아온 값 if문 이후" + selectVO);
		
		int personNo = (int) selectVO.getPersonNo(); //다음 페이지에 보내주기 위해 selectVO에 담겨 있는 고객이 검색 시 입력한 인원수를 가져와 저장
		System.out.println("화면에서 받아온 인원수를 personNo 담기 =>" + personNo);
		
		// 페이징 처리를 위한 셋팅
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);										// page와 perPageNum을 셋팅해준다.
		pageMaker.setTotalCount(memberDAO.countList3(selectVO));	// 총 가져와야 할 리스트 글의 갯수(count)
		System.out.println("pageMaker.getTotalCount 값 => "+ pageMaker.getTotalCount());
		
		System.out.println("pageStart 값, perPageNum값 => "+ cri.getPageStart() + "," + cri.getPerPageNum());
		
		HashMap<String, Object> map = new HashMap<String, Object>(); // HashMap 컬렉션 생성
		// HashMap에 담기 위해서 selectVO에 담긴 값과, cri담긴 값을 빼내서 필요한 값만 담는다.
		// 빼지 않고 selectVO, cri값을 그냥 보내면 HashMap안에 다시 selectVO와 cri안에 각각 파라메터값들이 들어 있기에 사용할 수 없다? (메퍼에서 적용이 안되서 뺀것)
		map.put("pageStart",cri.getPageStart());
		map.put("perPageNum",cri.getPerPageNum());
		map.put("findBigStore",findBigStore);
		map.put("findSmallStore",selectVO.getFindSmallStore());
		map.put("personNo",personNo);
		map.put("menu1",selectVO.getMenu1());
		map.put("menu2",selectVO.getMenu2());
		map.put("menu3",selectVO.getMenu3());
		map.put("menu4",selectVO.getMenu4());
		map.put("menu5",selectVO.getMenu5());
		System.out.println("map에 담은 값 => "+ map);
		
		List<StoreVO> FindStore2 = new ArrayList<> ();
		
		FindStore2	= memberDAO.findStoreList2(map);		
		System.out.println("FindStore2 가져온 값 => "+ FindStore2);
		System.out.println("selectVO 값 => "+ selectVO);
		
		ModelAndView mav = new ModelAndView("/member/storeListPage");
		mav.addObject("selectVO", selectVO);
		mav.addObject("findBigStore", findBigStore);
		mav.addObject("findSmallStore", selectVO.getFindSmallStore());
		mav.addObject("storeViewList", FindStore2);
		mav.addObject("personNo", personNo); // 고객이 입력한 인원수도 같이 보내준다.
		mav.addObject("pageMaker", pageMaker); // pageMaker에는 페이징을 위한 버튼의 값들이 들어 있다.
		
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 선택한 업체의 방 리스트 페이지 (예약 가능/ 예약 완료)
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/selectStoreRoomListForm.do", method=RequestMethod.GET)
	public ModelAndView storeViewRoomList(@RequestParam("fr_no") int fr_no, @RequestParam("personNo") int personNo, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("화면에서 받아온 fr_no, personNo 값 => " + fr_no + "," + personNo);		
		
		Date today = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String date = simpleDateFormat.format(today);
		System.out.println("오늘날짜 가져오기 => "+ date);
		
		HashMap<Object, Object> map = new HashMap<Object, Object>(); // HashMap 컬렉션 생성 후 화면에서 받아온 값 담기
		map.put("fr_no",fr_no);
		map.put("personNo",personNo);
		map.put("fr_reservation_date",date);
		
		int restCheck = memberDAO.restCheck(map); // 휴무일 판단하기
		
		List<RoomVO> selectStoreRoomList = memberDAO.selectStoreRoomList(map);	// 선택한 업체의 예약 가능한 방 List로 보여주기	
		System.out.println("selectStoreRoomList 가져온 값 => "+ selectStoreRoomList);
		
		List<RoomVO> completionRoomList = memberDAO.completionRoomList(map);	// 선택한 업체의 예약 완료된 방 List로 보여주기	
		System.out.println("completionRoomList 가져온 값 => "+ completionRoomList);
		
		ModelAndView mav = new ModelAndView("/member/roomListPage");
		mav.addObject("selectStoreRoomList", selectStoreRoomList);
		mav.addObject("completionRoomList", completionRoomList);
		mav.addObject("fr_no", fr_no);
		mav.addObject("personNo", personNo);
		mav.addObject("date", date);
		mav.addObject("restCheck", restCheck);
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 선택한 날짜와 업체의 방 리스트 페이지 (예약 가능/ 예약 완료)
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/selectDayStoreRoomListForm.do", method=RequestMethod.GET)
	public ModelAndView storeDayRoomList(@RequestParam("fr_no") int fr_no, @RequestParam("personNo") int personNo, 
			@RequestParam("fr_reservation_date") String fr_reservation_date, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("화면에서 받아온 fr_no, personNo, fr_reservation_date 값 => " + fr_no + "," + personNo + "," + fr_reservation_date);		
		
		HashMap<Object, Object> map = new HashMap<Object, Object>(); // HashMap 컬렉션 생성 후 화면에서 받아온 값 담기
		map.put("fr_no",fr_no);
		map.put("personNo",personNo);
		map.put("fr_reservation_date",fr_reservation_date);
		
		int restCheck = memberDAO.restCheck(map); // 휴무일 판단하기
		System.out.println("들어간 날짜 휴일 판단" + restCheck);
		
		List<RoomVO> selectDateRoomList = memberDAO.selectDateRoomList(map);	// 선택한 날짜와 업체의 예약 가능한 방 List로 보여주기	
		System.out.println("selectDateRoomList 가져온 값 => "+ selectDateRoomList);
		
		List<RoomVO> completionDateRoomList = memberDAO.completionDateRoomList(map);	// 선택한 날짜와 업체의 예약 완료된 방 List로 보여주기	
		System.out.println("completionDateRoomList 가져온 값 => "+ completionDateRoomList);
		
		ModelAndView mav = new ModelAndView("/member/roomListPage");
		mav.addObject("selectStoreRoomList", selectDateRoomList);
		mav.addObject("completionRoomList", completionDateRoomList);
		mav.addObject("fr_no", fr_no);
		mav.addObject("personNo", personNo);
		mav.addObject("date", fr_reservation_date);
		mav.addObject("restCheck", restCheck);
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원 온라인 예약하기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/onlineReservationForm.do", method=RequestMethod.POST)
	public ModelAndView onlineReservation(@ModelAttribute("reservationVO") ReservationVO reservationVO, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("reservationVO 가져온 값 => "+ reservationVO);
		
		// 세션 가져오기
		HttpSession session = request.getSession();
		// 세션에 담긴 이름/연락처 저장
		String sfr_name  	= (String) session.getAttribute("fr_name");
		String sfr_p_number = (String) session.getAttribute("fr_p_number");
		// reservationVO에 세션에 담긴 이름/연락처 값 저장
		reservationVO.setFr_name(sfr_name);
		reservationVO.setFr_p_number(sfr_p_number);
		
		String day = reservationVO.getFr_reservation_date();
		
		System.out.println("reservationVO에 세션 추가 값 => "+ reservationVO);
		memberDAO.onlineReservation(reservationVO);
		
		ModelAndView mav = new ModelAndView("redirect:/myReservationForm.do");
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원 예약내역 확인하기 페이지
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/myReservationForm.do", method=RequestMethod.GET)
	public ModelAndView myReservation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 세션 가져오기
		HttpSession session = request.getSession();
		// 세션에 담긴 이름/연락처 저장
		String sfr_name  	= (String) session.getAttribute("fr_name");
		String sfr_p_number = (String) session.getAttribute("fr_p_number");
		
		HashMap<Object, Object> map = new HashMap<Object, Object>(); // HashMap 컬렉션 생성 후 세션에서 받아온 값 담기
		map.put("fr_name",sfr_name);
		map.put("fr_p_number",sfr_p_number);
		System.out.println("map(이름,연락처)에 세션 추가 값 => "+ map);
		
		List<HashMap<String, Object>> myReservationList = memberDAO.myReservation(map);	// 회원이 예약한 룸 List로 보여주기	
		System.out.println("myReservation DB에서 가져온 값 => "+ myReservationList);
		
		ModelAndView mav = new ModelAndView("/member/lookReservationList");
		mav.addObject("myReservationList", myReservationList);
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원 예약 취소하기
	//-----------------------------------------------------------------------------------------------------------
	@ResponseBody
	@Override
	@RequestMapping(value="/myReservationCancle.do", method=RequestMethod.POST)
	public int myReservationCancle(@RequestParam("fr_room_no") int fr_room_no, @RequestParam("fr_reservation_date") String fr_reservation_date,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 세션 가져오기
		HttpSession session = request.getSession();
		// 세션에 담긴 이름/연락처 저장
		String sfr_name  	= (String) session.getAttribute("fr_name");
		String sfr_p_number = (String) session.getAttribute("fr_p_number");
		
		HashMap<String, Object> map = new HashMap<String, Object>(); // 룸번호와 예약일자를 가져와서 HashMap을 생성하여 담는다.
		map.put("fr_room_no", fr_room_no);
		map.put("fr_reservation_date", fr_reservation_date);
		
		// 세션에서 가져온 이름/폰번호 값 HashMap에 추가로 담기
		map.put("fr_name",sfr_name);
		map.put("fr_p_number",sfr_p_number);
		System.out.println("화면에서 가져온 룸번호/예약일자, 세션에서 가져온 이름/폰번호 값 => "+ map);
		
		int result = memberDAO.myReservationCancle(map);
		System.out.println("result 값 => "+ result);

		return result; 
	}
}// End - public class MemberControllerImpl implements MemberController


