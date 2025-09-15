package com.spring.springbootapplication.dao;

import org.apache.ibatis.annotations.Mapper;

import com.spring.springbootapplication.dto.UserAdd;
import com.spring.springbootapplication.entity.UserInfo;

import java.util.Optional; //追加

@Mapper
public interface UserInfoMapper {
// user情報登録
  void save(UserAdd userAdd);
// user情報取得
  UserInfo findById(Long id);
// userログイン情報取得
  Optional<UserInfo> findByEmail(String email);
}
