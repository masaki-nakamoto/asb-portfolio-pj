package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;  //mapper,bean取得
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  //viewへ渡す
import org.springframework.validation.BindingResult;  //validation
import org.springframework.validation.FieldError;  //validation
import org.springframework.validation.annotation.Validated;  //validation
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.springbootapplication.config.CUD;
import com.spring.springbootapplication.dto.UserAdd;
import com.spring.springbootapplication.entity.UserInfo;
import com.spring.springbootapplication.service.UserInfoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




@Controller
public class UserInfoController {
  // user情報
  @Autowired
  private UserInfoService userInfoService;
  @Autowired
  private UserDetailsService userDetailsService;

  // user新規登録画面
  @GetMapping(value = "signin")
  public String signin(Model model){
    model.addAttribute("loginPage", false);
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
                     BindingResult result, Model model,RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
    if (result.hasErrors()) {
        model.addAttribute("nameError",     pickTop(result, "name"));
        model.addAttribute("emailError",    pickTop(result, "email"));
        model.addAttribute("passwordError", pickTop(result, "password"));
        model.addAttribute("submitted", true);
        model.addAttribute("showLoginButton", true);
        return "signin";
    } else {
      try {
            UserInfo savedUser = userInfoService.create(userAdd, userAdd.getEmail());
            // 登録後ログイン
            UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
            // トークンの発行と認証のセット
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            // セッションに認証セットの保存
            HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
            repo.saveContext(SecurityContextHolder.getContext(), request, response);
            String name = savedUser.getName();
            // System.out.println("RequestMappingのtryの中");
            // System.out.println(name);
            redirectAttributes.addFlashAttribute("showLoginUser", true);
            redirectAttributes.addFlashAttribute("username", name);
        } catch (IllegalArgumentException e) {
            model.addAttribute("emailError", null);  //上のバリデーションと区別
            model.addAttribute("uniqueError", e.getMessage());
            model.addAttribute("submitted", true);
            model.addAttribute("showLoginButton", true);
            return "signin";
        }
    }
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
  public String top(Model model, @ModelAttribute(value = "username") String flashUsername){
    model.addAttribute("showLogoutButton", true);

    String name = flashUsername != null ? flashUsername : "";
    if (name.isEmpty()) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (auth != null && auth.getPrincipal() instanceof CUD) {
        name = ((CUD) auth.getPrincipal()).getName();
      }
    }
    model.addAttribute("loginPage", false);
    model.addAttribute("showLoginUser", true);
    model.addAttribute("username", name);
    return "top";
  }
}
