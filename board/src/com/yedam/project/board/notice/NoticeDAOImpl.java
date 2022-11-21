package com.yedam.project.board.notice;

import java.util.ArrayList;
import java.util.List;

import com.yedam.project.board.common.DAO;
import com.yedam.project.board.common.LoginControl;

public class NoticeDAOImpl extends DAO implements NoticeDAO {

	// 싱글톤
	private static NoticeDAO noticeDAO = null;

	private NoticeDAOImpl() {
	}

	public static NoticeDAO getInstance() {
		if (noticeDAO == null) {
			noticeDAO = new NoticeDAOImpl();
		}
		return noticeDAO;
	}

	// 게시글 불러오기
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
				noticeVO.setMemId("관리자");
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

	// 게시글 단건조회
	@Override
	public NoticeVO selectOne(int noticeNum) {
		NoticeVO findVO = null;
		try {
			connect();

			stmt = conn.createStatement();
			String sql = "SELECT * FROM notice WHERE n_num = " + noticeNum;
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				findVO = new NoticeVO();
				findVO.setNoticeNum(rs.getInt("n_num"));
				findVO.setMemId("관리자");
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

	// 게시글 작성
	@Override
	public void insert(NoticeVO noticeVO) {
		try {
			connect();
			String sql = "INSERT INTO notice (m_Id, n_title, n_content) VALUES (?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, LoginControl.getLoginInfo().getMemId());
			pstmt.setString(2, noticeVO.getNoticeTitle());
			pstmt.setString(3, noticeVO.getNoticeContent());

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("정상적으로 등록되었습니다.\n");
			} else {
				System.out.println("정상적으로 등록되지 않았습니다.\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 게시글 수정
	@Override
	public void update(int noticeNum, String noticeContent) {
		try {
			connect();
			String sql = "UPDATE notice SET n_content = ? WHERE n_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, noticeContent);
			pstmt.setInt(2, noticeNum);

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("정상적으로 수정되었습니다.\n");
			} else {
				System.out.println("정상적으로 수정되지 않았습니다.\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 게시글 삭제
	@Override
	public void delete(int noticeNum) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM notice WHERE n_num = " + noticeNum;

			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println("정상적으로 삭제되었습니다.\n");
			} else {
				System.out.println("정상적으로 삭제되지 않았습니다.\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

}
