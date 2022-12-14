package com.yedam.project.board.free.comment;

import java.util.ArrayList;
import java.util.List;

import com.yedam.project.board.common.DAO;
import com.yedam.project.board.common.LoginControl;

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
	public List<FreeCommentVO> selectAll(int freeNum) {
		List<FreeCommentVO> list = new ArrayList<>();
		FreeCommentVO findVO = null;
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "SELECT * FROM free_comment WHERE f_num=" + freeNum;
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
	
	//댓글 단건 출력 - 삭제 수정시 
	@Override
	public FreeCommentVO selectOne(int freeCNum) {
		FreeCommentVO freeCVO = null;
		try {
			connect();

			stmt = conn.createStatement();
			String sql = "SELECT * FROM free_comment WHERE fc_num = '" + freeCNum +"'";
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
			    freeCVO = new FreeCommentVO();
				freeCVO.setFreeCNum(rs.getInt("fc_num"));
				freeCVO.setMemId(rs.getString("m_id"));
				freeCVO.setFreeCContent(rs.getString("fc_content"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return freeCVO;
	}
	
	//댓글등록
	@Override
	public void insert(FreeCommentVO freeCVO) {
		try {
			connect();
			String sql = "INSERT INTO free_comment VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, LoginControl.getLoginInfo().getMemId());
			pstmt.setInt(2, freeCVO.getFreeNum());
			pstmt.setInt(3, freeCVO.getFreeCNum());
			pstmt.setString(4, freeCVO.getFreeCContent());
			
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
	public void update(int freeCNum, String freeCContent, int freeNum) {
		try {
			connect();
			String sql = "UPDATE free_comment SET fc_content = ? WHERE fc_num = ? and f_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, freeCContent);
			pstmt.setInt(2, freeCNum);
			pstmt.setInt(3, freeNum);
			
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
	public void delete(int freeCNum, int freeNum) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM free_comment WHERE fc_num = '"+ freeCNum+"' and f_num = '"+freeNum+"'";
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
	public void deleteAll(int freeNum) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM free_comment WHERE f_num = "+ freeNum;
			stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

}
