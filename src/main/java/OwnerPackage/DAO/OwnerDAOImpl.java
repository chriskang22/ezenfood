package OwnerPackage.DAO;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import vo.ReservationVO;
import vo.RoomVO;
import vo.SetRevVO;
import vo.StoreVO;

//-----------------------------------------------------------------------------------------------------------
// public class OwnerDAOImpl implements OwnerDAO 
//-----------------------------------------------------------------------------------------------------------
@Repository("ownerDAO")
public class OwnerDAOImpl implements OwnerDAO {
	
	@Autowired
	private SqlSession sqlSession;

	//-----------------------------------------------------------------------------------------------------------
	// 업체정보 등록 페이지 폼 (업체 중복 등록 방지)
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int regiForm(String sfr_id) throws DataAccessException {
		return sqlSession.selectOne("mapper.store.regiForm", sfr_id);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 업체명 중복 체크
	//-----------------------------------------------------------------------------------------------------------	
	@Override
	public int checkStore(String fr_store_name) throws DataAccessException {
		return sqlSession.selectOne("mapper.store.checkStore", fr_store_name);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 업체 등록 처리
	//-----------------------------------------------------------------------------------------------------------	
	@Override
	public int addregi(StoreVO storeVO) throws DataAccessException {	
		return sqlSession.insert("mapper.store.addregi", storeVO);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 업체 예약관리 달력 페이지 (달력 불러올 때 예약 리스트 가져오기)
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<ReservationVO> revAllList(int fr_no) throws DataAccessException {
		// System.out.println("달력 페이지 ajax DAO ==> " + fr_no);
		List<ReservationVO> revAllList = sqlSession.selectList("mapper.room.revAllLists", fr_no);
		// System.out.println("달력 페이지 ajax DAO List ==> " + revAllList);
		return revAllList;
	}

	//-----------------------------------------------------------------------------------------------------------
	// 달력 불러올 때 휴일 여부 리스트
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<SetRevVO> SetRevList(int fr_no) throws DataAccessException {
		List<SetRevVO> SetRevList = sqlSession.selectList("mapper.room.SetRevList", fr_no);
		return SetRevList;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 로그인한 id와 일치하는 fr_no 찾기
	//-----------------------------------------------------------------------------------------------------------	
	@Override
	public int findFr_no(String fr_id) throws DataAccessException {
		return sqlSession.selectOne("mapper.store.findFr_no", fr_id);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 해당 날짜에 해당 업체번호로 예약되어 있는 룸 목록 찾아오기
	//-----------------------------------------------------------------------------------------------------------	
	@Override
	public List<RoomVO> revRoomList(HashMap<String, Object> map) throws DataAccessException {
		System.out.println("map ==>" + map);
		List<RoomVO> revRoomList = sqlSession.selectList("mapper.room.revRoomList", map);
		System.out.println("revRoomList ==>" + revRoomList);
		
		return revRoomList;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 해당 날짜에 예약 가능한 룸 목록 찾아오기
	//-----------------------------------------------------------------------------------------------------------	
	@Override
	public List<RoomVO> frRoomNoList(HashMap<String, Object> map) throws DataAccessException {
		List<RoomVO> frRoomNoList = sqlSession.selectList("mapper.room.frRoomNoList", map);	
		return frRoomNoList;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 오프라인 예약 처리하기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int ownerRevOk(HashMap<String, Object> map) throws DataAccessException{
		System.out.println("넘겨받은 룸 번호, 날짜 ==> " + map);
		return sqlSession.insert("mapper.room.ownerRevOk", map);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 예약 취소하기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int cancleRev(HashMap<String, Object> map) throws DataAccessException{
		System.out.println("삭제할 룸 번호, 날짜 ==> " + map);	
		return sqlSession.delete("mapper.room.cancleRev", map);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 예약한 회원의 정보
	//-----------------------------------------------------------------------------------------------------------	
	@Override
	public List<ReservationVO> revUserList(HashMap<String, Object> map) throws DataAccessException {
		System.out.println("예약한 룸 조회용 번호, 날짜 ==> " + map);
		List<ReservationVO> revUserList = sqlSession.selectList("mapper.room.revUserList", map);	
		System.out.println("예약한 룸 조회 결과 ==> " + revUserList);
		return revUserList;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 업체 휴일 지정
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int restDay(HashMap<String, Object> map) throws DataAccessException{
		System.out.println("넘겨받은 업체번호, 날짜 ==> " + map);
		return sqlSession.insert("mapper.room.restDay", map);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 업체 휴일 취소
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int restDeleteDay(HashMap<String, Object> map) throws DataAccessException{
		System.out.println("취소할 업체 번호, 날짜 ==> " + map);
		return sqlSession.delete("mapper.room.restDeleteDay", map);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 예약예부 체크 카운트
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int revCnt(HashMap<String, Object> map) throws DataAccessException{
		System.out.println("넘겨받은 업체번호, 날짜 ==> " + map);
		return sqlSession.selectOne("mapper.room.revCnt", map);
	}
	

}// End - public class OwnerDAOImpl implements OwnerDAO 
