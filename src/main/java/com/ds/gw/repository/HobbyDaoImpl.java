package com.ds.gw.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ds.gw.domain.HobbyDto;
@Repository("hobbyDao")
public class HobbyDaoImpl implements HobbyDao {
	
	@Autowired
	SqlSessionTemplate sm;
	
	@Override
	public List<HobbyDto> getList(HobbyDto dto) {
		return sm.selectList("Hobby_getList", dto);
	}

}
