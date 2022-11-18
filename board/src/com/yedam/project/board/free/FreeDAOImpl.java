package com.yedam.project.board.free;

import java.util.ArrayList;
import java.util.List;

import com.yedam.project.board.common.DAO;

public class FreeDAOImpl extends DAO implements FreeDAO {
	
	//싱글톤 
	private static FreeDAO freeDAO = null;
	private FreeDAOImpl() {}
	public static FreeDAO getInstance() {
		if(freeDAO ==null) {
			freeDAO = new FreeDAOImpl();
		}
		return freeDAO;
	}

	//게시글 블러오기 
	@Override
	public List<FreeVO> selectAll() {
		List<FreeVO> list = new ArrayList<>();
		try {
			connect();

			stmt = conn.createStatement();
			String sql = "SELECT * FROM free";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				FreeVO freeVO = new FreeVO();
				freeVO.setFreeNum(rs.getInt("f_num"));
				freeVO.setMemId(rs.getString("m_id"));
				freeVO.setFreeTitle(rs.getString("f_title"));

				list.add(freeVO);
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
	public FreeVO selectOne(FreeVO freeVO) {
		FreeVO findVO = null;
		try {
			connect();

			stmt = conn.createStatement();
			String sql = "SELECT * FROM free WHERE f_num = " + freeVO.getFreeNum();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				findVO = new FreeVO();
				findVO.setFreeNum(rs.getInt("f_num"));
				findVO.setMemId(rs.getString("m_id"));
				findVO.setFreeTitle(rs.getString("f_title"));
				findVO.setFreeContent(rs.getString("f_content"));
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
	public void insert(FreeVO freeVO) {
		try {
			connect();
			String sql = "INSERT INTO free (f_title, f_content) VALUES (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(3, freeVO.getFreeTitle());
			pstmt.setString(4, freeVO.getFreeContent());
			
			
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
	public void update(FreeVO freeVO) {
		try {
			String sql = "UPDATE free SET f_content = ? WHERE f_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, freeVO.getFreeContent());
			pstmt.setInt(2, freeVO.getFreeNum());
			
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
	public void delete(FreeVO freeVO) {
		try {
			connect();
			stmt = conn.createStatement();
			String sql = "DELETE FROM free WHERE f_num = "+ freeVO.getFreeNum();
			
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
