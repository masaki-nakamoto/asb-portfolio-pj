package com.spring.springbootapplication.controller;

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
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.PathVariable;

import com.spring.springbootapplication.dto.UserAdd;
// import com.spring.springbootapplication.entity.UserInfo;
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
    if (!model.containsAttribute("userAdd")) {
        model.addAttribute("userAdd", new UserAdd());
    }
    model.addAttribute("submitted", false);
    return "signin";
  }

  // user新規登録画面
  @RequestMapping(value = "signin", method = RequestMethod.POST)
public String create(@Validated @ModelAttribute UserAdd userAdd,
                     BindingResult result, Model model) {

    if (result.hasErrors()) {
        model.addAttribute("nameError",     pickTop(result, "name"));
        model.addAttribute("emailError",    pickTop(result, "email"));
        model.addAttribute("passwordError", pickTop(result, "password"));
        model.addAttribute("submitted", true);
        return "signin";
    }

    userAdd.setPassword(passwordEncoder.encode(userAdd.getPassword()));
    userInfoService.save(userAdd);
    return "redirect:top";
}

private String pickTop(BindingResult r, String f) {
    FieldError best = null; //優先度高エラーの格納
    int rank = 99;  //優先度を数値で格納
    for (FieldError e : r.getFieldErrors(f)) {  //各フィールドエラーチェック
        int rnk = switch (e.getCode()) {
            case "NotBlank" -> 0;
            case "Pattern" -> 1;
            case "Size" -> 2;
            default -> 9;
        };
        if (rnk < rank) { //rnkよりrankが小さければ更新
          best = e;
          rank = rnk;
          if (rank==0) break;  //0で抜ける
        }
    }
    return best != null ? best.getDefaultMessage() : null;
}

  @GetMapping(value = "top")
  public void getUser(){
  }
}
