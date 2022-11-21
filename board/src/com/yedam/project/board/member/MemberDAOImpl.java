package com.yedam.project.board.member;

import com.yedam.project.board.common.DAO;

public class MemberDAOImpl extends DAO implements MemberDAO {

	// 싱글톤 - 부모객체 안만들게
	private static MemberDAO memberDAO = null;

	// 다른곳에 객체못만들게 막는용도
	private MemberDAOImpl() {
	}

	public static MemberDAO getInstance() {
		if (memberDAO == null) {
			memberDAO = new MemberDAOImpl();
		}
		return memberDAO;
	}

	// 회원정보 단건조회
	@Override
	public MemberVO selectOne(MemberVO memberVO) {
		MemberVO loginInfo = null;
		try {
			connect();

			stmt = conn.createStatement();
			String sql = "SELECT * FROM member WHERE m_id = '" + memberVO.getMemId() + "'";
			rs = stmt.executeQuery(sql);

			// 아이디 체크
			if (rs.next()) {
				loginInfo = new MemberVO();
				loginInfo.setMemId(rs.getString("m_id"));
				loginInfo.setMemPw(rs.getString("m_pw"));
				loginInfo.setMemRole(rs.getInt("m_role"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return loginInfo;
	}

	// 회원가입(회원정보 등록)
	@Override
	public void insert(MemberVO memberVO) {
		try {
			connect();

			String sql = "INSERT INTO member (m_id, m_pw) VALUES (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberVO.getMemId());
			pstmt.setString(2, memberVO.getMemPw());

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

}
