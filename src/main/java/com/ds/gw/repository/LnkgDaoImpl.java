package com.ds.gw.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ds.gw.domain.LnkgDto;

@Repository("lnkgDao")
public class LnkgDaoImpl implements LnkgDao{

	@Autowired
	SqlSessionTemplate sm;
	
	@Override
	public List<LnkgDto> getList(String user_id) {
		return sm.selectList("Lnkg_getList", user_id);
	}

	@Override
	public void insert(LnkgDto dto) {
		sm.insert("Lnkg_insert", dto);
	}

	@Override
	public void update(LnkgDto dto) {
		sm.update("Lnkg_update", dto);
	}

	@Override
	public void reset(LnkgDto dto) {
		sm.delete("Lnkg_reset", dto);
	}

}
