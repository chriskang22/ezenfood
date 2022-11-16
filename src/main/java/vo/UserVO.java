package vo;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("userVO")
public class UserVO {
		
	private String fr_id;		// 아이디
	private String fr_pwd;		// 비밀번호
	private String fr_name;		// 이름
	private String fr_p_number;	// 연락처
	private String fr_email;	// 이메일
	private String fr_class;	// 등급
	private Date   fr_date;		// 가입일자
	
	public UserVO() {}
	
	public UserVO(String fr_id, String fr_pwd, String fr_name, String fr_p_number, String fr_email, String fr_class) {
		this.fr_id 			= fr_id;
		this.fr_pwd 		= fr_pwd;
		this.fr_name 		= fr_name;
		this.fr_p_number 	= fr_p_number;
		this.fr_email 		= fr_email;
		this.fr_class 		= fr_class;
	}


	public String getFr_id() {
		return fr_id;
	}

	public void setFr_id(String fr_id) {
		this.fr_id = fr_id;
	}

	public String getFr_pwd() {
		return fr_pwd;
	}

	public void setFr_pwd(String fr_pwd) {
		this.fr_pwd = fr_pwd;
	}

	public String getFr_name() {
		return fr_name;
	}

	public void setFr_name(String fr_name) {
		this.fr_name = fr_name;
	}

	public String getFr_p_number() {
		return fr_p_number;
	}

	public void setFr_p_number(String fr_p_number) {
		this.fr_p_number = fr_p_number;
	}

	public String getFr_email() {
		return fr_email;
	}

	public void setFr_email(String fr_email) {
		this.fr_email = fr_email;
	}

	public String getFr_class() {
		return fr_class;
	}

	public void setFr_class(String fr_class) {
		this.fr_class = fr_class;
	}

	public Date getFr_date() {
		return fr_date;
	}

	public void setFr_date(Date fr_date) {
		this.fr_date = fr_date;
	}

	@Override
	public String toString() {
		return "UserVO [fr_id=" + fr_id + ", fr_pwd=" + fr_pwd + ", fr_name=" + fr_name + ", fr_p_number=" + fr_p_number
				+ ", fr_email=" + fr_email + ", fr_class=" + fr_class + ", fr_date=" + fr_date + "]";
	}
	
	
}
