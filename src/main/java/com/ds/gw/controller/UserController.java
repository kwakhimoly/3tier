package com.ds.gw.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ds.gw.domain.DeptDto;
import com.ds.gw.domain.HobbyDto;
import com.ds.gw.domain.LnkgDto;
import com.ds.gw.domain.UserDto;
import com.ds.gw.service.DeptService;
import com.ds.gw.service.HobbyService;
import com.ds.gw.service.LnkgService;
import com.ds.gw.service.UserService;

@Controller
public class UserController {
	@Resource(name = "userService")
	UserService user_service;
	
	@Resource(name = "deptService")
	DeptService dept_service;

	@Resource(name = "hobbyService")
	HobbyService hob_service;

	@Resource(name = "lnkgService")
	LnkgService lnkg_service;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/user")
	public String getWrite(DeptDto dept_dto, HobbyDto hob_dto, Model model) {		
		List<DeptDto> deptList = dept_service.getList(dept_dto);
		model.addAttribute("deptList", deptList);
		List<HobbyDto> hobList = hob_service.getList(hob_dto);
		model.addAttribute("hobList", hobList);
		return "user_write";
	}
	
	@PostMapping("/user/save")
	public String postSave(UserDto dto, LnkgDto l_dto) {

		if(dto.getIdcheck_yn().equals("N")) {
			return "redirect:/user";
		}

		user_service.insert(dto);
		
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
		
		return "redirect:/user";
	}
	
	@ResponseBody
	@RequestMapping("/user/idcheck")
	public HashMap<String, Object> idcheck(UserDto dto){
		System.out.println(dto.getUser_id());
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", user_service.findByID(dto));
		return map;
	}
	
}
