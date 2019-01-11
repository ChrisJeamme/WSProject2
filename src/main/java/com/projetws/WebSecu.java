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
import org.springframework.context.annotation.Configuration;
import org.springframework.security.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.projetws.model.UserService;
/**
 *
 * @author
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecu extends WebSecurityConfigurerAdapter
{

    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
//	    	.antMatchers("/updateCountry").hasRole("ALL")
//	    	.antMatchers("/updateDepartment").hasRole("ALL")
//	    	.antMatchers("/updateJobHistory").hasRole("ALL")
//	    	.antMatchers("/updateLocation").hasRole("ALL")
//	    	.antMatchers("/updateRegion").hasRole("ALL")
//	    	.antMatchers("/updateEmployee").hasRole("EDITOR")
//	    	.antMatchers("/updateJob").hasRole("EDITOR")
//	    	
//            .antMatchers("/location/all").hasRole("CONSULT")
//            .antMatchers("/region/all").hasRole("CONSULT")
//            .antMatchers("/department/all").hasRole("EDITOR")
//            .antMatchers("/employee/all").hasRole("EDITOR")
//            .antMatchers("/job/all").hasRole("EDITOR")
//            .antMatchers("/jobHistory/all").hasRole("EDITOR")
//            .antMatchers("/country/all").hasRole("EDITOR")
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

        auth.inMemoryAuthentication()
        .withUser("editor").password("editor").roles("CONSULT", "EDITOR")
        .and().withUser("consult").password("consult").roles("CONSULT")
        .and().withUser("admin").password("admin").roles("CONSULT", "EDITOR", "ADMIN");
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(userDetailsService.encoder);
    }
}
