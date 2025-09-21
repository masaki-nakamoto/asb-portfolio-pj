package com.spring.springbootapplication.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.springbootapplication.entity.UserInfo;

public class UD implements UserDetails {
  private final UserInfo user;
  private final Collection<GrantedAuthority> authorities;

  public UD(UserInfo user, Collection<GrantedAuthority> authorities){
    this.user = user;
    this.authorities = authorities;
  }

  @Override
  public String getPassword(){
    return user.getPassword();
  }

  @Override
  public String getUsername(){
    return user.getEmail();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities(){
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired(){
    return true;
  }

  @Override
  public boolean isAccountNonLocked(){
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired(){
    return true;
  }

  @Override
  public boolean isEnabled(){
    return true;
  }
}
