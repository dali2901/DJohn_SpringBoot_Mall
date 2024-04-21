package com.djohn.springbootmall.Dao;

import com.djohn.springbootmall.Model.Member;
import com.djohn.springbootmall.Model.Role;
import java.util.List;

public interface MemberDao {
      Member getMemberById(Integer memberId);
      Member getMemberByEmail(String email);
      Integer createMember(Member member);
      List<Role> getRolesByMemberId(Integer memberId);

}
