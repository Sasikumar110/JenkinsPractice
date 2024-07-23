package com.example.demo.Security;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.LoginEntity;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
@Component
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Value("${jwt.token.secret}")
	private String jwtSecret;

	@Value("${jwt.token.expiration}")
	private String jwtExpiration;

	public Key keys() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public String generateToken(Authentication authentication) {
		LoginEntity ln = (LoginEntity) authentication.getPrincipal();
		return Jwts.builder().setSubject(ln.getUserName()).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpiration))
				.signWith(keys(), SignatureAlgorithm.HS256).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(keys()).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authtoken) {
		try {
			Jwts.parserBuilder().setSigningKey(keys()).build().parse(authtoken);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid Jwt Token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("Invalid Jwt is Expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("Invalid Jwt is UnSupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Jwt Claims String is Empty: {}", e.getMessage());
		}
		return false;

	}
	
	@Bean 
	   public PasswordEncoder passwordEncoder() { 
	      return new BCryptPasswordEncoder(); 
	   } 

}
