package com.yedam.app.deptM;

import java.util.List;

public interface DeptDAO {
	
	List<DeptVO> selectAll();
	
	DeptVO selectOne(int deptNo);
	
	void insert(DeptVO deptVO);
	
	void update(DeptVO deptVO);
	
	void delete (int empNo);
}
