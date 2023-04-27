package com.ds.gw.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ds.gw.domain.DeptDto;
import com.ds.gw.domain.HobbyDto;
import com.ds.gw.domain.LnkgDto;
import com.ds.gw.domain.UserDto;
import com.ds.gw.service.DeptService;
import com.ds.gw.service.HobbyService;
import com.ds.gw.service.LnkgService;
import com.ds.gw.service.UserService;

@RestController
@RequestMapping("/api")
public class ApiController {
	@Resource(name = "userService")
	UserService user_service;

	@Resource(name = "deptService")
	DeptService dept_service;

	@Resource(name = "hobbyService")
	HobbyService hob_service;

	@Resource(name = "lnkgService")
	LnkgService lnkg_service;
	
	@GetMapping("/userList")
	public List<UserDto> getUserList(UserDto dto) {
		return user_service.getList(dto);
	}

	@RequestMapping("/deptList")
	public List<DeptDto> getDeptList(DeptDto dept_dto) {
		return dept_service.getList(dept_dto);
	}

	@RequestMapping("/hobList")
	public List<HobbyDto> getHobList(HobbyDto hob_dto) {
		return hob_service.getList(hob_dto);
	}

	
	@RequestMapping("/view/user")
	public UserDto getUserView(String user_id) {
		return user_service.getView(user_id);
	}
	
	@RequestMapping("/view/hobby")
	public String getHobView(String user_id) {
		List<LnkgDto> lnkgList = lnkg_service.getList(user_id);
		StringBuffer user_hobby = new StringBuffer();
		for (int i = 0; i < lnkgList.size(); i++) {
			user_hobby.append(lnkgList.get(i).getLnkg_hobby_id());
		}
		return user_hobby.toString();
	}
	
	@RequestMapping("/user/save")
	public ResponseEntity<UserDto> insertUser(@RequestBody UserDto dto) {
		user_service.insert(dto);
		return ResponseEntity.ok(dto);
	}

	@RequestMapping("/hobby/save")
	public ResponseEntity<LnkgDto> insertLnkg(@RequestBody LnkgDto l_dto) {
		if(l_dto.getLnkg_hobby_id().equals("")) {
			l_dto.setLnkg_hobby_id("Z00");
		} else if(l_dto.getLnkg_hobby_id().contains("Z00")) {
			l_dto.setLnkg_hobby_id("Z00");
		}
		
		if(l_dto.getLnkg_hobby_id().contains(",")) {
			String[] hobby_list = l_dto.getLnkg_hobby_id().split(",");
			for (int i = 0; i < hobby_list.length; i++) {
				l_dto.setLnkg_hobby_id(hobby_list[i]);
				lnkg_service.insert(l_dto);
			}
		} else {
			lnkg_service.insert(l_dto);
		}
		return ResponseEntity.ok(l_dto);
	}

	@RequestMapping("/delete/{user_id}")
	public ResponseEntity<UserDto> delete(@PathVariable String user_id) {
		user_service.delete(user_id);
		return ResponseEntity.ok().build();
	}
	@RequestMapping("/deleteHob/{user_id}")
	public ResponseEntity<UserDto> deleteHob(@PathVariable String user_id) {
		LnkgDto dto = new LnkgDto();
		dto.setUser_id(user_id);
		lnkg_service.reset(dto);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping("/user/update")
	public ResponseEntity<UserDto> update(@RequestBody UserDto dto){
		user_service.update(dto);
		return ResponseEntity.ok().build();
	}

	@ResponseBody
	@RequestMapping("/idcheck")
	public boolean idcheck(@RequestBody UserDto dto){
		int user_idCount = user_service.findByID(dto);
		
		if(user_idCount>0) {
			return false;
		} else {
			return true;
		}
	}
}
