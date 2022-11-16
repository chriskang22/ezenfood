package vo;

import org.springframework.stereotype.Component;

@Component("selectVO")
public class SelectVO {
	
	private String findBigStore;		// 업체 검색 (대분류)
	private String findSmallStore;		// 업체 검색 (소분류)
	private String menu1;				// 메뉴1  검색
	private String menu2;				// 메뉴2  검색
	private String menu3;				// 메뉴3  검색
	private String menu4;				// 메뉴4  검색
	private String menu5;				// 메뉴5  검색
	private int	   personNo;			// 인원수 검색
	
	public SelectVO() {}
	
	public SelectVO(String findBigStore, String findSmallStore, String menu1, String menu2, String menu3, String menu4, String menu5, int personNO) {
		this.findBigStore 	= findBigStore;
		this.findSmallStore = findSmallStore;
		this.menu1			= menu1;
		this.menu2			= menu2;
		this.menu3			= menu3;
		this.menu4			= menu4;
		this.menu5			= menu5;
		this.personNo		= personNO;
	}

	public String getFindBigStore() {
		return findBigStore;
	}

	public void setFindBigStore(String findBigStore) {
		this.findBigStore = findBigStore;
	}

	public String getFindSmallStore() {
		return findSmallStore;
	}

	public void setFindSmallStore(String findSmallStore) {
		this.findSmallStore = findSmallStore;
	}

	public String getMenu1() {
		return menu1;
	}

	public void setMenu1(String menu1) {
		this.menu1 = menu1;
	}

	public String getMenu2() {
		return menu2;
	}

	public void setMenu2(String menu2) {
		this.menu2 = menu2;
	}

	public String getMenu3() {
		return menu3;
	}

	public void setMenu3(String menu3) {
		this.menu3 = menu3;
	}

	public String getMenu4() {
		return menu4;
	}

	public void setMenu4(String menu4) {
		this.menu4 = menu4;
	}

	public String getMenu5() {
		return menu5;
	}

	public void setMenu5(String menu5) {
		this.menu5 = menu5;
	}

	public int getPersonNo() {
		return personNo;
	}

	public void setPersonNo(int personNo) {
		this.personNo = personNo;
	}
	
	@Override
	public String toString() {
		return "SelectVO [findBigStore=" + findBigStore + ", findSmallStore=" + findSmallStore + ", menu1=" + menu1
				+ ", menu2=" + menu2 + ", menu3=" + menu3 + ", menu4=" + menu4 + ", menu5=" + menu5 + ", personNo="
				+ personNo + "]";
	}
	
}
