package vo;

import org.springframework.stereotype.Component;

@Component("ReservationVO")
public class ReservationVO {
	
	private int fr_room_no;						// 룸 번호
	private String fr_name;						// 이름(예약자)
	private String fr_p_number;					// 연락처(예약자)
	private String fr_reservation_person_no;	// 예약인원
	private String fr_reservation_date;			// 예약일자
	private String fr_reservation_ox;			// 예약여부
	
	public ReservationVO() {}
	
	public ReservationVO(int fr_room_no, String fr_name, String fr_p_number, String fr_reservation_person_no,
							String fr_reservation_date, String fr_reservation_ox) {
		this.fr_room_no					= fr_room_no;
		this.fr_name					= fr_name;
		this.fr_p_number				= fr_p_number;
		this.fr_reservation_person_no	= fr_reservation_person_no;
		this.fr_reservation_date		= fr_reservation_date;
		this.fr_reservation_ox			= fr_reservation_ox;
	}

	public int getFr_room_no() {
		return fr_room_no;
	}

	public void setFr_room_no(int fr_room_no) {
		this.fr_room_no = fr_room_no;
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

	public String getFr_reservation_person_no() {
		return fr_reservation_person_no;
	}

	public void setFr_reservation_person_no(String fr_reservation_person_no) {
		this.fr_reservation_person_no = fr_reservation_person_no;
	}

	public String getFr_reservation_date() {
		return fr_reservation_date;
	}

	public void setFr_reservation_date(String fr_reservation_date) {
		this.fr_reservation_date = fr_reservation_date;
	}

	public String getFr_reservation_ox() {
		return fr_reservation_ox;
	}

	public void setFr_reservation_ox(String fr_reservation_ox) {
		this.fr_reservation_ox = fr_reservation_ox;
	}

	@Override
	public String toString() {
		return "ReservationVO [fr_room_no=" + fr_room_no + ", fr_name=" + fr_name + ", fr_p_number=" + fr_p_number
				+ ", fr_reservation_person_no=" + fr_reservation_person_no + ", fr_reservation_date="
				+ fr_reservation_date + ", fr_reservation_ox=" + fr_reservation_ox + "]";
	}



	
	
}