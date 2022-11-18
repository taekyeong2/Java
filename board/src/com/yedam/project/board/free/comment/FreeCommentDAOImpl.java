package com.yedam.project.board.free.comment;

import java.util.ArrayList;
import java.util.List;

import com.yedam.project.board.common.DAO;

public class FreeCommentDAOImpl extends DAO implements FreeCommentDAO {
	
	//싱글톤
	private static FreeCommentDAO freeCDAO = null;

	private FreeCommentDAOImpl() {
	}

	public static FreeCommentDAO getInstance() {
		if (freeCDAO == null) {
			freeCDAO = new FreeCommentDAOImpl();
		}
		return freeCDAO;
	}


	//댓글전체 출력
	@Override
	public List<FreeCommentVO> selectAll(FreeCommentVO freeCVO) {
		List<FreeCommentVO> list = new ArrayList<>();
		FreeCommentVO findVO = null;
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "SELECT * FROM free_comment WHERE f_num=" + freeCVO.getFreeNum();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				findVO = new FreeCommentVO();
				findVO.setFreeCNum(rs.getInt("fc_num"));
				findVO.setMemId(rs.getString("m_id"));
				findVO.setFreeCContent(rs.getString("fc_content"));

				list.add(findVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	//댓글등록
	@Override
	public void insert(FreeCommentVO freeCVO) {
		try {
			connect();
			String sql = "INSERT INTO free_comment (fc_content) VALUES (?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, freeCVO.getFreeCContent());
			
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

	//댓글수정
	@Override
	public void update(FreeCommentVO freeCVO) {
		try {
			String sql = "UPDATE free_comment SET fc_content = ? WHERE fc_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, freeCVO.getFreeCContent());
			pstmt.setInt(2, freeCVO.getFreeCNum());
			
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
	public void delete(FreeCommentVO freeCVO) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM free_comment WHERE fc_num = "+ freeCVO.getFreeCNum();
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
