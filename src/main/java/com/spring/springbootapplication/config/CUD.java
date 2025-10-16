
package com.spring.springbootapplication.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.springbootapplication.entity.UserInfo;

import java.util.Arrays;
import java.util.Collection;

public class CUD implements UserDetails {
    private final UserInfo user;

    public CUD(UserInfo user){
    this.user = user;
  }

    // 独自getter
    public String getName() {
        return user.getName(); // UserInfoエンティティのname
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // ログインIDとしてemailを使う場合
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    // "ROLE_USER"を返す
    return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
