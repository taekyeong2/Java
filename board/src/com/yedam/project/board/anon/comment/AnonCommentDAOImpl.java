package com.yedam.project.board.anon.comment;

import java.util.ArrayList;
import java.util.List;

import com.yedam.project.board.anon.AnonDAO;
import com.yedam.project.board.anon.AnonDAOImpl;
import com.yedam.project.board.anon.AnonVO;
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
			String sql = "SELECT * FROM anon_comment WHERE a_num=" + anonNum;
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				findVO = new AnonCommentVO();
				findVO.setAnonCNum(rs.getInt("ac_num"));
				findVO.setAnonCName(rs.getString("ac_name"));
				findVO.setAnonCContent(rs.getString("ac_content"));

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
			anonCVO.getAnonCNum();
			String sql = "INSERT INTO anon_comment (ac_name, ac_pw, ac_content, a_num) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, anonCVO.getAnonCName());
			pstmt.setString(2, anonCVO.getAnonCPw());
			pstmt.setString(3, anonCVO.getAnonCContent());
			pstmt.setInt(4, anonCVO.getAnonNum());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("정상적으로 등록되었습니다.");
			}else {
				System.out.println("정상적으로 등록되지 않았습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	//댓글 수정
	@Override
	public void update(int anonCNum, String anonCContent) {
		try {
			connect();
			String sql = "UPDATE anon_comment SET ac_content = ? WHERE ac_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, anonCContent);
			pstmt.setInt(2, anonCNum);
			
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

	//댓글삭제
	@Override
	public void delete(int anonCNum) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM anon_comment WHERE ac_num = "+ anonCNum;
			
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

	//댓글 전체 삭제
	@Override
	public void deleteAll(int anonNum) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM anon_comment WHERE a_num = "+ anonNum;
			
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
