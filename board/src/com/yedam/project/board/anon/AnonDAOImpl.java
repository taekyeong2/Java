package com.yedam.project.board.anon;


import java.util.ArrayList;
import java.util.List;

import com.yedam.project.board.common.DAO;

public class AnonDAOImpl extends DAO implements AnonDAO {

	// 싱글톤
	private static AnonDAO anonDAO = null;
	//다른곳에서 객체 생성 못하게
	private AnonDAOImpl() {
	}
	//최초 호출시 만들어진다.
	public static AnonDAO getInstance() {
		if (anonDAO == null) {
			anonDAO = new AnonDAOImpl();
		}
		return anonDAO;
	}

	// 게시글 전체출력
	@Override
	public List<AnonVO> selectAll() {
		//ArrayList로 anonVO 클래스 담을 리스트 
		List<AnonVO> list = new ArrayList<>();
		try {
			//연결
			connect();

			stmt = conn.createStatement();
			//anon테이블 전체 호출
			String sql = "SELECT * FROM anon";
			rs = stmt.executeQuery(sql);
			//rs 한줄씩 읽으면서 담는다.
			while (rs.next()) {
				AnonVO anonVO = new AnonVO();
				anonVO.setAnonNum(rs.getInt("a_num"));
				anonVO.setAnonName(rs.getString("a_name"));
				anonVO.setAnonTitle(rs.getString("a_title"));
				//리스트에 넣어줌
				list.add(anonVO);
			}
		} catch (Exception e) {  //오류시 메세지 
			e.printStackTrace();
		} finally {
			//연결끊음
			disconnect();
		}
		//리스트값 반환
		return list;
	}
	

	// 게시글 단건조회 - 게시글로 조회한다.
	@Override
	public AnonVO selectOne(int anonNum) {
		AnonVO anonVO = null;
		try {
			connect();

			//statement 사용
			stmt = conn.createStatement();
			//입력한 게시글 번호를 가진 anon테이블 필드 가져옴
			String sql = "SELECT * FROM anon WHERE a_num = " + anonNum;
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				//앞에서 선언한 anonVO 타입의 객체변수에 불러온 필드값 담는다.
			    anonVO = new AnonVO();
				anonVO.setAnonNum(rs.getInt("a_num"));
				anonVO.setAnonName(rs.getString("a_name"));
				anonVO.setAnonTitle(rs.getString("a_title"));
				anonVO.setAnonContent(rs.getString("a_content"));
				anonVO.setAnonPw(rs.getString("a_pw"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		//값 반환
		return anonVO;
	}


	// 게시글 작성
	@Override
	public void insert(AnonVO anonVO) {
		try {
			connect();
			//prepareStatement사용
			String sql = "INSERT INTO anon (a_name, a_pw, a_title, a_content) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, anonVO.getAnonName());
			pstmt.setString(2, anonVO.getAnonPw());
			pstmt.setString(3, anonVO.getAnonTitle());
			pstmt.setString(4, anonVO.getAnonContent());
			
			//작성된 필드? 수
			int result = pstmt.executeUpdate();
			if(result > 0) {
				//작성된 필드가 있다면
				System.out.println("정상적으로 등록되었습니다.\n");
			}else {
				//작성된 필드가 없다면
				System.out.println("정상적으로 등록되지 않았습니다.\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 게시글 수정 - 게시글번호와 내용만 매개변수
	@Override
	public void update(int anonNum, String anonContent) {
		try {
			connect();
			String sql = "UPDATE anon SET a_content = ? WHERE a_num = ?";
			pstmt = conn.prepareStatement(sql);
			//각각 위치에 맞게 매개변수로 받은 값 넣어준다.
			pstmt.setString(1, anonContent);
			pstmt.setInt(2, anonNum);
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("정상적으로 수정되었습니다.\n");
			}else {
				System.out.println("정상적으로 수정되지 않았습니다.\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	//게시글 삭제 - 게시글 번호로
	@Override
	public void delete(int anonNum) {
		try {
			connect();
			stmt = conn.createStatement();
			//넣어준 게시글 번호를 가진 모든 anon테이블 필드 지움
			String sql = "DELETE FROM anon WHERE a_num = "+ anonNum;
			
			int result = stmt.executeUpdate(sql);
			if(result > 0) {
				System.out.println("정상적으로 삭제되었습니다.\n");
			}else {
				System.out.println("정상적으로 삭제되지 않았습니다.\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}



}
