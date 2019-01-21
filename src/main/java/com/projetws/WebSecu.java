/*
 *      _____   _                   _            _____                      _   
 *     |  __ \ (_) Website Project | |          / ____|                    | |  
 *     | |  | | _  _ __  ___   ___ | |_    ___ | (___   _ __    ___   _ __ | |_ 
 *     | |  | || || '__|/ _ \ / __|| __|  / _ \ \___ \ | '_ \  / _ \ | '__|| __|
 *     | |__| || || |  |  __/| (__ | |_  |  __/ ____) || |_) || (_) || |   | |_ 
 *     |_____/ |_||_|   \___| \___| \__|  \___||_____/ | .__/  \___/ |_|    \__|
 *                                                     | |                                                                                              
 *  ----Authors----                                    |_| 
 * Dimitri BRUYERE
 * Clément COLIN
 * Christopher JEAMME
 *  ---------------
 */
package com.projetws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import com.projetws.model.UserService;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecu extends WebSecurityConfigurerAdapter
{
	@Bean
    public SpringSecurityDialect springSecurityDialect()
	{
        return new SpringSecurityDialect();
    }
	
	/**
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()    
		.antMatchers("/upload").hasRole("PHOTOGRAPHER")
		.antMatchers("/testUpload").hasRole("PHOTOGRAPHER")
		
		.antMatchers("/schoolManagement").hasRole("SCHOOLADMIN")
		.antMatchers("/schoolClassManagement").hasRole("SCHOOLADMIN")
		.antMatchers("/newSchool").hasRole("SCHOOLADMIN")
		.antMatchers("/newSchoolClass").hasRole("SCHOOLADMIN")
		.antMatchers("/newChild").hasRole("SCHOOLADMIN")
		
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/appLogin")
		.usernameParameter("app_username")
		.passwordParameter("app_password")
		.defaultSuccessUrl("/")
		.permitAll()
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/")
		.permitAll();
	}

	@Autowired
	UserService userDetailsService;

	/**
	 *
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(userDetailsService.encoder);
	}
}
