package com.smart.ap.common.component;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import com.smart.ap.common.advice.JwtTokenProvider;

@Component
public class HttpFilterComponent extends WebRequestHandlerInterceptorAdapter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public HttpFilterComponent(WebRequestInterceptor requestInterceptor) {
		super(requestInterceptor);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

		String token = jwtTokenProvider.resolveToken(request);
		if (jwtTokenProvider.validateToken(token)) {
			return true;
		} else {
			response.sendError(401);
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		System.out.println("================ Method Executed");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		System.out.println("================ Method Completed");
	}
}
