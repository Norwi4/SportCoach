package com.techsolutions.sportcoach.controller;


import com.techsolutions.sportcoach.exception.DisabledUserException;
import com.techsolutions.sportcoach.exception.InvalidUserCredentialsException;
import com.techsolutions.sportcoach.service.UserAuthService;
import com.techsolutions.sportcoach.util.JwtUtil;
import com.techsolutions.sportcoach.vo.JwtRequest;
import com.techsolutions.sportcoach.vo.JwtResponse;
import com.techsolutions.sportcoach.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class JwtRestController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> generateJwtToken(@RequestBody JwtRequest jwtRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getUserpwd()));
		} catch (DisabledException e) {
			throw new DisabledUserException("User Inactive");
		} catch (BadCredentialsException e) {
			throw new InvalidUserCredentialsException("Invalid Credentials");
		}
		UserDetails userDetails = userAuthService.loadUserByUsername(jwtRequest.getUsername());
		String username = userDetails.getUsername();
		String userpwd = userDetails.getPassword();
		Set<String> roles = userDetails.getAuthorities().stream().map(r -> r.getAuthority())
				.collect(Collectors.toSet());
		UserVo user = new UserVo();
		user.setUsername(username);
		user.setUserpwd(userpwd);
		user.setRoles(roles);
		String token = jwtUtil.generateToken(user);
		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody UserVo userVo) {
		Boolean u = userAuthService.existUser(userVo.getUsername());

		if (!u) {
			userAuthService.saveUser(userVo);
			return new ResponseEntity<String>("User successfully registered", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("User already exists", HttpStatus.CONFLICT);
		}
	}

}
