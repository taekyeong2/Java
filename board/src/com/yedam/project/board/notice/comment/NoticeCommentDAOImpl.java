package com.yedam.project.board.notice.comment;

import java.util.ArrayList;
import java.util.List;

import com.yedam.project.board.common.DAO;
import com.yedam.project.board.free.comment.FreeCommentDAO;
import com.yedam.project.board.free.comment.FreeCommentDAOImpl;
import com.yedam.project.board.free.comment.FreeCommentVO;

public class NoticeCommentDAOImpl extends DAO implements NoticeCommentDAO {
	
	//싱글톤
	private static NoticeCommentDAO noticeCDAO = null;

	private NoticeCommentDAOImpl() {
	}

	public static NoticeCommentDAO getInstance() {
		if (noticeCDAO == null) {
			noticeCDAO = new NoticeCommentDAOImpl();
		}
		return noticeCDAO;
	}

	//댓글 전체 출력
	@Override
	public List<NoticeCommentVO> selectAll(NoticeCommentVO noticeCVO) {
		List<NoticeCommentVO> list = new ArrayList<>();
		NoticeCommentVO findVO = null;
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "SELECT * FROM notice_comment WHERE n_num=" + noticeCVO.getNoticeNum();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				findVO = new NoticeCommentVO();
				findVO.setNoticeCNum(rs.getInt("nc_num"));
				findVO.setMemId(rs.getString("m_id"));
				findVO.setNoticeCContent(rs.getString("nc_content"));

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
	public void insert(NoticeCommentVO noticeCVO) {
		try {
			connect();
			String sql = "INSERT INTO notice_comment (nc_content) VALUES (?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, noticeCVO.getNoticeCContent());
			
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
	public void update(NoticeCommentVO noticeCVO) {
		try {
			String sql = "UPDATE notice_comment SET nc_content = ? WHERE nc_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, noticeCVO.getNoticeCContent());
			pstmt.setInt(2, noticeCVO.getNoticeCNum());
			
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
	public void delete(NoticeCommentVO noticeCVO) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM notice_comment WHERE nc_num = "+ noticeCVO.getNoticeCNum();
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
