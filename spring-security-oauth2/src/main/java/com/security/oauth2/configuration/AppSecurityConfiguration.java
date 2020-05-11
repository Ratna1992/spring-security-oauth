package com.security.oauth2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

	/*
	 * @Bean
	 * 
	 * @Override public UserDetailsService userDetailsServiceBean() throws Exception
	 * { List<UserDetails> users = new ArrayList<UserDetails>();
	 * users.add(User.withDefaultPasswordEncoder().username("ratna").password(
	 * "ratna").roles("USER").build()); return new
	 * InMemoryUserDetailsManager(users); }
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()// disable cross origin requests
				.authorizeRequests()// authorize requests
				.antMatchers("/admin").hasRole("ADMIN") // for admin access
				.antMatchers("/user").hasRole("USER") // for user access
				.antMatchers("/contact").permitAll() // for all roles
				.antMatchers("/login").permitAll() // for all roles
				.anyRequest().authenticated()// and only authenticated
				.and().formLogin().successHandler(customizeAuthenticationSuccessHandler)// navigate based on handler
				.loginPage("/login").permitAll()// custom login approach
				.and().logout().invalidateHttpSession(true)// invalidating session after logout
				.clearAuthentication(true)// clearing authentication after logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))// to perform above when matches the URL
				.logoutSuccessUrl("/logout-success")// after successful logout navigate to this URL
				.permitAll();// allow all
	}

}
