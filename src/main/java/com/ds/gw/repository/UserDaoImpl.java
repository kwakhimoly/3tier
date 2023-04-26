package com.ds.gw.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ds.gw.domain.UserDto;

@Repository("userDao")
public class UserDaoImpl implements UserDao{

	@Autowired
	SqlSessionTemplate sm;
	
	@Override
	public List<UserDto> getList(UserDto userdto) {
		
		return sm.selectList("User_getList", userdto);
	}

	@Override
	public UserDto getView(String user_id) {
		return sm.selectOne("User_getView", user_id);
	}

	@Override
	public void insert(UserDto dto) {
		sm.insert("User_insert", dto);
		
	}

	@Override
	public void update(UserDto dto) {
		sm.update("User_update", dto);
	}

	@Override
	public void delete(String user_id) {
		sm.delete("User_delete", user_id);
	}

	@Override
	public int findByID(UserDto userdto) {
		return sm.selectOne("User_findById", userdto);
	}
}
