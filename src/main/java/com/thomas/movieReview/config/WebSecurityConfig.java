package com.thomas.movieReview.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.thomas.movieReview.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{


	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.setAllowedOrigins(List.of("http://localhost:8000"));
        configuration.setAllowedMethods(List.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
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


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.httpBasic().and()
		.cors().and()
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/actuator/**").permitAll()
		.antMatchers("/users/**").permitAll()
		.antMatchers("/h2-console/**").permitAll()
		.antMatchers( HttpMethod.OPTIONS, "/**" ).permitAll()
		//.antMatchers(HttpMethod.GET, "/**").permitAll()
		.antMatchers(HttpMethod.POST,"/movies/**").hasAnyRole("ADMIN")
		.antMatchers("/movies/**").hasAnyRole("USER","ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().disable();
		//.and().formLogin().permitAll()
		//.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/showlist");

	}
}
