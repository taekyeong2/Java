package com.yedam.project.board.anon;


import java.util.ArrayList;
import java.util.List;

import com.yedam.project.board.common.DAO;

public class AnonDAOImpl extends DAO implements AnonDAO {

	// 싱글톤
	private static AnonDAO anonDAO = null;

	private AnonDAOImpl() {
	}

	public static AnonDAO getInstance() {
		if (anonDAO == null) {
			anonDAO = new AnonDAOImpl();
		}
		return anonDAO;
	}

	// 게시글 전체출력
	@Override
	public List<AnonVO> selectAll() {
		List<AnonVO> list = new ArrayList<>();
		try {
			connect();

			stmt = conn.createStatement();
			String sql = "SELECT * FROM anon";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				AnonVO anonVO = new AnonVO();
				anonVO.setAnonNum(rs.getInt("a_num"));
				anonVO.setAnonName(rs.getString("a_name"));
				anonVO.setAnonTitle(rs.getString("a_title"));

				list.add(anonVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	

//	//게시글 검색시 조회
//	@Override
//	public List<AnonVO> selectAll(String anonName) {
//		List<AnonVO> list = new ArrayList<>();
//		try {
//			connect();
//
//			stmt = conn.createStatement();
//			String sql = "SELECT * FROM anon WHERE a_name = '" + anonName+"'";
//			rs = stmt.executeQuery(sql);
//			while (rs.next()) {
//				AnonVO anonVO = new AnonVO();
//				anonVO.setAnonName(rs.getString("a_name"));
//				anonVO.setAnonTitle(rs.getString("a_title"));
//
//				list.add(anonVO);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			disconnect();
//		}
//		return list;
//	}

	// 게시글 단건조회 (+ 나중에 댓글이랑 메소드 붙이기)
	@Override
	public AnonVO selectOne(int anonNum) {
		AnonVO anonVO = null;
		try {
			connect();

			stmt = conn.createStatement();
			String sql = "SELECT * FROM anon WHERE a_num = '" + anonNum +"'";
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
			    anonVO = new AnonVO();
				anonVO.setAnonNum(rs.getInt("a_num"));
				anonVO.setAnonName(rs.getString("a_name"));
				anonVO.setAnonTitle(rs.getString("a_title"));
				anonVO.setAnonContent(rs.getString("a_content"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return anonVO;
	}
	
	//게시글 + 비밀번호 확인(비밀번호)
	@Override
	public AnonVO selectCheck(int anonNum) {
		AnonVO anonVO = null;
		try {
			connect();

			stmt = conn.createStatement();
			String sql = "SELECT a_pw FROM anon WHERE a_num = " + anonNum ;
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
			    anonVO = new AnonVO();
				anonVO.setAnonPw(rs.getString("a_pw"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return anonVO;
	}


	// 게시글 작성
	@Override
	public void insert(AnonVO anonVO) {
		try {
			connect();
			anonVO.getAnonNum();
			String sql = "INSERT INTO anon (a_name, a_pw, a_title, a_content) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, anonVO.getAnonName());
			pstmt.setString(2, anonVO.getAnonPw());
			pstmt.setString(3, anonVO.getAnonTitle());
			pstmt.setString(4, anonVO.getAnonContent());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("정상적으로  등록되었습니다.");
			}else {
				System.out.println("정상적으로 등록되지 않았습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 게시글 수정 - manage에서 조건을 걸자(if 비번 같으면..)
	@Override
	public void update(int anonNum, String anonContent) {
		try {
			connect();
			String sql = "UPDATE anon SET a_content = ? WHERE a_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, anonContent);
			pstmt.setInt(2, anonNum);
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("정상적으로 수정되었습니다.");
			}else {
				System.out.println("정상적으로 수정되지 않았습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	//게시글 삭제
	@Override
	public void delete(int anonNum) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM anon WHERE a_num = "+ anonNum;
			
			int result = stmt.executeUpdate(sql);
			if(result > 0) {
				System.out.println("정상적으로 삭제되었습니다..");
			}else {
				System.out.println("정상적으로 삭제되지 않았습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}



}
