package com.example.demo.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.FileUploadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin("*")
@RestController
public class MemberController {
	@Value("${fileUploadPath}") // src/main/resources/application.properies의 값을 읽어온다
	String fileUploadPath;

	@Value("${domain}")
	String domain;

	@Resource(name = "memberService")
	MemberService memberService;

//	@RequestMapping(value="/member/login")
//	public String member_login()
//	{
//		return "member/member_login";
//	}

	@RequestMapping("/member/mypage/{user_id}")
	HashMap<String, Object> member_myinfo(@PathVariable("user_id") String user_id, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println(user_id);
		HttpSession session = request.getSession();
		HashMap<String, Object> map = new HashMap<String, Object>();

		if (user_id == null) {
			map.put("result", "fail");
			return map;
		}

		MemberDto dto = new MemberDto();
		dto.setUser_id(user_id);

		MemberDto resultDto = memberService.getInfo(dto);
		map.put("result", "success");
		map.put("info", resultDto);
		// model.addAttribute("memberDto", resultDto);
		return map;
	}

	@RequestMapping("/member/login_proc")
	public HashMap<String, String> member_login_proc(@RequestBody String payload, HttpServletRequest request) {
		Map<String, Object> javaObject = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			javaObject = mapper.readValue(payload, Map.class);
		} catch (Exception e) {
			System.out.println("payload 오류");
		}

		System.out.println("javaObject: " + javaObject);
		System.out.println("userid:" + javaObject.get("user_id").toString());
		System.out.println("password:" + javaObject.get("user_password").toString());

		MemberDto paramDto = new MemberDto();
		paramDto.setUser_id(javaObject.get("user_id").toString());
		paramDto.setUser_password(javaObject.get("user_password").toString());

		System.out.println("login_proc:" + paramDto);
		HttpSession session = request.getSession();

		MemberDto resultDto = memberService.getInfo(paramDto);
		HashMap<String, String> map = new HashMap<String, String>();
		System.out.println(resultDto);

		if (resultDto == null) {
			map.put("msg", "DB에 정보가 없습니다.");
			map.put("result", "fail");
			map.put("flag", "2");
		} else {
			System.out.println(resultDto.getUser_password().equals(paramDto.getUser_password()));
			if (resultDto.getUser_password().equals(paramDto.getUser_password())) {
				System.out.println("111111111111111111111");
				map.put("result", "success");
				map.put("msg", "로그인에 성공 하셨습니다."); // 로그온 성공시 세션에 정보를 저장한다
				map.put("flag", "1");
				session.setAttribute("user_seq", resultDto.getUser_seq());
				session.setAttribute("user_id", resultDto.getUser_id());
				session.setAttribute("user_name", resultDto.getUser_name());
				session.setAttribute("user_mail", resultDto.getUser_mail());
				session.setAttribute("user_phone", resultDto.getUser_phone());
				session.setAttribute("user_level", resultDto.getUser_level());
				session.setAttribute("user_image1", resultDto.getUser_image1());
				session.setAttribute("user_image2", resultDto.getUser_image2());
				session.setAttribute("user_address1", resultDto.getUser_address1());
				session.setAttribute("user_address2", resultDto.getUser_address2());
				session.setAttribute("user_business", resultDto.getUser_business());
			}

			else {
				System.out.println("333333333333333");
				map.put("msg", "패스워드가 틀렸습니다");
				map.put("result", "fail");
				map.put("flag", "3");
			}
		}

		return map;
	}

	@RequestMapping("/member/insert")
	public HashMap<String, String> member_insert(MultipartFile file1, MultipartFile file2, MemberDto dto) {
		// File Upload
		String uploadDir = fileUploadPath + "/image";

		if (file1 != null) {
			try {
				// 새로운 파일명을 반환한다(파일명이 중복될 수 있기때문에)
				String filename1 = FileUploadUtil.upload(uploadDir, file1);
				dto.setUser_image1(domain + "/" + uploadDir + "/" + filename1);

				// String filename2=FileUploadUtil.upload(uploadDir, file2);
				// dto.setUser_image2(domain +"/"+ uploadDir + "/"+ filename2);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (file2 != null) {
			try {
				// 새로운 파일명을 반환한다(파일명이 중복될 수 있기때문에)
				String filename2 = FileUploadUtil.upload(uploadDir, file2);
				dto.setUser_image2(domain + "/" + uploadDir + "/" + filename2);

				// String filename2=FileUploadUtil.upload(uploadDir, file2);
				// dto.setUser_image2(domain +"/"+ uploadDir + "/"+ filename2);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		///////////////// 이미지 업로드 end /////////////
		System.out.println("userid : " + dto.getUser_id());
		memberService.insert(dto);
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("result", "success");
		return map;
	}

	@RequestMapping("/member/logout")
	public HashMap<String, String> member_logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate(); // 세션의 데이터 삭제
								// 일단 void로 처리하긴 했는데 로그아웃하면 다시 로그인 창으로 리다이렉트 하게 하고 싶어요
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("result", "success");
		return map;
	}

	@RequestMapping("/member/findid_proc")
	public HashMap<String, String> member_findid_proc(MemberDto dto) {
		MemberDto findDto = memberService.findId(dto);
		HashMap map = new HashMap<String, String>();
		if (findDto == null)
			map.put("result", "fail");
		else {
			map.put("result", findDto.getUser_id());
			map.put("user_id", findDto.getUser_id());
			map.put("user_name", findDto.getUser_name());
		}
		return map;
	}

	@RequestMapping("/member/findpass_proc")
	public HashMap<String, String> member_findpass_proc(MemberDto dto) {
		System.out.println(dto);
		MemberDto findDto = memberService.findPassword(dto);
		System.out.println(findDto);
		HashMap map = new HashMap<String, String>();
		if (findDto == null)
			map.put("result", "fail");
		else {
			map.put("result", findDto.getUser_password());
			map.put("user_id", findDto.getUser_id());
			map.put("user_name", findDto.getUser_name());
		}
		return map;
	}

	@RequestMapping("/member/update")
	public HashMap<String, String> member_update(MultipartFile file1, MultipartFile file2, MemberDto dto) {
		System.out.println(dto);
		// File Upload
		String uploadDir = fileUploadPath + "/image";

		if (file1 != null && !file1.getOriginalFilename().isBlank()) {
			try {
				// 새로운 파일명을 반환한다(파일명이 중복될 수 있기때문에)
				String filename1 = FileUploadUtil.upload(uploadDir, file1);
				dto.setUser_image1(domain + "/" + uploadDir + "/" + filename1);

				// String filename2=FileUploadUtil.upload(uploadDir, file2);
				// dto.setUser_image2(domain +"/"+ uploadDir + "/"+ filename2);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (file2 != null && !file2.getOriginalFilename().isBlank()) {
			try {
				// 새로운 파일명을 반환한다(파일명이 중복될 수 있기때문에)
				String filename2 = FileUploadUtil.upload(uploadDir, file2);
				dto.setUser_image2(domain + "/" + uploadDir + "/" + filename2);

				// String filename2=FileUploadUtil.upload(uploadDir, file2);
				// dto.setUser_image2(domain +"/"+ uploadDir + "/"+ filename2);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		memberService.update(dto);
		HashMap map = new HashMap<String, String>();
		map.put("result", "success");
		return map;
	}

}
