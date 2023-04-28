package com.ds.gw.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ds.gw.domain.DeptDto;
import com.ds.gw.domain.HobbyDto;
import com.ds.gw.domain.LnkgDto;
import com.ds.gw.domain.UserDto;
import com.ds.gw.service.PTService;



@Controller
public class AdminController {

	@Resource(name = "PTService")
	PTService service;
	
	

	@RequestMapping("/admin")
	public String getList(Model model, UserDto dto) {
		String schkey = dto.getSchKey();
		model.addAttribute("schKey", schkey);
		
		List<UserDto> resultList = service.getUserList(dto);
		model.addAttribute("result", resultList);
		
		List<DeptDto> deptList = service.getDeptList();
		model.addAttribute("deptList", deptList);
		
		
		return "admin";
	}

	@RequestMapping("/admin/{user_id}")
	public String getView(@PathVariable String user_id, UserDto dto, Model model) {
		String schkey = dto.getSchKey();
		model.addAttribute("schKey", schkey);
		
		List<UserDto> resultList = service.getUserList(dto);
		model.addAttribute("result", resultList);
		
		List<DeptDto> deptList = service.getDeptList();
		model.addAttribute("deptList", deptList);
				
		UserDto viewdto = service.getViewDto(user_id);
		model.addAttribute("view", viewdto);

		List<HobbyDto> hobList = service.getHobList();
		model.addAttribute("hobList", hobList);
		
		String user_hobby = service.getUH(user_id);
		model.addAttribute("viewHob", user_hobby);
		return "admin";
	}


	@RequestMapping("/admin/delete/{user_id}")
	public String delete(@PathVariable String user_id) {
		service.delete(user_id);
		return "redirect:/admin";
	}

	@RequestMapping("/admin/update/{user_id}")
	public String update(@PathVariable String user_id, UserDto uDto, LnkgDto lDto, Model model) {
		service.updateUser(uDto);
		service.resetUH(user_id);
		service.insertUH(lDto);
		return "redirect:/admin/{user_id}";
	}
}
