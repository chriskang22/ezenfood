package UserPackage.DAO;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import vo.ChatVO;
import vo.UserVO;

@Repository
public class UserDAOImpl implements UserDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원 가입 처리
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int addUser(UserVO userVO) throws DataAccessException {
		System.out.println("테스트값 ==>" + userVO);
		int result = sqlSession.insert("mapper.mapper.addUser", userVO);
		System.out.println("결과값 ==>" + result);
		return result; 
	}

	//-----------------------------------------------------------------------------------------------------------
	// 로그인 처리
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public UserVO login(UserVO userVO) throws DataAccessException {
		System.out.println("service 로그인 ==>" + userVO);
		UserVO useVO = sqlSession.selectOne("mapper.mapper.login", userVO);
		System.out.println("dao 로그인 결과 ==>" + useVO);
		return useVO;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// ID 찾기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public UserVO findId(UserVO userVO) throws DataAccessException {
		System.out.println("dao id찾기 ==>" + userVO);
		UserVO useVO = sqlSession.selectOne("mapper.mapper.findId", userVO);
		System.out.println("dao id찾기 결과 ==>" + useVO);
		return useVO;
	}

	//-----------------------------------------------------------------------------------------------------------
	// ID 중복 체크
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int checkId(UserVO userVO) throws DataAccessException {
		return sqlSession.selectOne("mapper.mapper.checkId", userVO);
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// PWD 찾기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public UserVO findPwd(UserVO userVO) throws DataAccessException {
		System.out.println("dao pwd찾기 ==>" + userVO);
		UserVO useVO = sqlSession.selectOne("mapper.mapper.findPwd", userVO);
		System.out.println("dao pwd찾기 결과 ==>" + useVO);
		return useVO;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 마이페이지 변경하기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int myPageUpdateGo(UserVO userVO) throws DataAccessException {
		System.out.println("테스트값 ==>" + userVO);
		int result = sqlSession.update("mapper.mapper.myPageUpdateGo", userVO);
		System.out.println("결과값 ==>" + result);
		return result; 
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 채팅하기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public void chatInput(HashMap<String, Object> map) throws DataAccessException {
		
		sqlSession.insert("mapper.mapper.chatInput", map);

	}

	//-----------------------------------------------------------------------------------------------------------
	// 채팅 리스트 가져오기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<ChatVO> chatList(String enterDate) throws DataAccessException {
		List<ChatVO> result = sqlSession.selectList("mapper.mapper.chatList", enterDate);
		System.out.println("가져온 내용" + result);
		return result;
	}

	//-----------------------------------------------------------------------------------------------------------
	// 입장시 데이터 카운트
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int inCnt(String enterDate) throws DataAccessException {
		return sqlSession.selectOne("mapper.mapper.inCnt", enterDate);
	}

}
