package com.ds.gw.repository;

import java.util.List;

import com.ds.gw.domain.DeptDto;

public interface DeptDao {
	List<DeptDto> getList(DeptDto dto);
}
