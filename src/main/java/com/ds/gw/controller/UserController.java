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
import com.ds.gw.service.PTService;

@Controller
public class UserController {


	@Resource(name = "PTService")
	PTService service;
	
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	
	@GetMapping("/user")
	public String getWrite(Model model) {		
		List<DeptDto> deptList = service.getDeptList();
		model.addAttribute("deptList", deptList);
		
		List<HobbyDto> hobList = service.getHobList();
		model.addAttribute("hobList", hobList);
		return "user_write";
	}
	
	@PostMapping("/user/save")
	public String postSave(UserDto dto, LnkgDto l_dto) {

		if(dto.getIdcheck_yn().equals("N")) { 
			return "redirect:/user";
		}

		service.userSave(dto);
		service.insertUH(l_dto);
		return "redirect:/user";
	}
	

	@ResponseBody
	@RequestMapping("/user/idcheck")
	public HashMap<String, Object> idcheck(UserDto dto){
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", service.idcheck(dto));
		return map;
	}
	
}
