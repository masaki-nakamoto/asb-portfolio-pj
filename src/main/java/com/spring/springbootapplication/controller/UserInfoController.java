package com.spring.springbootapplication.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;  //mapper,bean取得
import org.springframework.security.crypto.password.PasswordEncoder;  //ハッシュ化
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  //viewへ渡す
import org.springframework.validation.BindingResult;  //validation
import org.springframework.validation.FieldError;  //validation
import org.springframework.validation.annotation.Validated;  //validation
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.spring.springbootapplication.dto.UserAdd;
import com.spring.springbootapplication.entity.UserInfo;
import com.spring.springbootapplication.service.UserInfoService;




@Controller
public class UserInfoController {
  // user情報
  @Autowired
  private UserInfoService userInfoService;
  // password ServiceConfig
  @Autowired
  private PasswordEncoder passwordEncoder;

  // user新規登録画面
  @GetMapping(value = "signin")
  public String Add(Model model){
    model.addAttribute("userAdd", new UserAdd());
    return "signin";
  }

  // user新規登録画面
  @RequestMapping(value = "create", method=RequestMethod.POST)
  // @Validated @ModelAttribute UserAdd userAdd→UserAddクラスにマッピング(探しにいく)
  // BindingResult result→バリデーション結果を保持
  // Model model→ビューにデータを渡す
  public String create(@Validated @ModelAttribute UserAdd userAdd, BindingResult result, Model model) {
      if (result.hasErrors()) {
            //インスタンスの作成、変数xxxErrotListに入れる、xxxフィールドのエラーを都度蓄積する、xxxで発生したエラーをまとめて取得し変数に入れる、modelオブジェクトにxxxErrorというキーで渡す、ビューで表示できる
            List<String> nameErrorList = new ArrayList<String>();
            for (FieldError error : result.getFieldErrors("name")) {
                nameErrorList.add(error.getDefaultMessage());
            }
            model.addAttribute("nameError", nameErrorList);

            List<String> emailErrorList = new ArrayList<String>();
            for (FieldError error : result.getFieldErrors("email")) {
                emailErrorList.add(error.getDefaultMessage());
            }
            model.addAttribute("emailError", emailErrorList);

            List<String> passwordErrorList = new ArrayList<String>();
            for (FieldError error : result.getFieldErrors("password")) {
                passwordErrorList.add(error.getDefaultMessage());
            }
            model.addAttribute("passwordError", passwordErrorList);

            return "signin";
        }
        // passwordをハッシュ化
        userAdd.setPassword(passwordEncoder.encode(userAdd.getPassword()));
        // 保存
        userInfoService.save(userAdd);
        return "redirect:top";
  }
  @GetMapping(value = "top")
  public String top(Model model){
    UserInfo users =userInfoService.findById(1L);  //Long型のID1を探す
    model.addAttribute("users", users);
    return "top";
  }
}
