package com.ds.gw.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto extends BaseDto {
	String user_id="";
	String user_nm="";
	int user_dept_se_no=0;
	String user_eml_addr="";
	String user_telno="";
	String user_addr="";
	String user_aprv_yn="";
}
