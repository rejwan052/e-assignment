package com.eassignment.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component("myLogoutSuccessHandler")
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
    @Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	HttpSession session = request.getSession();
        if (session != null && authentication.getDetails() != null) {
            session.removeAttribute("user");
            session.invalidate();
            LOGGER.info("User Successfully Logout"+"---->"+authentication.getDetails().toString());
            
        }
        String URL = request.getContextPath() + "/login?logSucc=true";
		response.setStatus(HttpStatus.OK.value());
		response.sendRedirect(URL);
    }

}
