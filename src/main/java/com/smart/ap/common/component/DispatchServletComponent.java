package com.smart.ap.common.component;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class DispatchServletComponent implements DispatcherServletPath {

	@Override
	public String getPath() {
		return "/";
	}
}