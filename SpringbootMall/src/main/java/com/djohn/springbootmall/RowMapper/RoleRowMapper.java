package com.djohn.springbootmall.RowMapper;

import com.djohn.springbootmall.Model.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleRowMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Role role = new Role();
                role.setRoleId(resultSet.getInt("role_id"));
                role.setRoleName(resultSet.getString("role_name"));

                return role;
        }
}
