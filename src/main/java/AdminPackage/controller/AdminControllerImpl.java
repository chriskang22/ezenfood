package AdminPackage.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import AdminPackage.DAO.AdminDAO;
import vo.RoomVO;
import vo.StoreVO;

//-----------------------------------------------------------------------------------------------------------
// public class AdminControllerImpl implements AdminController
//-----------------------------------------------------------------------------------------------------------
@Controller("adminController")
public class AdminControllerImpl implements AdminController{
	
	@Autowired
	private AdminDAO adminDAO;
	private static String FOODROOM_IMAGE_REPO = "C:\\data\\room_image";
	
	//-----------------------------------------------------------------------------------------------------------
	// 승인 요청 List 페이지 폼
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/approveForm.do", method=RequestMethod.GET)
	public ModelAndView approveForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<StoreVO> approve = adminDAO.approveList();	// 승인 요청 List로 보여주기	
		
		ModelAndView mav = new ModelAndView("/admin/approveForm");
		mav.addObject("approve", approve);
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 승인하기 (미등록 업체 승인하기, 업체 승인시 fr_class를 12로 변경)
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/approve.do", method=RequestMethod.GET)
	public ModelAndView approveGo(@ModelAttribute("storeVO") StoreVO storeVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		adminDAO.approveGo(storeVO);
		adminDAO.approveOwnerGo(storeVO);
		ModelAndView mav = new ModelAndView("redirect:/approveForm.do");
		
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 승인된 업체 관리 List 페이지 폼
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/manageFoodRoom.do", method=RequestMethod.GET)
	public ModelAndView manageFoodRoom(@RequestParam(value="page", required = false) String page, @RequestParam(value="selectText", required = false) String selectText, 
			@RequestParam(value="selectChk", required = false) String selectChk, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<StoreVO> approveOk = new ArrayList<> ();

		// ★★★★★★★★★★★★★★★★★★★★ 페이징 기능 시작
		// 변수 선언 및 초기화	
		int PagingCnt = 7; 												// 한 페이지에 보여줄 데이터 갯수
		int pagingNum = 10; 											// 하단 순번에 보여지게 될 페이지 선택 가능 갯수
		int startCnt = 0; 												// 게시글 처음에 보여줄 index 넘버
		int getPage = 0; 												// 파라메터로 넘어올 현재 페이지 넘버를 담을 변수
		int startPage = 1; 												// 하단 순번에서 시작할 페이지 번호
		int endPage = 0; 												// 하단 순번에서 마지막에 보여줄 페이지 번호
		int fullPage = 0; 												// 총 생성해야 할 페이지 갯수를 구한다.
		int fullIndex = 0; 												// 전체 데이터 갯수를 담을 변수.
		
		HashMap<String, Object> findValue = new HashMap<String, Object>();	// 맵을 생성해서
		findValue.put("selectChk", selectChk);							// 셀렉 체크박스 선택값을 담고
		findValue.put("selectText", selectText);						// 입력된 텍스트 값도 담고
		
		fullIndex = adminDAO.fr_no_Count(findValue);					// 보여지는 데이터의 총 갯수를 쿼리문으로 구한다.
		
		// 필요한 총 페이지 갯수 구하기
		if(fullIndex%PagingCnt==0) {									// 총 데이터 수가 페이지당 보여줄 갯수와 딱 나누어 떨어지면
			fullPage	=	fullIndex/PagingCnt;						// 나누어진 몫을 총 페이지 갯수로 반환하여 총 페이지 갯수로 반환하고
		} else {														// 총 데이터 수가 페이지당 보여줄 갯수로 나누어 나머지가 남는 경우
			fullPage	=	fullIndex/PagingCnt+1;						// 나누어진 몫에 나머지 데이터를 보여줄 페이지를 하나 더하여 총 페이지 갯수로 반환한다.
		}
	
		// 게시글 처음에 시작할 index 넘버를 구하기
		if(page==null) { 												// 만약 넘어온 페이지 값이 없는 경우
			startCnt = 0; 												// 시작할 index넘버를 0으로
		}else {															// 넘어온 페이지 값이 있는 경우
			getPage = (Integer.valueOf(page));							// 받아온 페이지 넘버를 인트 타입으로 변경하고
			if(getPage<2) {												// 받아온 페이지 값이 2 보다 작은 경우 (즉, 1페이지 인 경우//  행여 말도 안되는 -1-2-3 이딴 페이지넘버가 넘어온다고 하더라도)
				startCnt = 0;											// 시작할 데이터 인덱스를 0으로 고정한다.
			} else if(getPage>=fullPage) {								// 받아온 페이지 값이 2 이상인데 총 페이지와 같거나 더 크다면,
				startCnt = ((fullPage-1)*PagingCnt);					// 총 페이지 숫자에서 -1* 보여줄 갯수를 게시글 시작 index 넘버로 반환한다.
			} else {													// 받아온 페이지 값이 2 이상이고, 총 페이지 갯수보다 작은 경우
				startCnt = ((getPage-1)*PagingCnt);						// 페이지 번호-1 * 보여줄 갯수를 게시글 시작 index 넘버로 반환한다.
			}
		}
		// 페이징 처러시 하단에 시작할 페이지 번호와, 마지막에 보내줄 페이지 번호를 구한다.
		if(fullPage<=pagingNum) {										// 총 페이지 갯수가 페이징 처리할 갯수보다 적은 경우
			endPage = fullPage;											// 보여지는 마지막 페이지를 총 페이지 갯수로 한다.
		}else {															// 총 페이지 갯수가 페이징 처리할 갯수보다 많고,
			if(getPage<=pagingNum) {									// 현재 페이지가 보여지는 페이지 출력 갯수보다 적은 경우에는
				endPage = pagingNum;									// 보여지는 마지막 페이지를 페이지 출력 갯수로 한다. (즉, 1~pagingNum 까지 하단에 표기하기 위함)
			}else {														// 현재 페이지가 보여지는 페이지 출력 갯수보다 큰 경우
				startPage = ((getPage-1)/pagingNum)*pagingNum+1;		// 보여지는 시작 페이지를 (현재 페이지-1 / 출력갯수) * 출력갯수 +1로 한다. (즉, 20page-1= 19page / 10page = 몫 1을 반환하고, 1*10에 1을 더하여, 11page를 시작페이지로 한다)
				endPage = startPage+pagingNum-1;						// 보여지는 마지막 페이지는 바로 위에서 구한 시작페이지에 출력 페이지 갯수, 1을 더한다. (즉, 11page+10 -1로, 20page 까지를 마지막 페이지로 한다.)
				if(endPage>=fullPage) {									// 이 때, 마지막 페이지 번호가 데이터 총 페이지 번호보다 크거나 같아지는 경우에는
				endPage = fullPage;										// 보여지는 마지막 페이지를 총 페이지 갯수로 한다. (즉, 총 32페이지 데이터가 나온 경우 31페이지에 있다면, 40번까지 보여줄 수 없으니 32번까지 보여주도록 강제한다)
				}
			}
		}
		
		// 그 다음 최종적으로 확정된 스타트 index와, 한 페이지에 보여줄 갯수를 map에 담는다.
		findValue.put("startCnt", startCnt);							// 시작 인덱스 넘버와
		findValue.put("PagingCnt", PagingCnt);							// 보여질 인덱스 갯수를 마저 맵에 담아서
		// ★★★★★★★★★★★★★★★★★★★★ 페이징 기능 끝
		approveOk = adminDAO.approveOkList(findValue);					// 쿼리문에 날린다.

		ModelAndView mav = new ModelAndView("/admin/manageFoodRoom");	// 보여줄 jsp 파일을 특정한다.
		mav.addObject("approveOk", approveOk);							// 그렇게 불러온 리스트를 오브젝트에 추가한다.
		mav.addObject("startPage", startPage);							// 시작 페이지와
		mav.addObject("endPage", endPage);								// 마지막 페이지를 오브젝트로 넘긴다.
		mav.addObject("fullPage", fullPage);							// 생성할 총 페이지 갯수를 넘긴다.
		mav.addObject("selectChk", selectChk);
		mav.addObject("selectText", selectText);
		
		return mav;
	}
	
	
	//-----------------------------------------------------------------------------------------------------------
	// 룸 정보 List 페이지 폼
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/lookRoomList.do", method=RequestMethod.GET)
	public ModelAndView lookRoomList(@ModelAttribute("roomVO") RoomVO roomVO,@RequestParam("fr_no") int fr_no, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<String> roomList = adminDAO.roomList(roomVO);	// 룸 정보 List로 보여주기	
		
		ModelAndView mav = new ModelAndView("/admin/lookRoomList");
		mav.addObject("roomList", roomList);
		mav.addObject("room_no", fr_no);
		
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 룸추가 페이지 폼
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/addRoomInfo.do", method=RequestMethod.GET)
	public ModelAndView addRoomInfo(@RequestParam("fr_no") int fr_no, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/addRoomInfo");
		mav.addObject("fr_no", fr_no);
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 룸정보 추가
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/addRoomInfoGo.do", method=RequestMethod.POST)
	public ModelAndView addRoomInfo(@ModelAttribute("AdminVO") RoomVO roomVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
      
		System.out.println("addPosts ==>" + roomVO);

		ModelAndView mav = new ModelAndView();
      
		Map<String, String> roomImageMap   = uploadRoomImage(request, response);

		System.out.println("Map ==> " + roomImageMap);
		roomVO.setFr_no(Integer.parseInt(roomImageMap.get("fr_no")));
		roomVO.setFr_room_name(roomImageMap.get("fr_room_name"));
		roomVO.setFr_room_person_no(roomImageMap.get("fr_room_person_no"));
		roomVO.setFr_room_image(roomImageMap.get("fr_room_image"));
		System.out.println("adminVO ==> " + roomVO);
      
		int result = adminDAO.addRoomImage(roomVO);
		System.out.println("게시글 추가 controller 결과 freeboard_no ==> " + result);
      

		if(roomImageMap.get("fr_room_image") != null && roomImageMap.get("fr_room_image").length() != 0) { // 둘 중 하나가 작동 하지 않을 때가 있다.
			File srcFile = new File(FOODROOM_IMAGE_REPO + "\\" + roomImageMap.get("fr_room_image")); // 이미지 파일을 저장한 경로
			File destDir = new File(FOODROOM_IMAGE_REPO + "\\" + roomVO.getFr_no());   // 해당 경로 + fr_no(업체번호) 폴더를 생성
         
			destDir.mkdirs();
			FileUtils.moveFileToDirectory(srcFile, destDir, true); // 해당 경로에 저장된 이미지 파일을 fr_no명으로 생성된 폴더로 이동시킨다.
		}

		mav = new ModelAndView("redirect:/lookRoomList.do?fr_no="+roomVO.getFr_no());
   
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 룸정보 이미지 첨부 메서드
	//-----------------------------------------------------------------------------------------------------------
	private Map<String, String> uploadRoomImage(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
      
		Map<String, String> roomImageMap = new HashMap<String, String>();
		String encoding   =   "utf-8";
      
		// 업로드할 파일의 경로를 지정한다.
		File            currentDirPath      = new File(FOODROOM_IMAGE_REPO);
      
		DiskFileItemFactory   factory            = new DiskFileItemFactory();
      
		// 파일 경로를 설정한다.
		factory.setRepository(currentDirPath);
      
		// 업로드될 파일의 최대 크기를 설정한다.
		factory.setSizeThreshold(1024*1024*1024);
      
		ServletFileUpload uploadRoomImage = new ServletFileUpload(factory);
      
		try {
			// request객체에서 매개 변수를 List로 가져온다.
			List items = uploadRoomImage.parseRequest(request);
         
			for(int i = 0; i < items.size(); i++) {
				//   파일 업로드 창에서 업로드된 항목들을 하나씩 가져와서 작업을 한다.
				FileItem fileItem = (FileItem) items.get(i);
            
				// 폼 필드이면 전송된 매개 변수의 값을 출력한다.
				if(fileItem.isFormField()) {
					roomImageMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
				} else { // 폼 필드가 아니면 파일 업로드 기능을 실행한다.
					// 업로드한 파일의 이름을 가져온다.
					// 파일의 사이즈가 0보다 큰 경우만 업로드를 한다.
					if(fileItem.getSize() > 0) {
						// 변수.lastIndexOf(검색값) => 변수에서 검색값 들 중에서 마지막 것을 말한다.
						int idx = fileItem.getName().lastIndexOf("\\");
						if(idx == -1) { // 이런(\\) 경로가 아니라면 / 경로의 마지막에서 파일이름을 찾는다.
							idx = fileItem.getName().lastIndexOf("/");
						}
                  
						// 경로에서 파일 이름을 추출한다.
						// "ABCDEFGHIJ"
						// substring(4) = > 인덱스번호 4이상 모든 값 => EFGHIJ
						// substring(3, 7) => 인덱스번호 3번 부터 7번 전까지 => DEFG
						// String fileName = fileItem.getName().substring(idx+1);
                  
						// 파일 이름을 room_img로 통일한다. (추 후 수정편의성)
						String reName = roomImageMap.get("fr_no")+"-"+roomImageMap.get("fr_room_name");
                  
						// 파일 고유 네임값을 불러온 뒤
						String f_Name = fileItem.getName();
						// 파일 확장자를 분리한다.
						String ext = f_Name.substring(f_Name.lastIndexOf(".") + 1);
						// 저장할 파일명을 reName과 확장자의 조합으로 명명한다.
						String fileName = reName+"."+ext;
                  
                  
						// 업로드한 파일의 이름으로 저장소(currentDirPath)에 파일을 업로드 한다.
						// File uploadFile = new File(currentDirPath + "\\" + fileName);
						// 파일이름이 중복되면 마지막에 올린 파일만 존재하게 되므로 임시 파일에 저장시키고,
						// 책 번호를 부여받게 되면 책 번호의 폴더를 만들어서 저장시키도록 한다.
						// upload()를 호출한 곳으로 bookInfoMap에 파일 정보를 넣어준다.
						roomImageMap.put(fileItem.getFieldName(), fileName);
                  
						File uploadFile = new File(currentDirPath + "\\" + fileName);
						fileItem.write(uploadFile);
					}
               
				} // End - if
            
			} // End - for
         
		} catch (Exception e) {
			e.printStackTrace();
		}
      
		return roomImageMap;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 룸 삭제하기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/roomDelete.do", method=RequestMethod.GET)
	public ModelAndView roomDelete(@RequestParam("fr_room_no") int fr_room_no, @RequestParam("fr_no") int fr_no, @RequestParam("fr_room_name") String fr_room_name,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("화면에서 가져온 fr_room_no ==> " + fr_room_no);
		System.out.println("화면에서 가져온 fr_no ==> " + fr_no);
		System.out.println("화면에서 가져온 fr_room_no ==> " + fr_room_name);
		
		// 룸 이미지의 실제 디렉토리 삭제하기
		try {
			String path = "C:\\data\\room_image\\" + fr_no + "\\" + fr_no + "-" + fr_room_name + ".jpg";  // 디렉토리가 저장되어있는 경로 + 저장된 이미지의 이름
			File file = new File(path);
			
			if(file.delete()) {
				System.out.println("파일을 삭제하였습니다.");
			} else {
				System.out.println("파일 삭제에 실패하였습니다.");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// DB에서 룸 정보 삭제하기
		int result = adminDAO.roomDelete(fr_room_no);
		System.out.println("result 성공여부 ==> " + result);
		
		ModelAndView mav = new ModelAndView("redirect:/lookRoomList.do");
		mav.addObject("fr_no", fr_no);
		return mav;
	}


}// End - public class AdminControllerImpl implements AdminController
