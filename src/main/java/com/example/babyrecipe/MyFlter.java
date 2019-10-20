package com.example.babyrecipe;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
@Component
@WebFilter(urlPatterns="/*",filterName="CorsFilter")
public class MyFlter implements Filter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletResponse response = (HttpServletResponse)(arg1);
		HttpServletRequest reqsuest = (HttpServletRequest)arg0;
		String curOrigin = reqsuest.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", curOrigin == null ? "true" : curOrigin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,PATCH,DELETE,PUT");
		response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,ContentType,Accept");
		arg2.doFilter(arg0, arg1);
	}

}