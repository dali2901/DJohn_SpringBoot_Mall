package com.djohn.springbootmall.Dao;

import com.djohn.springbootmall.Model.Member;

public interface MemberDao {
      Member getMemberById(Integer memberId);
      Member getMemberByEmail(String email);
      Integer createMember(Member member);
}
