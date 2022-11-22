package com.yedam.project.board.anon.comment;

import java.util.ArrayList;
import java.util.List;

import com.yedam.project.board.common.DAO;

public class AnonCommentDAOImpl extends DAO implements AnonCommentDAO {

	// 싱글톤
	private static AnonCommentDAO anonCDAO = null;

	private AnonCommentDAOImpl() {
	}

	public static AnonCommentDAO getInstance() {
		if (anonCDAO == null) {
			anonCDAO = new AnonCommentDAOImpl();
		}
		return anonCDAO;
	}

	// 댓글 전체 출력
	@Override
	public List<AnonCommentVO> selectAll(int anonNum) {
		List<AnonCommentVO> list = new ArrayList<>();
		AnonCommentVO findVO = null;
		try {
			connect();
			stmt = conn.createStatement();
			//게시글 번호로 해당 필드 전부 호출
			String sql = "SELECT * FROM anon_comment WHERE a_num=" + anonNum;
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				findVO = new AnonCommentVO();
				//댓글번호, 댓글닉네임, 댓글내용을 VO변수에 담기
				findVO.setAnonCNum(rs.getInt("ac_num"));
				findVO.setAnonCName(rs.getString("ac_name"));
				findVO.setAnonCContent(rs.getString("ac_content"));
				findVO.setAnonCPw(rs.getString("ac_pw"));
				//담은내용들 리스트에 추가
				list.add(findVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	//댓글단건조회 - 삭제시필요
	@Override
	public AnonCommentVO selectOne(int anonCNum) {
		AnonCommentVO findVO = null;
		try {
			connect();

			stmt = conn.createStatement();
			//댓글번호로 익명게시판 댓글 패스워드 호출
			String sql = "SELECT ac_pw FROM anon_comment WHERE ac_num = " + anonCNum;
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				findVO = new AnonCommentVO();
				findVO.setAnonCPw(rs.getString("ac_pw"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return findVO;
	}


	// 댓글등록
	@Override
	public void insert(AnonCommentVO anonCVO) {
		try {
			connect();
			String sql = "INSERT INTO anon_comment VALUES (?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, anonCVO.getAnonCName());
			pstmt.setString(2, anonCVO.getAnonCPw());
			pstmt.setInt(3, anonCVO.getAnonNum());
			pstmt.setInt(4, anonCVO.getAnonCNum());
			pstmt.setString(5, anonCVO.getAnonCContent());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("정상적으로 등록되었습니다.\n");
			}else {
				System.out.println("정상적으로 등록되지 않았습니다.\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	//댓글 수정
	@Override
	public void update(String anonCContent, int anonCNum, int anonNum) {
		try {
			connect();
			String sql = "UPDATE anon_comment SET ac_content = ? WHERE ac_num = ? and a_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, anonCContent);
			pstmt.setInt(2, anonCNum);
			pstmt.setInt(3, anonNum);
			
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

	//댓글삭제
	@Override
	public void delete(int anonCNum, int anonNum) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM anon_comment WHERE ac_num = '"+ anonCNum +"' and a_num = '"+ anonNum+"'";
			
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

	//댓글 전체 삭제
	@Override
	public void deleteAll(int anonNum) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM anon_comment WHERE a_num = "+ anonNum;
			stmt.executeUpdate(sql);
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	
}
