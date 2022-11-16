package MemberPackage.DAO;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import vo.Criteria;
import vo.ReservationVO;
import vo.RoomVO;
import vo.SelectVO;
import vo.StoreVO;

//-----------------------------------------------------------------------------------------------------------
// public class MemberDAOImpl implements MemberDAO
//-----------------------------------------------------------------------------------------------------------
@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
	
	@Autowired
	private SqlSession sqlSession;

	//-----------------------------------------------------------------------------------------------------------
	// 이용 가능한 업체 리스트 총 갯수(conunt) 구하기(totalCount) - 페이징 처리 -
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int countList() throws DataAccessException {
		return sqlSession.selectOne("mapper.store.countList");
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 이용 가능한 업체 리스트 페이지
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<StoreVO> storeViewList(Criteria cri) throws DataAccessException {
		List<StoreVO> storeViewList = sqlSession.selectList("mapper.store.StoreList", cri);
		return storeViewList;
	}

	//-----------------------------------------------------------------------------------------------------------
	// 이용 가능한 업체 지역별 리스트 갯수(conunt) 구하기(totalCount) - 페이징 처리 -
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int countList2(SelectVO selectVO) throws DataAccessException {
		return sqlSession.selectOne("mapper.store.countList2", selectVO);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 이용 가능한 업체 지역별 검색
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<StoreVO> findStoreList(HashMap<String, Object> map) throws DataAccessException {
		System.out.println("findStoreList DAO map 가져온 값 => "+ map);
		List<StoreVO> FindStore = sqlSession.selectList("mapper.store.findStoreList", map);			
		return FindStore;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 지역검색 이후 메뉴 및 인원으로 검색 한 업체 리스트 갯수(conunt) 구하기(totalCount) - 페이징 처리 -
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int countList3(SelectVO selectVO) throws DataAccessException {
		return sqlSession.selectOne("mapper.store.countList3", selectVO);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 지역검색 이후 메뉴 및 인원으로 인원 검색
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<StoreVO> findStoreList2(HashMap<String, Object> map) throws DataAccessException {
		List<StoreVO> FindStore2 = sqlSession.selectList("mapper.store.findStoreList2", map);
		return FindStore2;
	}

	//-----------------------------------------------------------------------------------------------------------
	// 선택한 업체의 예약 가능한 방 리스트 페이지
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<RoomVO> selectStoreRoomList(HashMap<Object, Object> map) throws DataAccessException {
		List<RoomVO> selectRoomList = sqlSession.selectList("mapper.room.selectRoomList", map);
		return selectRoomList;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 선택한 업체의 예약 완료된 방 리스트 페이지
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<RoomVO> completionRoomList(HashMap<Object, Object> map) throws DataAccessException {
		List<RoomVO> completionRoomList = sqlSession.selectList("mapper.room.completionRoomList", map);
		return completionRoomList;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 선택한 날짜와 업체의 예약 가능한 방 리스트 페이지
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<RoomVO> selectDateRoomList(HashMap<Object, Object> map) throws DataAccessException {
		List<RoomVO> selectDateRoomList = sqlSession.selectList("mapper.room.selectDateRoomList", map);
		return selectDateRoomList;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 선택한 날짜와 업체의 예약 완료된 방 리스트 페이지
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<RoomVO> completionDateRoomList(HashMap<Object, Object> map) throws DataAccessException {
		List<RoomVO> completionDateRoomList = sqlSession.selectList("mapper.room.completionDateRoomList", map);
		return completionDateRoomList;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 해당 업체의 휴무일 여부 판단하기.
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int restCheck(HashMap<Object, Object> map) throws DataAccessException {	
		return sqlSession.selectOne("mapper.room.restCheck", map);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원 온라인 예약하기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int onlineReservation(ReservationVO reservationVO) throws DataAccessException {	
		return sqlSession.insert("mapper.room.onlineReservation", reservationVO);
	}
	//-----------------------------------------------------------------------------------------------------------
	// 회원 예약내역 확인하기 페이지
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<HashMap<String, Object>> myReservation(HashMap<Object, Object> map) throws DataAccessException {
		List<HashMap<String, Object>> myReservation = sqlSession.selectList("mapper.room.myReservation", map);
		return myReservation;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원 예약 취소하기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int myReservationCancle(HashMap<String, Object> map) throws DataAccessException {	
		return sqlSession.delete("mapper.room.myReservationCancle", map);
	}
	
}// End - public class MemberDAOImpl implements MemberDAO
