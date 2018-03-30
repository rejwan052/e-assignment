package com.eassignment.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.eassignment.persistence.dao.UserRepository;
import com.eassignment.persistence.model.User;

@Component("myAuthenticationSuccessHandler")
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    ActiveUserStore activeUserStore;
    
    @Autowired
    private UserRepository userRepository;

    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
    	
        handle(request, response, authentication);
        
        final HttpSession session = request.getSession(false);
        
        if (session != null) {
            session.setMaxInactiveInterval(30 * 60); //30 minutes
        }
        
        clearAuthenticationAttributes(request);
    }

    protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        final String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication) {
        
    	boolean isAdmin = false;
    	boolean isTeacher = false;
    	boolean isStudent = false;
        
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        for (final GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("TEACHER_PRIVILEGE")) {
            	isTeacher = true;
            } else if (grantedAuthority.getAuthority().equals("STUDENT_PRIVILEGE")) {
            	isStudent = true;
                isTeacher = false;
                break;
            } else if (grantedAuthority.getAuthority().equals("ADMIN_PRIVILEGE")) {
            	isAdmin = true;
            	isTeacher = false;
            	isStudent = false;
			}
        }
        if (isTeacher) {
        	 logger.info("Teacher :"+authentication.getName());
            return "/home?user=" + authentication.getName();
        } else if (isStudent) {
        	 logger.info("Student :"+authentication.getName());
            return "/home?user=" + authentication.getName();
        }else if (isAdmin) {
       	 logger.info("Admin :"+authentication.getName());
         return "/home?user=" + authentication.getName();
        } else {
            throw new IllegalStateException();
        }
    }

    protected void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}