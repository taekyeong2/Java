package com.yedam.project.board.notice.comment;

import java.util.ArrayList;
import java.util.List;

import com.yedam.project.board.common.DAO;
import com.yedam.project.board.common.LoginControl;

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
	public List<NoticeCommentVO> selectAll(int noticeNum) {
		List<NoticeCommentVO> list = new ArrayList<>();
		NoticeCommentVO findVO = null;
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "SELECT * FROM notice_comment WHERE n_num=" + noticeNum;
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
	
	//댓글 단건 출력 - 삭제 수정시 
		@Override
		public NoticeCommentVO selectOne(int noticeCNum) {
			NoticeCommentVO notiCVO = null;
			try {
				connect();

				stmt = conn.createStatement();
				String sql = "SELECT * FROM notice_comment WHERE nc_num = '" + noticeCNum +"'";
				rs = stmt.executeQuery(sql);

				if (rs.next()) {
				    notiCVO = new NoticeCommentVO();
				    notiCVO.setNoticeCNum(rs.getInt("nc_num"));
				    notiCVO.setMemId(rs.getString("m_id"));
				    notiCVO.setNoticeCContent(rs.getString("nc_content"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
			return notiCVO;
		}
		

	//댓글등록
	@Override
	public void insert(NoticeCommentVO noticeCVO) {
		try {
			connect();
			String sql = "INSERT INTO notice_comment VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, LoginControl.getLoginInfo().getMemId());
			pstmt.setInt(2, noticeCVO.getNoticeNum());
			pstmt.setInt(3, noticeCVO.getNoticeCNum());
			pstmt.setString(4, noticeCVO.getNoticeCContent());
		
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
	
	
	//댓글수정
	@Override
	public void update(int noticeCNum, String noticeCContent, int noticeNum) {
		try {
			connect();
			String sql = "UPDATE notice_comment SET nc_content = ? WHERE nc_num = ? and n_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, noticeCContent);
			pstmt.setInt(2, noticeCNum);
			pstmt.setInt(3, noticeNum);
			
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
	public void delete(int noticeCNum, int noticeNum) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM notice_comment WHERE nc_num = '"+ noticeCNum+"' and n_num='"+ noticeNum+"'";
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

	//댓글전체 삭제
	@Override
	public void deleteAll(int noticeNum) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM notice_comment WHERE n_num = "+ noticeNum;
			stmt.executeUpdate(sql);

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

}
