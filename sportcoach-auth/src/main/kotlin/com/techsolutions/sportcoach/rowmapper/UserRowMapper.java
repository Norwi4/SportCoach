package com.techsolutions.sportcoach.rowmapper;

import com.techsolutions.sportcoach.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUsername(rs.getString("user_name"));
		user.setUserpwd(rs.getString("user_pass"));
		return user;
	}

}
