package com.example.demo.member;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("memberDao")
public class MemberDaoImpl implements MemberDao{

	@Autowired
	SqlSessionTemplate sm;
	
	@Override
	public MemberDto getInfo(MemberDto dto) {
		System.out.println("MemberDaoImpl Dto -----------------" + dto );
		return sm.selectOne("Member_getInfo", dto.getUser_id());
	}
	
	@Override
	public void insert(MemberDto dto) {
		sm.insert("Member_insert", dto);
	}

	@Override
	public MemberDto findId(MemberDto dto) {
		return sm.selectOne("Member_findId", dto);
	}

	@Override
	public MemberDto findPassword(MemberDto dto) {
		// TODO Auto-generated method stub
		return sm.selectOne("Member_findPassword", dto);
	}

	@Override
	public void update(MemberDto dto) {
		sm.update("Member_updateInfo", dto);
		
	}
	
	
}
