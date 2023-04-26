package com.ds.gw.repository;

import java.util.List;

import com.ds.gw.domain.HobbyDto;

public interface HobbyDao {
	List<HobbyDto> getList(HobbyDto dto);
}
