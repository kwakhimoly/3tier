package com.ds.gw.service;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ds.gw.domain.DeptDto;
import com.ds.gw.domain.HobbyDto;
import com.ds.gw.domain.LnkgDto;
import com.ds.gw.domain.UserDto;

@Service("PTService")
public class PTService {

	private final RestTemplate restTemplate = new RestTemplate();
	private final String bturl = "http://localhost:8090/api";

	//admin

	@SuppressWarnings("unchecked")
	public List<UserDto> getUserList(UserDto dto) {
		String schkey = dto.getSchKey();
		
		String url = bturl+"/userList?schKey="+schkey;
		List<UserDto> resultList = restTemplate.getForObject(url, List.class);
		
		return resultList;
	}

	public String getUH(String user_id) {
		String url = bturl+"/view/hobby?user_id="+user_id;
		String user_hobby = restTemplate.getForObject(url, String.class);
		
		return user_hobby;
	}

	@SuppressWarnings("unchecked")
	public List<DeptDto> getDeptList() {
		List<DeptDto> deptList = restTemplate.getForObject(bturl+"/deptList", List.class);
		return deptList;
	}

	@SuppressWarnings("unchecked")
	public List<HobbyDto> getHobList() {
		List<HobbyDto> hobList = restTemplate.getForObject(bturl+"/hobList", List.class);
		return hobList;
	}

	public UserDto getViewDto(String user_id) {

		URI uri = UriComponentsBuilder
				.fromUriString(bturl)
				.path("/view/user")
				.queryParam("user_id", user_id)
				.encode()
				.build()
				.toUri();
				
		UserDto viewdto = restTemplate.getForObject(uri, UserDto.class);

		return viewdto;
	}


	public void delete(String user_id) {
		restTemplate.delete(bturl+"/delete/{user_id}", user_id);
	}

	public void updateUser(UserDto uDto) {
		restTemplate.postForEntity(bturl+"/user/update", uDto, UserDto.class);
	}
	
	public void resetUH(String user_id) {
		restTemplate.delete(bturl+"/deleteHob/{user_id}", user_id);
	}
	
	public void insertUH(LnkgDto lDto) {
		restTemplate.postForEntity(bturl+"/hobby/save", lDto, LnkgDto.class);
	}
	
	//user

	public void userSave(UserDto dto) {
		restTemplate.postForEntity(bturl+"/user/save", dto, UserDto.class);
	}
	

	public boolean idcheck(UserDto dto){
		boolean result = restTemplate.postForObject(bturl+"/idcheck", dto, Boolean.class);
		return result;
	}
}
