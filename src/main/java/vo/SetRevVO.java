package vo;

import org.springframework.stereotype.Component;

	@Component("SetRevVO")
	public class SetRevVO {
		
		private int fr_no;						// 룸 번호
		private int fr_room_no;
		private String fr_reservation_date;			// 예약일자
		private String fr_room_ox;			// 예약여부
		
		public SetRevVO() {}
		
		public SetRevVO(int fr_no, int fr_room_no, String fr_reservation_date,
								String fr_room_ox) {
			this.fr_no					= fr_no;
			this.fr_room_no				= fr_room_no;
			this.fr_reservation_date	= fr_reservation_date;
			this.fr_room_ox				= fr_room_ox;
		}

		public int getFr_no() {
			return fr_no;
		}

		public void setFr_no(int fr_no) {
			this.fr_no = fr_no;
		}

		public int getFr_room_no() {
			return fr_room_no;
		}

		public void setFr_room_no(int fr_room_no) {
			this.fr_room_no = fr_room_no;
		}

		public String getFr_reservation_date() {
			return fr_reservation_date;
		}

		public void setFr_reservation_date(String fr_reservation_date) {
			this.fr_reservation_date = fr_reservation_date;
		}

		public String getFr_room_ox() {
			return fr_room_ox;
		}

		public void setFr_room_ox(String fr_room_ox) {
			this.fr_room_ox = fr_room_ox;
		}

		@Override
		public String toString() {
			return "SetRevVO [fr_no=" + fr_no + ", fr_room_no=" + fr_room_no + ", fr_reservation_date="
					+ fr_reservation_date + ", fr_room_ox=" + fr_room_ox + "]";
		}
		
		
}
