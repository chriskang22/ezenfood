package vo;

public class Criteria {

	private int page; 		// 현재 페이지 번호
	private int perPageNum; // 한 페이지당 보여줄 리스트글의 갯수
	
	public int getPageStart() { // 특정 페이지의 리스트글 시작번호, 리스트글 시작 행 번호
		return(this.page-1)*perPageNum;
	}
	
	// 기본 생성자
	// 최초로 리스트 목록에 들어오게 되면 현재 페이지 번호와 한 페이지당 보여줄 보여줄 리스트 글이
	// 필수인데 가져올 방법이 없으므로 초기값을 설정해 두어야 한다.
	// 1페이지에 페이지당 7개의 글을 보여준다.
	public Criteria() {
		this.page = 1;
		this.perPageNum = 7;
	}
	
	public int getPage() {
		return page;
	}
	
	// 잘못된 값들이 셋팅되지 않게 적절하게 set메서드 셋팅
	// 페이지가 음수값이 되지 않게 설정, 음수가 되면 1페이지를 나타낸다.
	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1;
		}else {
			this.page = page;
		}
	}
	
	public int getPerPageNum() {
		return perPageNum;
	}
	
	// 페이지당 보여줄 리스트글의 수가 변하지 않게 설정한다.
	public void setPerPageNum(int pageCount) {
		int cnt = this.perPageNum;
		if(pageCount != cnt) {
			this.perPageNum = cnt;
		}else {
			this.perPageNum = pageCount;
		}
	}
	
}
