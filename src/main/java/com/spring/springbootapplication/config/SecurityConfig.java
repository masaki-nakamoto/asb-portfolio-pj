package com.spring.springbootapplication.config;

import org.springframework.context.annotation.Bean;  //部品化
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;  //security
import org.springframework.security.web.SecurityFilterChain;  //security
import org.springframework.security.config.annotation.web.builders.HttpSecurity;  //security

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  // パスワード比較デバック用
  private final CustomAuthenticationProvider customAuthenticationProvider;

  public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
    this.customAuthenticationProvider = customAuthenticationProvider;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    // 認証許可する
    http.authorizeHttpRequests(authz -> authz
      .requestMatchers("/signin", "/login", "/css/**").permitAll()
      .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()
      .requestMatchers("/top").authenticated()
      .anyRequest().authenticated()
      )

    // loginパスの認証
    .formLogin(login -> login
      .loginPage("/login")
      .loginProcessingUrl("/login")
      .failureUrl("/login?error")
      .usernameParameter("email")
      .passwordParameter("password")
      .defaultSuccessUrl("/top", true)
      .permitAll()
    )
    //CSRF対策を一時的にオフ、開発の時のみ
    .authenticationProvider(customAuthenticationProvider) // パスワード比較デバック用
    .logout(logout -> logout
    .logoutSuccessUrl("/login?logout")
    .invalidateHttpSession(true)
    .deleteCookies("JSESSIONID")
    .permitAll()
    )
    .csrf(csrf -> csrf.disable());
    return http.build();

  }
}