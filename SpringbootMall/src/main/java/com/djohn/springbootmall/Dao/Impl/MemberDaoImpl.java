package com.djohn.springbootmall.Dao.Impl;

import com.djohn.springbootmall.Dao.MemberDao;
import com.djohn.springbootmall.Model.Member;
import com.djohn.springbootmall.Model.Role;
import com.djohn.springbootmall.RowMapper.MemberRowMapper;
import com.djohn.springbootmall.RowMapper.RoleRowMapper;
import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MemberDaoImpl implements MemberDao {

    //@Autowired 默認按照該''類型''裝配 如果想要使用名稱裝配可以結合@Qualifier註解
    //@Resource 默認按照''名稱''裝配, 名稱可以通過name屬性進行指定 如果沒有指定name屬性 當註解寫在字段上時
    //  默認取字段名稱進行名稱查找
    @Resource
    private final NamedParameterJdbcTemplate mallsecurityJdbcTemplate;
    private final MemberRowMapper memberRowMapper;
    private final RoleRowMapper roleRowMapper;

    public MemberDaoImpl(NamedParameterJdbcTemplate mallsecurityJdbcTemplate, MemberRowMapper memberRowMapper, RoleRowMapper roleRowMapper) {
        this.mallsecurityJdbcTemplate = mallsecurityJdbcTemplate;
        this.memberRowMapper = memberRowMapper;
        this.roleRowMapper = roleRowMapper;
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

    @Override
    public List<Role> getRolesByMemberId(Integer memberId) {
        String sql = "SELECT role.role_id, role.role_name FROM role "+
                  "JOIN member_has_role ON role.role_id = member_has_role.role_id "+
                  "WHERE member_has_role.member_id = :memberId ";

        Map<String , Object> map = new HashMap<>();
        map.put("memberId", memberId);

        List<Role> roleList = mallsecurityJdbcTemplate.query(sql, map, roleRowMapper);
        return roleList;

    }
}
