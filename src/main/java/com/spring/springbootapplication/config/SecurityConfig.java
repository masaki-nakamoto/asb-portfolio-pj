package com.spring.springbootapplication.config;



import org.springframework.context.annotation.Bean;  //部品化
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;  //security
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  //ハッシュ化
import org.springframework.security.crypto.password.PasswordEncoder;  //ハッシュ化
import org.springframework.security.web.SecurityFilterChain;  //security
import org.springframework.security.config.annotation.web.builders.HttpSecurity;  //security

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean  //部品化して他でも渡せる
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    // 認証許可する
    http.authorizeHttpRequests(authz -> authz.requestMatchers("/signin","/create", "/top", "/css/**").permitAll().anyRequest().authenticated()).formLogin(form -> form.disable())
    //CSRF対策を一時的にオフ、開発の時のみ
    .csrf(csrf -> csrf.disable());
    return http.build();

  }
}
