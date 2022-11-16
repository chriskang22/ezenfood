package vo;

import org.springframework.stereotype.Component;

@Component("storeVO")
public class StoreVO {
	
	private int    fr_no;				// 업체 번호
	private String fr_id;				// 아이디
	private String fr_store_name;		// 업체 명
	private String fr_address;			// 업체 주소
	private String fr_store_p_number;	// 업체 연락처
	private String fr_menu;				// 업체 주 메뉴
	private String fr_store_ox;			// 업체 승인여부
	
	public StoreVO() {}
	
	public StoreVO(int fr_no, String fr_id, String fr_store_name, String fr_address, String fr_store_p_number, String fr_menu, String fr_store_ox) {
		this.fr_no				= fr_no;
		this.fr_id				= fr_id;
		this.fr_store_name		= fr_store_name;
		this.fr_address			= fr_address;
		this.fr_store_p_number	= fr_store_p_number;
		this.fr_menu			= fr_menu;
		this.fr_store_ox		= fr_store_ox;
	}

	public int getFr_no() {
		return fr_no;
	}

	public void setFr_no(int fr_no) {
		this.fr_no = fr_no;
	}

	public String getFr_id() {
		return fr_id;
	}

	public void setFr_id(String fr_id) {
		this.fr_id = fr_id;
	}

	public String getFr_store_name() {
		return fr_store_name;
	}

	public void setFr_store_name(String fr_store_name) {
		this.fr_store_name = fr_store_name;
	}

	public String getFr_address() {
		return fr_address;
	}

	public void setFr_address(String fr_address) {
		this.fr_address = fr_address;
	}

	public String getFr_store_p_number() {
		return fr_store_p_number;
	}

	public void setFr_store_p_number(String fr_store_p_number) {
		this.fr_store_p_number = fr_store_p_number;
	}

	public String getFr_menu() {
		return fr_menu;
	}

	public void setFr_menu(String fr_menu) {
		this.fr_menu = fr_menu;
	}

	public String getFr_store_ox() {
		return fr_store_ox;
	}

	public void setFr_store_ox(String fr_store_ox) {
		this.fr_store_ox = fr_store_ox;
	}

	@Override
	public String toString() {
		return "StoreVO [fr_no=" + fr_no + ", fr_id=" + fr_id + ", fr_store_name=" + fr_store_name + ", fr_address="
				+ fr_address + ", fr_store_p_number=" + fr_store_p_number + ", fr_menu=" + fr_menu + ", fr_store_ox="
				+ fr_store_ox + "]";
	}
	

}
