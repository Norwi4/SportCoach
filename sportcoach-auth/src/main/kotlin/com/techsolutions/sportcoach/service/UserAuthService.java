package com.techsolutions.sportcoach.service;


import com.techsolutions.sportcoach.dao.UserDao;
import com.techsolutions.sportcoach.model.Role;
import com.techsolutions.sportcoach.model.User;
import com.techsolutions.sportcoach.model.UserRole;
import com.techsolutions.sportcoach.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAuthService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Boolean existUser(String username) {
		return userDao.existUser(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getUser(username);
		if (user == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found.");
		}
		List<Role> roles = userDao.getRoles(username);
		List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
			return new SimpleGrantedAuthority(r.getRole());
		}).collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getUserpwd(),
				grantedAuthorities);
	}

	public UserVo getUserByUsername(String username) {
		User user = userDao.getUser(username);

		if (user != null) {

			List<Role> roles = userDao.getRoles(username);

			Set<String> rls = roles.stream().map(r -> r.getRole()).collect(Collectors.toSet());

			UserVo userVo = new UserVo();
			userVo.setUsername(user.getUsername());
			userVo.setUserpwd(user.getUserpwd());
			userVo.setRoles(rls);

			return userVo;
		}

		return null;
	}

	public void saveUser(UserVo userVo) {
		UserRole user = new UserRole();
		user.setUsername(userVo.getUsername());
		user.setUserpwd(passwordEncoder.encode(userVo.getUserpwd()));
		user.setRoles(userVo.getRoles());
		userDao.saveUser(user);
	}

}
