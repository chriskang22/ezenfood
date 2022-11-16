package vo;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("chatVO")
public class ChatVO {
	
	private int no;
	private String fr_id;	
	private Date date;			
	private String word;	
	
	public ChatVO() {}
	
	public ChatVO(int no, String fr_id, Date date, String word) {
		this.no					= no;
		this.fr_id					= fr_id;
		this.date					= date;
		this.word				= word;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getFr_id() {
		return fr_id;
	}

	public void setFr_id(String fr_id) {
		this.fr_id = fr_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public String toString() {
		return "ChatVO [no=" + no + ", fr_id=" + fr_id + ", date=" + date + ", word=" + word + "]";
	}

}
