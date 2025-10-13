package com.spring.springbootapplication.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

// パスワード比較用クラス
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

private final UserDetailsService userDetailsService;
private final PasswordEncoder passwordEncoder;

public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
  this.userDetailsService = userDetailsService;
  this.passwordEncoder = passwordEncoder;
}

@Override
public Authentication authenticate(Authentication authentication) throws AuthenticationException {

  String username = authentication.getName();
  String presentedPassword = authentication.getCredentials().toString();
  UserDetails userDetails = userDetailsService.loadUserByUsername(username);

  System.out.println(userDetails.getPassword());
  System.out.println(presentedPassword);

  boolean matches = passwordEncoder.matches(presentedPassword, userDetails.getPassword());
  System.out.println(matches);

  if(!matches) {
    throw new BadCredentialsException("bad");
  }
  return new UsernamePasswordAuthenticationToken(userDetails,presentedPassword,userDetails.getAuthorities());
}
@Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
