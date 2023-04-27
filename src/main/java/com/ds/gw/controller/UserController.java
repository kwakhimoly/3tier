package com.ds.gw.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ds.gw.domain.DeptDto;
import com.ds.gw.domain.HobbyDto;
import com.ds.gw.domain.LnkgDto;
import com.ds.gw.domain.UserDto;

@Controller
public class UserController {

	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	private final RestTemplate restTemplate = new RestTemplate();	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/user")
	public String getWrite(Model model) {		
		List<DeptDto> deptList = restTemplate.getForObject("http://localhost:8090/api/deptList", List.class);
		model.addAttribute("deptList", deptList);
		
		List<HobbyDto> hobList = restTemplate.getForObject("http://localhost:8090/api/hobList", List.class);
		model.addAttribute("hobList", hobList);
		return "user_write";
	}
	
	@PostMapping("/user/save")
	public String postSave(UserDto dto, LnkgDto l_dto) {

		if(dto.getIdcheck_yn().equals("N")) { 
			return "redirect:/user";
		}

		restTemplate.postForEntity("http://localhost:8090/api/user/save", dto, UserDto.class);
		restTemplate.postForEntity("http://localhost:8090/api/hobby/save", l_dto, LnkgDto.class);
		
		return "redirect:/user";
	}
	

	@ResponseBody
	@RequestMapping("/user/idcheck")
	public HashMap<String, Object> idcheck(UserDto dto){
		boolean result = restTemplate.postForObject("http://localhost:8090/api/idcheck", dto, Boolean.class);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
}
