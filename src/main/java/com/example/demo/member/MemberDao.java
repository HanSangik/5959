package com.example.demo.member;

public interface MemberDao {
	
	MemberDto getInfo(MemberDto dto);
	void insert(MemberDto dto);
	MemberDto findId(MemberDto dto);
	MemberDto findPassword(MemberDto dto);
	void update(MemberDto dto);
}
