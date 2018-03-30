package com.eassignment.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.web.filter.GenericFilterBean;

public class AjaxTimeoutRedirectFilter extends GenericFilterBean {
	
	private static final Logger logger = LoggerFactory.getLogger(AjaxTimeoutRedirectFilter.class);

	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
	private int sessionExpiredErrorCode = 901;

	public AjaxTimeoutRedirectFilter() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try { chain.doFilter(request, response); }
		catch (IOException ex) { throw ex; }
		catch (Exception ex) {
			Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
			RuntimeException ase = (AuthenticationException) throwableAnalyzer
					.getFirstThrowableOfType(AuthenticationException.class, causeChain);
			if (ase == null) ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);

			if (ase != null) {
				if (ase instanceof AuthenticationException) { throw ase; }
				else if (ase instanceof AccessDeniedException) {
					if (authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
						logger.info("User session expired or not logged in yet");
						String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");

						if ("XMLHttpRequest".equals(ajaxHeader)) {
							logger.info("Ajax call detected, send {} error code", this.sessionExpiredErrorCode);
							HttpServletResponse resp = (HttpServletResponse) response;
							resp.sendError(this.sessionExpiredErrorCode, "Session time out");
						} else {
							logger.info("Redirect to login page");
							throw ase;
						}
					} else {
						throw ase;
					}
				}
			}
		}
	}
	
	private static class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
		protected void initExtractorMap() {
			super.initExtractorMap();
			registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
				public Throwable extractCause(Throwable throwable) {
					ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
					return ((ServletException) throwable).getRootCause();
				}
			});
		}
	}

	public void setSessionExpiredErrorCode(int sessionExpiredErrorCode) {
		this.sessionExpiredErrorCode = sessionExpiredErrorCode;
	}
}
