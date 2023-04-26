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
import com.ds.gw.service.DeptService;
import com.ds.gw.service.HobbyService;
import com.ds.gw.service.LnkgService;
import com.ds.gw.service.UserService;

@Controller
public class AdminController {
	@Resource(name = "userService")
	UserService user_service;

	@Resource(name = "deptService")
	DeptService dept_service;

	@Resource(name = "hobbyService")
	HobbyService hob_service;

	@Resource(name = "lnkgService")
	LnkgService lnkg_service;
	
	@RequestMapping("/admin")
	public String getList(DeptDto dept_dto, HobbyDto hob_dto, UserDto dto, Model model) {
		List<UserDto> resultList = user_service.getList(dto);
		model.addAttribute("result", resultList);
		
		List<DeptDto> deptList = dept_service.getList(dept_dto);
		model.addAttribute("deptList", deptList);
		
		List<HobbyDto> hobList = hob_service.getList(hob_dto);
		model.addAttribute("hobList", hobList);
		
		model.addAttribute("schKey", dto.getSchKey());
		
		return "admin";
	}

	@RequestMapping("/admin/{user_id}")
	public String getView(@PathVariable String user_id, Model model, DeptDto dept_dto, HobbyDto hob_dto, UserDto dto) {
		model.addAttribute("schKey", dto.getSchKey());
	
		List<UserDto> resultList = user_service.getList(dto);		
		model.addAttribute("result", resultList);

		List<DeptDto> deptList = dept_service.getList(dept_dto);
		model.addAttribute("deptList", deptList);
		
		List<HobbyDto> hobList = hob_service.getList(hob_dto);
		model.addAttribute("hobList", hobList);
		
		UserDto viewdto = user_service.getView(user_id);
		model.addAttribute("view", viewdto);


		List<LnkgDto> lnkgList = lnkg_service.getList(user_id);
		StringBuffer user_hobby = new StringBuffer();

		for (int i = 0; i < lnkgList.size(); i++) {
			user_hobby.append(lnkgList.get(i).getLnkg_hobby_id());
		}
		model.addAttribute("viewHob", user_hobby);

		return "admin";
	}

	
	@RequestMapping("/admin/delete/{user_id}")
	public String delete(@PathVariable String user_id) {
		user_service.delete(user_id);
		return "redirect:/admin";
	}

	@RequestMapping("/admin/update/{user_id}")
	public String update(@PathVariable String user_id, UserDto uDto, LnkgDto lDto, Model model) {
		user_service.update(uDto);
		lnkg_service.reset(lDto);
		
		if(lDto.getLnkg_hobby_id().equals("")) {
			lDto.setLnkg_hobby_id("Z00");
		} else if(lDto.getLnkg_hobby_id().contains("Z00")) {
			lDto.setLnkg_hobby_id("Z00");
		}
		
		if (lDto.getLnkg_hobby_id().contains(",")) {
			String[] hobby_list = lDto.getLnkg_hobby_id().split(",");
			for (int i = 0; i < hobby_list.length; i++) {
				lDto.setLnkg_hobby_id(hobby_list[i]);
				lnkg_service.insert(lDto);
			}
		} else {
			lnkg_service.insert(lDto);
		}

		return "redirect:/admin/{user_id}";
	}
}
