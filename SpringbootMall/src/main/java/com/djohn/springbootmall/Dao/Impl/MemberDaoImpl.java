package com.djohn.springbootmall.Dao.Impl;

import com.djohn.springbootmall.Dao.MemberDao;
import com.djohn.springbootmall.Model.Member;
import com.djohn.springbootmall.RowMapper.MemberRowMapper;
import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MemberDaoImpl implements MemberDao {

    @Resource
    private final NamedParameterJdbcTemplate secondJdbcTemplate;
    private final MemberRowMapper memberRowMapper;

    public MemberDaoImpl(NamedParameterJdbcTemplate secondJdbcTemplate, MemberRowMapper memberRowMapper) {
        this.secondJdbcTemplate = secondJdbcTemplate;
        this.memberRowMapper = memberRowMapper;
    }

    @Override
    public Member getMemberById(Integer memberId) {
        String sql = "SELECT member_id, email, password, name, age FROM member WHERE member_id = :memberId";

        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);

        List<Member> memberList =secondJdbcTemplate.query(sql, map, memberRowMapper);

        if (memberList.size() > 0) {
            return memberList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Member getMemberByEmail(String email) {
        String sql = "SELECT member_id, email, password, name, age FROM member WHERE email = :email";

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        List<Member> memberList = secondJdbcTemplate.query(sql, map, memberRowMapper);

        if (memberList.size() > 0) {
            return memberList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createMember(Member member) {
        return null;
    }
}
