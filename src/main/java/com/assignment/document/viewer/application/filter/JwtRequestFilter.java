package com.assignment.document.viewer.application.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.assignment.document.viewer.application.service.MyUserDetailsService;
import com.assignment.document.viewer.application.utils.JWTUtils;


/**
 * 
 * @author Santosh Gadekar
 *
 * This filter intercepts every request to validate JWT from request headers
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(JwtRequestFilter.class);
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JWTUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.debug("Intercepting incoming request");
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		
		String username = null;
		String jwt = null;
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtils.extractUsername(jwt);
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			 
			UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);
			 
			 if(jwtUtils.validateToken(jwt, userDetails)) {
				 UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				 token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 SecurityContextHolder.getContext().setAuthentication(token);
			 }
		}
		
		filterChain.doFilter(request, response);
		logger.debug("Incoming request forwarded further for processing");
	}
	
}

