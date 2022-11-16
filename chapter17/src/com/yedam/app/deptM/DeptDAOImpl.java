package com.yedam.app.deptM;

import java.util.ArrayList;
import java.util.List;

import com.yedam.app.common.DAO;

public class DeptDAOImpl extends DAO implements DeptDAO {
	
	private static DeptDAO instance = null;
	
	public static DeptDAO getInstance() {
		if(instance == null)
			instance = new DeptDAOImpl();
		return instance;	
	}
	

	@Override
	public List<DeptVO> selectAll() {
		List<DeptVO> list = new ArrayList<>();
		try {
			connect();
			
			stmt = conn.createStatement();
			String sql = "SELECT * FROM dept_manager";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				DeptVO deptVo = new DeptVO();
				deptVo.setDeptNo(rs.getString("dept_no"));
				deptVo.setEmpNo(rs.getInt("emp_no"));
				deptVo.setFromDate(rs.getString("from_date"));
				deptVo.setToDate(rs.getString("to_date"));
				list.add(deptVo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return list;
	}

	@Override
	public DeptVO selectOne(int deptNo) {
		DeptVO find = null;
		try {
			connect();
			
			stmt = conn.createStatement();
			String sql = "SELECT * FROM dept_manager WHERE emp_no =" + deptNo;
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				find = new DeptVO();
				find.setDeptNo(rs.getString("dept_no"));
				find.setEmpNo(rs.getInt("emp_no"));
				find.setFromDate(rs.getString("from_date"));
				find.setToDate(rs.getString("to_date"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return find;
	}

	@Override
	public void insert(DeptVO deptVO) {
		try {
			connect();
			
			String sql = "INSERT INTO dept_manager VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deptVO.getDeptNo());
			pstmt.setInt(2, deptVO.getEmpNo());
			pstmt.setString(3, deptVO.getFromDate());
			pstmt.setString(4, deptVO.getToDate());
			
			int result = pstmt.executeUpdate();
			
			if(result >0) {
				System.out.println("정상적으로 등록되었습니다.");
			}else {
				System.out.println("정상적으로 등록되지 않았습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}

	}

	@Override
	public void update(DeptVO deptVO) {
		try {
			connect();
			
			String sql = "UPDATE dept_manager SET dept_no = ? WHERE emp_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deptVO.getDeptNo());
			pstmt.setInt(2, deptVO.getEmpNo());
			
			int result = pstmt.executeUpdate();
			
			if(result >0) {
				System.out.println("정상적으로 수정되었습니다.");
			}else {
				System.out.println("정상적으로 수정되지 않았습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
	}

	@Override
	public void delete(int empNo) {
		try {
			connect();
			
			stmt = conn.createStatement();
			String sql = "DELETE FROM dept_manager WHERE emp_no="+ empNo;
			int result = stmt.executeUpdate(sql);
			
			if(result >0) {
				System.out.println("정상적으로 삭제되었습니다.");
			}else {
				System.out.println("정상적으로 삭제되지 않았습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}

	}

}
