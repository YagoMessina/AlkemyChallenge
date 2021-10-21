package com.yago.Alkemy.security.config;


import com.yago.Alkemy.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private CustomUserDetailsService service;

  public WebSecurity(CustomUserDetailsService service) {
    this.service = service;
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(service);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
      .authorizeRequests()
      .antMatchers("/auth/**").permitAll()
            .antMatchers("/**").permitAll() //sacar
      .anyRequest().authenticated();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
