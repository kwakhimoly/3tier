package com.ds.gw.controller;

import java.net.URI;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ds.gw.service.DeptService;
import com.ds.gw.service.HobbyService;
import com.ds.gw.service.LnkgService;
import com.ds.gw.service.UserService;

@Controller
public class AdminController {

	private final RestTemplate restTemplate = new RestTemplate();
	

	@SuppressWarnings("unchecked")
	@RequestMapping("/admin")
	public String getList(Model model) {
		List<UserDto> resultList = restTemplate.getForObject("http://localhost:8090/api/userList", List.class);
		model.addAttribute("result", resultList);
		
		List<DeptDto> deptList = restTemplate.getForObject("http://localhost:8090/api/deptList", List.class);
		model.addAttribute("deptList", deptList);
		
		String schkey = restTemplate.getForObject("http://localhost:8090/api/schkey", String.class);
		model.addAttribute("schKey", schkey);
		
		return "admin";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/{user_id}")
	public String getView(@PathVariable String user_id, Model model) {
		List<UserDto> resultList = restTemplate.getForObject("http://localhost:8090/api/userList", List.class);
		model.addAttribute("result", resultList);
		
		List<DeptDto> deptList = restTemplate.getForObject("http://localhost:8090/api/deptList", List.class);
		model.addAttribute("deptList", deptList);
		
		String schkey = restTemplate.getForObject("http://localhost:8090/api/schkey", String.class);
		model.addAttribute("schKey", schkey);
		
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
	
		
		List<LnkgDto> lnkgList = (List<LnkgDto>) restTemplate.getForObject("http://localhost:8090/api/view/hobby", List.class);
		StringBuffer user_hobby = new StringBuffer();
		for (int i = 0; i < lnkgList.size(); i++) {
			user_hobby.append(lnkgList.get(i).getLnkg_hobby_id());
			System.out.println(user_hobby);
		}
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
//		restTemplate.delete("http://localhost:8090/api/deleteHob/{user_id}", user_id);

		return "redirect:/admin/{user_id}";
	}
}
