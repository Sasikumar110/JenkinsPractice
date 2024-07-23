package com.example.demo.Security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.entity.LoginEntity;
import com.example.demo.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class AuthtokenFilter  extends OncePerRequestFilter{

	private static final Logger logger = LoggerFactory.getLogger(AuthtokenFilter.class);
	
	@Autowired
	private WebSecurityConfig websecu;
	
	@Autowired
	private UserService service;
	
	public String parseJwt(HttpServletRequest httpreq)
	{
		String headerauth=httpreq.getHeader("Authorization");
		if(StringUtils.hasText(headerauth) && headerauth.startsWith("Bearer")) {
			return headerauth.substring(7);
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if(jwt !=null && websecu.validateJwtToken(jwt)) {
				String username = websecu.getUserNameFromJwtToken(jwt);
//				LoginEntity entity = service.LoadUserByUsername(username);
			}
		}
		catch (MalformedJwtException e) {
			logger.error("Invalid Jwt Token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("Invalid Jwt is Expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("Invalid Jwt is UnSupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Jwt Claims String is Empty: {}", e.getMessage());
		}
		
	}
	

}
