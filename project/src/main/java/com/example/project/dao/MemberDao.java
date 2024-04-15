package com.example.project.dao;

import com.example.project.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    public Member loginMember(Member member) throws Exception;
}

