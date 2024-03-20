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

    //@Autowired 默認按照該''類型''裝配 如果想要使用名稱裝配可以結合@Qualifier註解
    //@Resource 默認按照''名稱''裝配, 名稱可以通過name屬性進行指定 如果沒有指定name屬性 當註解寫在自段上時
    //  默認取自段名稱進行名稱查找
    @Resource
    private final NamedParameterJdbcTemplate mallsecurityJdbcTemplate;
    private final MemberRowMapper memberRowMapper;

    public MemberDaoImpl(NamedParameterJdbcTemplate mallsecurityJdbcTemplate, MemberRowMapper memberRowMapper) {
        this.mallsecurityJdbcTemplate = mallsecurityJdbcTemplate;
        this.memberRowMapper = memberRowMapper;
    }

    @Override
    public Member getMemberById(Integer memberId) {
        String sql = "SELECT member_id, email, password, name, age FROM member WHERE member_id = :memberId";

        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);

        List<Member> memberList =mallsecurityJdbcTemplate.query(sql, map, memberRowMapper);

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

        List<Member> memberList = mallsecurityJdbcTemplate.query(sql, map, memberRowMapper);

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
