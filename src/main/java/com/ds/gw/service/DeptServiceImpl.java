package com.ds.gw.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ds.gw.domain.DeptDto;
import com.ds.gw.repository.DeptDao;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
	@Resource(name = "deptDao")
	DeptDao dao;

	@Override
	public List<DeptDto> getList(DeptDto dto) {
		return dao.getList(dto);
	}

	
}
