package AdminPackage.DAO;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import vo.RoomVO;
import vo.StoreVO;

//-----------------------------------------------------------------------------------------------------------
// public class AdminDAOImpl implements AdminDAO
//-----------------------------------------------------------------------------------------------------------
@Repository("adminDAO")
public class AdminDAOImpl implements AdminDAO{

	@Autowired
	private SqlSession sqlSession;
	
	//-----------------------------------------------------------------------------------------------------------
    // 승인 요청 List 페이지 폼
    //-----------------------------------------------------------------------------------------------------------
	@Override
	public List<StoreVO> approveList() throws DataAccessException {
		
		List<StoreVO> approveList = sqlSession.selectList("mapper.store.approveList");		
		System.out.println("결과값 ==>" + approveList);
		return approveList; 			
	}

	//-----------------------------------------------------------------------------------------------------------
	// 미등록 업체 승인하기 
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int approveGo(StoreVO storeVO) throws DataAccessException {
		System.out.println("미등록 업체 승인 테스트값 ==> " + storeVO);
		
		return sqlSession.update("mapper.store.approveGo", storeVO);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 업체 승인시 fr_class를 12로 변경 
	//-----------------------------------------------------------------------------------------------------------	
	@Override
	public int approveOwnerGo(StoreVO storeVO) throws DataAccessException {
		System.out.println("승인 시 fr_class 변경 테스트값 ==> " + storeVO);
		
		return sqlSession.update("mapper.store.approveOwnerGo", storeVO);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 승인된 업체 관리 List 페이지 폼
	//-----------------------------------------------------------------------------------------------------------	
	@Override
	public List<StoreVO> approveOkList(HashMap<String, Object> findValue) throws DataAccessException {
		
		return sqlSession.selectList("mapper.store.approveOkList", findValue);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 룸 정보 List 페이지 폼
	//-----------------------------------------------------------------------------------------------------------	
	@Override
	public List<String> roomList(RoomVO roomVO) throws DataAccessException {
		List<String> roomList = sqlSession.selectList("mapper.room.roomList", roomVO);	
		System.out.println("찾아온 룸 정보" + roomList);
		return roomList;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 승인된 업체의 갯수 카운트하기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int fr_no_Count(HashMap<String, Object> pageObject) throws DataAccessException {
		return sqlSession.selectOne("mapper.store.fr_no_Count", pageObject);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 룸 정보 추가
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int addRoomImage(RoomVO roomVO) throws DataAccessException {
		System.out.println("freeBoardVO DAO ==> " + roomVO);
		int result = sqlSession.insert("mapper.room.addRoomImage", roomVO);
		return result;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 룸 삭제하기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int roomDelete(@RequestParam("fr_room_no") int fr_room_no) throws DataAccessException {
		int result = sqlSession.delete("mapper.room.roomDelete", fr_room_no);
		return result;
	}
	
}// End - public class AdminDAOImpl implements AdminDAO
