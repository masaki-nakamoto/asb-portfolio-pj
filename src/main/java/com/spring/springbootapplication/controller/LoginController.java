package com.spring.springbootapplication.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  //viewへ渡す
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {

  // login画面
  @GetMapping(value = "/login")
  public String login(
    @RequestParam(value = "error", required = false) String error,RedirectAttributes redirectAttributes, Model model
    ){
      model.addAttribute("loginPage", true);
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
