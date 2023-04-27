package com.ds.gw.controller;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ds.gw.domain.DeptDto;
import com.ds.gw.domain.HobbyDto;
import com.ds.gw.domain.LnkgDto;
import com.ds.gw.domain.UserDto;



@Controller
public class AdminController {

	private final RestTemplate restTemplate = new RestTemplate();
	

	@SuppressWarnings("unchecked")
	@RequestMapping("/admin")
	public String getList(Model model, UserDto dto) {
		String schkey = dto.getSchKey();
		model.addAttribute("schKey", schkey);
		
		String url = "http://localhost:8090/api/userList?schKey="+schkey;
		List<UserDto> resultList = restTemplate.getForObject(url, List.class);
		model.addAttribute("result", resultList);
		
		List<DeptDto> deptList = restTemplate.getForObject("http://localhost:8090/api/deptList", List.class);
		model.addAttribute("deptList", deptList);
		
		
		return "admin";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/{user_id}")
	public String getView(@PathVariable String user_id, UserDto dto, Model model) {
		String schkey = dto.getSchKey();
		model.addAttribute("schKey", schkey);
		
		String schurl = "http://localhost:8090/api/userList?schKey="+schkey;
		List<UserDto> resultList = restTemplate.getForObject(schurl, List.class);
		model.addAttribute("result", resultList);
		
		List<DeptDto> deptList = restTemplate.getForObject("http://localhost:8090/api/deptList", List.class);
		model.addAttribute("deptList", deptList);

		URI uri = UriComponentsBuilder
				.fromUriString("http://localhost:8090")
				.path("/api/view/user")
				.queryParam("user_id", user_id)
				.encode()
				.build()
				.toUri();
				
		UserDto viewdto = restTemplate.getForObject(uri, UserDto.class);
		model.addAttribute("view", viewdto);

		List<HobbyDto> hobList = restTemplate.getForObject("http://localhost:8090/api/hobList", List.class);
		model.addAttribute("hobList", hobList);
		
		String hoburl = "http://localhost:8090/api/view/hobby?user_id="+user_id;
		String user_hobby = restTemplate.getForObject(hoburl, String.class);
		model.addAttribute("viewHob", user_hobby);
		return "admin";
	}


	@RequestMapping("/admin/delete/{user_id}")
	public String delete(@PathVariable String user_id) {
		restTemplate.delete("http://localhost:8090/api/delete/{user_id}", user_id);
		return "redirect:/admin";
	}

	@RequestMapping("/admin/update/{user_id}")
	public String update(@PathVariable String user_id, UserDto uDto, LnkgDto lDto, Model model) {
		restTemplate.postForEntity("http://localhost:8090/api/user/update", uDto, UserDto.class);
		restTemplate.delete("http://localhost:8090/api/deleteHob/{user_id}", user_id);
		restTemplate.postForEntity("http://localhost:8090/api/hobby/save", lDto, LnkgDto.class);
		
		return "redirect:/admin/{user_id}";
	}
}
