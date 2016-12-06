package com.indielemon.logsystem.webboot.config;

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

@Configuration
@PropertySource("ldap.properties") 
public class LdapConfig extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	private Environment env;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
//		contextSource.setUrl(env.getRequiredProperty("ldap.url"));
//		contextSource.setBase(env.getRequiredProperty("ldap.base"));
//		contextSource.setUserDn(env.getRequiredProperty("ldap.user"));
//		contextSource.setPassword(env.getRequiredProperty("ldap.password"));
		auth
			.ldapAuthentication()
				.userDnPatterns("uid={0},ou=people")
				.groupSearchBase("ou=groups")
//				.passwordCompare()
//				.and()
				.contextSource().root(env.getRequiredProperty("ldap.base")).ldif(env.getRequiredProperty("ldap.ldif"));
	}

}
