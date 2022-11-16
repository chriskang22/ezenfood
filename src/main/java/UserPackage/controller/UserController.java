package UserPackage.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import vo.UserVO;


public interface UserController {
	//-----------------------------------------------------------------------------------------------------------
	// 메인 페이지 불러오기 layout
	//-----------------------------------------------------------------------------------------------------------
	public String home(Locale locale, Model model);
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원가입 페이지 폼 불러오기
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView signUpForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원 가입 처리
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView addUser(@ModelAttribute("info") UserVO userVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 로그인 페이지 폼 불러오기
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 로그인 처리
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView login(@ModelAttribute("user") UserVO user,RedirectAttributes rAttr, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 로그아웃
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// id pwd 찾기 페이지 폼 불러오기
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView findForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// ID 찾기
	//-----------------------------------------------------------------------------------------------------------
	public UserVO findId(@RequestParam("fr_name") String fr_name, @RequestParam("fr_email") String fr_email, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// ID 중복 체크
	//-----------------------------------------------------------------------------------------------------------
	public int checkId(UserVO userVO) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// PWD 찾기
	//-----------------------------------------------------------------------------------------------------------
	public UserVO findPwd(@RequestParam("fr_id") String fr_id, @RequestParam("fr_email") String fr_email, HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 마이페이지 폼 불러오기
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView myPageForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
	// 마이페이지 변경 폼 불러오기
	//-----------------------------------------------------------------------------------------------------------
	public ModelAndView myPageUpdateForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//-----------------------------------------------------------------------------------------------------------
    // 마이페이지 변경하기
    //-----------------------------------------------------------------------------------------------------------
	public ModelAndView myPageUpdateGo(@ModelAttribute("userVO") UserVO userVO, HttpServletRequest request, HttpServletResponse response)
	          throws Exception;
	
	
	
}