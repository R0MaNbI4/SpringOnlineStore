package ru.rompet.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.rompet.shop.service.security.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  UserService userService;

  public SecurityConfig(UserService userService) {
    this.userService = userService;
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(new BCryptPasswordEncoder());
    provider.setUserDetailsService(userService);
    return provider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/products/delete/**", "/products/update/**")
            .hasAnyRole("ADMIN", "MANAGER")
            .antMatchers("/products/addToCart/**")
            .hasAnyRole("ADMIN", "MANAGER", "USER")
            .antMatchers("/user/**")
            .hasRole("ADMIN")
            .and().formLogin().defaultSuccessUrl("/")
            .and().logout().logoutSuccessUrl("/");

    http.csrf().disable().headers().frameOptions().disable();
  }
}
