package com.yedam.project.board.notice;


import java.util.ArrayList;
import java.util.List;

import com.yedam.project.board.common.DAO;


public class NoticeDAOImpl extends DAO implements NoticeDAO {

	//싱글톤 
		private static NoticeDAO noticeDAO = null;
		private NoticeDAOImpl() {}
		public static NoticeDAO getInstance() {
			if(noticeDAO ==null) {
				noticeDAO = new NoticeDAOImpl();
			}
			return noticeDAO;
		}
		
		
	//게시글 불러오기
	@Override
	public List<NoticeVO> selectAll() {
		List<NoticeVO> list = new ArrayList<>();
		try {
			connect();

			stmt = conn.createStatement();
			String sql = "SELECT * FROM notice";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				NoticeVO noticeVO = new NoticeVO();
				noticeVO.setNoticeNum(rs.getInt("n_num"));
				noticeVO.setMemId(rs.getString("m_id"));
				noticeVO.setNoticeTitle(rs.getString("n_title"));

				list.add(noticeVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	//게시글 단건조회
	@Override
	public NoticeVO selectOne(NoticeVO noticeVO) {
		NoticeVO findVO = null;
		try {
			connect();

			stmt = conn.createStatement();
			String sql = "SELECT * FROM noitce WHERE n_num = " + noticeVO.getNoticeNum();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				findVO = new NoticeVO();
				findVO.setNoticeNum(rs.getInt("n_num"));
				findVO.setMemId(rs.getString("m_id"));
				findVO.setNoticeTitle(rs.getString("n_title"));
				findVO.setNoticeContent(rs.getString("n_content"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return findVO;
	}

	//게시글 작성
	@Override
	public void insert(NoticeVO noticeVO) {
		try {
			connect();
			String sql = "INSERT INTO notice (n_title, n_content) VALUES (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(3, noticeVO.getNoticeTitle());
			pstmt.setString(4, noticeVO.getNoticeContent());
			
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

	
	//게시글 수정
	@Override
	public void update(NoticeVO noticeVO) {
		try {
			String sql = "UPDATE notice SET n_content = ? WHERE n_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, noticeVO.getNoticeContent());
			pstmt.setInt(2, noticeVO.getNoticeNum());
			
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
	public void delete(NoticeVO noticeVO) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM notice WHERE n_num = "+ noticeVO.getNoticeNum();
			
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

