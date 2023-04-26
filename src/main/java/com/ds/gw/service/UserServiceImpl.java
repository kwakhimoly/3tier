package com.ds.gw.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.gw.domain.UserDto;
import com.ds.gw.repository.UserDao;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource(name = "userDao")
	UserDao dao;

	@Override
	public List<UserDto> getList(UserDto userdto) {
		
		return dao.getList(userdto);
	}

	@Override
	public UserDto getView(String user_id) {
		return dao.getView(user_id);
	}

	@Override
	@Transactional
	public void insert(UserDto dto) {
		dao.insert(dto);
	}

	@Override
	@Transactional
	public void update(UserDto dto) {
		dao.update(dto);
	}

	@Override
	public void delete(String user_id) {
		dao.delete(user_id);
	}

	@Override
	public int findByID(UserDto userdto) {
		return dao.findByID(userdto);
	}

}
