package com.shope.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   
   
   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }
   @Bean
   public UserDetailsService userDetailsService() {
      return new ShopeUserDetailsService();   
   	}
   
   public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService());
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider;
   }
   
//	@Override
//	protected void configure(HttpSecurity http) throws Exception{
//		http.authorizeRequests().anyRequest().permitAll();
//	}
   
   @Override
   protected void configure(HttpSecurity http) throws Exception{
      http.authorizeRequests()
      //.antMatchers("/").permitAll()
      .antMatchers("/users/**").hasAuthority("Admin")
      .antMatchers("/categories/**").hasAnyAuthority("Admin","Editor")
      .anyRequest().authenticated()
      .and()
      .formLogin()
	         .loginPage("/login")
	         //.defaultSuccessUrl("/") // url로 보냄
	         .successForwardUrl("/categories") // mapping으로 보냄
	         .usernameParameter("email")
	         .permitAll();
      http.logout().permitAll();
   }
	
   @Override
   public void configure(WebSecurity web) throws Exception{
      web.ignoring().antMatchers("/images/**","/js/**","/webjars/**");
   }

}