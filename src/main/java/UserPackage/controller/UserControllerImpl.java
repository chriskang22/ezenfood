package UserPackage.controller;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import UserPackage.DAO.UserDAO;
import vo.UserVO;

@Controller("userController")
public class UserControllerImpl implements UserController {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserVO userVO;
	
	//-----------------------------------------------------------------------------------------------------------
	// 메인 페이지 불러오기 layout
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/index.do", method=RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "main";
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원가입 페이지 폼 불러오기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/signUp.do", method=RequestMethod.GET)
	public ModelAndView signUpForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/user/signUpForm");
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원 가입 처리
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/loginGO.do", method=RequestMethod.POST)
	public ModelAndView addUser(@ModelAttribute("UserVO") UserVO userVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		System.out.println("controllerVO ==>" + userVO);
		ModelAndView mav = new ModelAndView();
			
		int result1 = userDAO.addUser(userVO); 	// 회원 가입
		mav = new ModelAndView("redirect:/index.do");
			
		return mav;
	}	
	
	//-----------------------------------------------------------------------------------------------------------
	// 로그인 페이지 폼 불러오기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/loginForm.do", method=RequestMethod.GET)
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/user/loginForm");
		return mav;
	}	
	
	//-----------------------------------------------------------------------------------------------------------
	// 로그인 처리
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("user") UserVO user, RedirectAttributes rAttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println("controller 로그인 ==> " + user);   
		
		
		ModelAndView mav = new ModelAndView();
		
		userVO = userDAO.login(user);   
	    
		System.out.println("controller 로그인 ==> " + userVO); // userVO에 입력한 로그인 정보가 있는지 확인
		
		
		if(userVO == null) { // 로그인 정보가 없을 경우
			// 다시 로그인 페이지로
			rAttr.addAttribute("result", "loginFailed");
			mav = new ModelAndView("redirect:/loginForm.do");
			
		} else { // 로그인 정보가 있는 경우
			// 입력한 id, pwd 가 등록된 id, pwd와 같을 경우
			if(user.getFr_id().equals(userVO.getFr_id()) && user.getFr_pwd().equals(userVO.getFr_pwd())) { 			
				
					// 세션 생성
					HttpSession session = request.getSession();
					
					session.setAttribute("fr_id", userVO.getFr_id());
					session.setAttribute("fr_pwd", userVO.getFr_pwd());
					session.setAttribute("fr_name", userVO.getFr_name());
					session.setAttribute("fr_p_number", userVO.getFr_p_number());
					session.setAttribute("fr_email", userVO.getFr_email());
					session.setAttribute("fr_class", userVO.getFr_class());
					session.setAttribute("isLogOn", 	true);
					session.setMaxInactiveInterval(60*120);
				
					mav = new ModelAndView("redirect:/index.do");							
			}	
		}
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 로그아웃
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/logout.do", method=RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
			session.removeAttribute("fr_id");
			session.removeAttribute("fr_pwd");
			session.removeAttribute("fr_name");
			session.removeAttribute("fr_p_number");
			session.removeAttribute("fr_email");
			session.removeAttribute("fr_class");
			session.removeAttribute("isLogOn");
			
		ModelAndView mav = new ModelAndView("redirect:/index.do");
		
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// id pwd 찾기 페이지 폼 불러오기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/find.do", method=RequestMethod.GET)
	public ModelAndView findForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/user/findForm");
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// ID 찾기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@ResponseBody
	@RequestMapping(value="/findId.do", method=RequestMethod.POST)
	public UserVO findId(@RequestParam("fr_name") String fr_name, @RequestParam("fr_email") String fr_email, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println("controller id찾기 ==>" + fr_name + ", " + fr_email);
		UserVO user = new UserVO();
		user.setFr_name(fr_name);
		user.setFr_email(fr_email);
		
		userVO = userDAO.findId(user);		
		System.out.println("id찾기 결과 ==>" + userVO);
		
		return userVO;		
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// ID 중복 체크
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@ResponseBody
	@RequestMapping(value="/checkId.do", method=RequestMethod.POST)
	public int checkId(UserVO userVO) throws Exception {
		
		System.out.println("controller checkId ==>" + userVO);
		int result = userDAO.checkId(userVO);
		
		return result;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// PWD 찾기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@ResponseBody
	@RequestMapping(value="/findPwd.do", method=RequestMethod.POST)
	public UserVO findPwd(@RequestParam("fr_id") String fr_id, @RequestParam("fr_email") String fr_email, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		System.out.println("controller pwd찾기 ==>" + fr_id + ", " + fr_email);
		UserVO user = new UserVO();
		user.setFr_id(fr_id);
		user.setFr_email(fr_email);
		
		userVO = userDAO.findPwd(user);
		
		System.out.println("pwd찾기 결과 ==>" + userVO);
		
		return userVO;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 마이페이지 폼 불러오기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/myPage.do", method=RequestMethod.GET)
	public ModelAndView myPageForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/user/myPage");
		mav.addObject("userVO", userVO);
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 마이페이지 변경 폼 불러오기
	//-----------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/myPageUpdate.do", method=RequestMethod.GET)
	public ModelAndView myPageUpdateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/user/myPageUpdateForm");
		mav.addObject("userVO", userVO);
		return mav;
	}	
	
	//-----------------------------------------------------------------------------------------------------------
    // 마이페이지 변경하기
    //-----------------------------------------------------------------------------------------------------------
	@Override
    @RequestMapping(value="/myPageUpdateGo.do", method=RequestMethod.POST)
    public ModelAndView myPageUpdateGo(@ModelAttribute("userVO") UserVO userVO, HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    
       userDAO.myPageUpdateGo(userVO);

       HttpSession session = request.getSession();
      
       session.removeAttribute("fr_name");
       session.removeAttribute("fr_p_number");
       session.removeAttribute("fr_email");
       session.removeAttribute("fr_pwd");
      
       session.setAttribute("fr_name", userVO.getFr_name());
       session.setAttribute("fr_p_number", userVO.getFr_p_number());
       session.setAttribute("fr_email", userVO.getFr_email());
       session.setAttribute("fr_pwd", userVO.getFr_pwd());
      
       ModelAndView mav = new ModelAndView("redirect:myPage.do");
      
      
       return mav;
	}	
	
}