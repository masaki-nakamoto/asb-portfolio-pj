package com.spring.springbootapplication.service; //依存関係を分ける


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.config.CUD;
import com.spring.springbootapplication.entity.UserInfo;
import com.spring.springbootapplication.repository.UserRepository;
// @Bean
@Service
public class UDS implements UserDetailsService {
@Autowired
private UserRepository userRepository;

public UDS(UserRepository userRepository){
  this.userRepository = userRepository;
}

@Override
public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException{
  String e = (email == null) ? null : email.trim().toLowerCase();

 UserInfo user = userRepository.findByEmail(e).orElseThrow(()->new UsernameNotFoundException("not found"));

 return new CUD(user);
//  return org.springframework.security.core.userdetails.User
//         .withUsername(user.getEmail())
//         .password(user.getPassword())
//         .roles("USER")
//         .build();
  }
}
