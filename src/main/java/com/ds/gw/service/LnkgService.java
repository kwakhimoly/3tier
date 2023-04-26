package com.ds.gw.service;

import java.util.List;

import com.ds.gw.domain.LnkgDto;

public interface LnkgService {
	List<LnkgDto> getList(String user_id);
	void insert(LnkgDto dto);
	void update(LnkgDto dto);
	void reset(LnkgDto dto);
}
