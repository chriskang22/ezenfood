package MainController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import MemberPackage.DAO.MemberDAO;
import UserPackage.DAO.UserDAO;
import vo.ChatVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@Autowired
	private UserDAO userDAO;
	
	//-----------------------------------------------------------------------------------------------------------
	// 채팅 페이지 띄우기
	//-----------------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="/chat.do", method= RequestMethod.GET)
	public ModelAndView chat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//입장 시간 저장해서
		Date today = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String date = simpleDateFormat.format(today);
		
		HttpSession session = request.getSession();
		String fr_id = (String)session.getAttribute("fr_id");
		
		ModelAndView mav = new ModelAndView("/chat/chat");
		mav.addObject("loginId", fr_id);	//아이디랑
		mav.addObject("enterDate", date);	//입장시간을 오브젝트로 추가해두고,
		
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 대화 입력처리.
	//-----------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/chatInput.do", method=RequestMethod.POST)
	public void chatInput(@RequestParam("fr_id") String fr_id, @RequestParam("word") String word, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	HashMap<String, Object> map = new HashMap<String, Object>();
	map.put("fr_id", fr_id);
	map.put("word", word);
	userDAO.chatInput(map);	//리턴 없이 단순 insert만 수행하기 때문에 void로 선언.
	
	}
	

	//-----------------------------------------------------------------------------------------------------------
	// 대화 출력용
	//-----------------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="/chatOutput.do", method=RequestMethod.POST)
	public List<ChatVO> chatOutput(@RequestParam("enterDate") String enterDate,HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ChatVO> chatList = userDAO.chatList(enterDate); 
		
		return chatList;	// 채팅 내용은 입장시간을 기준해서 리스트를 뽑아온다.
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// DB 변동 감지
	//-----------------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="/chatDbCnt.do", method=RequestMethod.POST)
	public int chatDbCnt(@RequestParam("enterDate") String enterDate,HttpServletRequest request, HttpServletResponse response) throws Exception {	
		//입장시간은 그 전에 chat.do에서 생성한 값을 받아오기 때문에 변동 없다.
		int inCnt = userDAO.inCnt(enterDate);
		
		return inCnt;	// 입장 당시 DB 정보와 현재의 DB 정보의 갯수 차이를 비교하기 위해 AJAX 반복수행 카운팅용 매핑을 구성.
	}
	
}
