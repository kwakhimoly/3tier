package com.ds.gw.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ds.gw.domain.HobbyDto;
import com.ds.gw.repository.HobbyDao;

@Service("hobbyService")
public class HobbyServiceImpl implements HobbyService {
	@Resource(name = "hobbyDao")
	HobbyDao dao;

	@Override
	public List<HobbyDto> getList(HobbyDto dto) {
		return dao.getList(dto);
	}

	
}
