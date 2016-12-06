package com.indielemon.logsystem.webboot.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.indielemon.logsystem.webboot.controller.WelcomeController;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/images/**").permitAll()
			.antMatchers("/home").permitAll()
			.anyRequest().fullyAuthenticated()
			.and()
		.formLogin()
			.successHandler(new ResumeAuthenticationSuccessHandler())
			.loginProcessingUrl("/login")
			.loginPage("/welcome")
			.failureUrl("/welcome?login_error=t")
			.permitAll()
			.and()
		.logout();
	}

	public static class ResumeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
		static final Logger logger = Logger.getLogger(WebSecurityConfig.class);
		
		private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
			handle(request, response, authentication);
			clearAuthenticationAttributes(request);
		}

		protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
			String targetUrl = determineTargetUrl(authentication);

			if (response.isCommitted()) {
				logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
				return;
			}

			redirectStrategy.sendRedirect(request, response, targetUrl);
		}

		/** Builds the target URL according to the logic defined in the main class Javadoc. */
		protected String determineTargetUrl(Authentication authentication) {
			boolean isUser = false;
			boolean isAdmin = false;
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			logger.info("grantedAuthoritys:");
			for (GrantedAuthority grantedAuthority : authorities) {
				logger.info("    [grantedAuthority.getAuthority():" + grantedAuthority.getAuthority() + "]");
				if (grantedAuthority.getAuthority().equals("ROLE_HRS")) {
					isUser = true;
					break;
				} else if (grantedAuthority.getAuthority().equals("ROLE_MANAGERS")) {
					isAdmin = true;
					break;
				}
			}

			if (isUser) {
				return "/anyUrl";
			} else if (isAdmin) {
				return "/admin";
			} else {
				throw new IllegalStateException();
			}
		}

		protected void clearAuthenticationAttributes(HttpServletRequest request) {
			HttpSession session = request.getSession(false);
			if (session == null) {
				return;
			}
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}

		public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
			this.redirectStrategy = redirectStrategy;
		}

		protected RedirectStrategy getRedirectStrategy() {
			return redirectStrategy;
		}
	}
}
