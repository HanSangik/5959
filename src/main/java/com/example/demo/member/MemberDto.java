package com.example.demo.member;

import java.io.File;
import java.sql.Date;

import com.example.demo.common.BaseDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor(access=AccessLevel.PUBLIC)		//기본생성자 생성
@ToString
public class MemberDto extends BaseDto{

	private String user_seq = "";		//유저식별번호
	private String user_id = "";		//아이디
	private String user_password = "";	//비번
	private String user_name= "";		//이름
	private String user_image1="";			//사용자 이미지
	private String user_image2="";			//사용자 배경 이미지
	private String user_address1="";	//집 주소1
	private String user_address2="";	//집 주소2
	private String user_mail="";		//이메일
	private String user_phone="";		//폰 넘버
	private Date user_wdate;			//가입날자
	private String user_active;			//사용자 활동상태
	private String user_level="";		//사용자 등급
	private String user_business="";	//사업자 등록증
	private String user_birth="";
	
	
	
	
	@Builder
	public MemberDto(String user_seq, String user_id, String user_password, String user_name, String user_image1,
			String user_image2, String user_address1, String user_address2, String user_mail, String user_phone,
			Date user_wdate, String user_active, String user_level, String user_business) {
		this.user_seq = user_seq;
		this.user_id = user_id;
		this.user_password = user_password;
		this.user_name = user_name;
		this.user_image1 = user_image1;
		this.user_image2 = user_image2;
		this.user_address1 = user_address1;
		this.user_address2 = user_address2;
		this.user_mail = user_mail;
		this.user_phone = user_phone;
		this.user_wdate = user_wdate;
		this.user_active = user_active;
		this.user_level = user_level;
		this.user_business = user_business;
	}
	
	
	
}
