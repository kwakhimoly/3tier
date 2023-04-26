package com.ds.gw.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ds.gw.domain.LnkgDto;
import com.ds.gw.repository.LnkgDao;

@Service("lnkgService")
public class LnkgServiceImpl implements LnkgService {
	@Resource(name = "lnkgDao")
	LnkgDao dao;

	@Override
	public List<LnkgDto> getList(String user_id) {
		return dao.getList(user_id);
	}

	@Override
	public void insert(LnkgDto dto) {
		dao.insert(dto);
	}

	@Override
	public void update(LnkgDto dto) {
		dao.update(dto);
	}

	@Override
	public void reset(LnkgDto dto) {
		dao.reset(dto);
	}

}
