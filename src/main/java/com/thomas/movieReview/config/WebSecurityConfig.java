package com.thomas.movieReview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.thomas.movieReview.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{


	@Bean public UserDetailsService userDetailsService() { 
		return new UserDetailsServiceImpl(); 
	}

	@Bean public BCryptPasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	}

	@Bean public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());

		return authenticationProvider; 
	}

	@Override protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
		auth.authenticationProvider(authenticationProvider()); 
	}


	/*
	 * @Bean public PasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 * 
	 * @Bean
	 * 
	 * @Override protected UserDetailsService userDetailsService() { UserDetails
	 * user1 = User .withUsername("Johny")
	 * .password(passwordEncoder().encode("Oracle123")) .roles("USER") .build();
	 * UserDetails user2 = User .withUsername("Thomas")
	 * .password(passwordEncoder().encode("Oracle123@")) .roles("ADMIN") .build();
	 * 
	 * return new InMemoryUserDetailsManager(user1, user2); }
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable().formLogin().disable()
		.headers().frameOptions().disable().and()
		.authorizeRequests()
		.antMatchers("/**").permitAll()
		.antMatchers("/h2-console/**").permitAll()
		.antMatchers("/movies/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
		.anyRequest().authenticated();

		http.httpBasic();
	}
}
