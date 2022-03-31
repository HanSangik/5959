package com.example.demo.member;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl implements MemberService{

	@Resource(name="memberDao")
	MemberDao dao;
	
	@Override
	public MemberDto getInfo(MemberDto dto) {

		
		return dao.getInfo(dto);
	}

	@Override
	public void insert(MemberDto dto) {
		dao.insert(dto);
	}

	@Override
	public MemberDto findId(MemberDto dto) {
		// TODO Auto-generated method stub
		return dao.findId(dto);
	}

	@Override
	public MemberDto findPassword(MemberDto dto) {
		// TODO Auto-generated method stub
		return  dao.findPassword(dto);
	}

	@Override
	public void update(MemberDto dto) {
		dao.update(dto);	
	}

	
}
