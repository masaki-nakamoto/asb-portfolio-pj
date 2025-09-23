package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;  //mapper,bean取得
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  //viewへ渡す
import org.springframework.validation.BindingResult;  //validation
import org.springframework.validation.FieldError;  //validation
import org.springframework.validation.annotation.Validated;  //validation
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.springbootapplication.dto.UserAdd;
import com.spring.springbootapplication.service.UserInfoService;




@Controller
public class UserInfoController {
  // user情報
  @Autowired
  private UserInfoService userInfoService;
  // password ServiceConfig
  // @Autowired
  // private PasswordEncoder passwordEncoder;

  // user新規登録画面
  @GetMapping(value = "signin")
  public String Add(Model model){
    model.addAttribute("showLoginButton", true);
    if (!model.containsAttribute("userAdd")) {
        model.addAttribute("userAdd", new UserAdd());
    }
    model.addAttribute("submitted", false);
    return "signin";
  }

  // user新規登録画面
  @RequestMapping(value = "signin", method = RequestMethod.POST)
public String registUser(@Validated @ModelAttribute UserAdd userAdd,
                     BindingResult result, Model model) {

    if (result.hasErrors()) {
        model.addAttribute("nameError",     pickTop(result, "name"));
        model.addAttribute("emailError",    pickTop(result, "email"));
        model.addAttribute("passwordError", pickTop(result, "password"));
        model.addAttribute("submitted", true);
        return "signin";
    } else {
      try {
            userInfoService.create(userAdd, userAdd.getEmail());
        } catch (IllegalArgumentException e) {
            model.addAttribute("emailError", null);  //上のバリデーションと区別
            model.addAttribute("uniqueError", e.getMessage());
            model.addAttribute("submitted", true);
            return "signin";
        }
    }
    //   userInfoService.create(userAdd, userAdd.getEmail());
    // }

    // ここで二重にエンコード発生
    // userAdd.setPassword(passwordEncoder.encode(userAdd.getPassword()));
    // System.out.println("UserInfoCoontorller");  //debug
    // System.out.println(userAdd);  //debug
    // userInfoService.save(userAdd);
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
  // top画面
  @GetMapping(value = "top")
  public void getUser(Model model){
    model.addAttribute("showLogoutButton", true);
  }


  // login画面
  @GetMapping(value = "/login")
  public String moveToTop(
    @RequestParam(value = "error", required = false) String error,RedirectAttributes redirectAttributes, Model model
    ){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if(auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())){
      return "redirect:top";
    }
    if (error != null){
      model.addAttribute("error","メールアドレス、もしくはパスワードが間違ってます");
    }
    return "login";
  }
}
