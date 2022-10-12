package com.sprinboot.clienteapp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sprinboot.clienteapp.util.LoginSuccessMessage;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;
	
	@Autowired
	private LoginSuccessMessage successMessage;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().antMatchers("/index", "/home", "/", "/css/**", "/js/**", "/images/**").permitAll()
				/*
				 * .antMatchers("/views/clientes/").hasAnyRole("USER")
				 * .antMatchers("/views/clientes/create").hasAnyRole("ADMIN")
				 * .antMatchers("/views/clientes/save").hasAnyRole("ADMIN")
				 * .antMatchers("/views/clientes/edit/**").hasAnyRole("ADMIN")
				 * .antMatchers("/views/clientes/delete/**").hasAnyRole("ADMIN")
				 */
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.successHandler(successMessage)
			.loginPage("/login")
		.permitAll()
		.and()
		.logout().permitAll();
	}



	@Autowired
	public void configurerSecurityGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passEncoder)
		.usersByUsernameQuery("SELECT username, password, enabled FROM spring_db.users where username=?;")
		.authoritiesByUsernameQuery("SELECT u.username, r.rol FROM spring_db.roles r INNER JOIN spring_db.users u ON r.user_id=u.id WHERE u.username=?");
	}

}
