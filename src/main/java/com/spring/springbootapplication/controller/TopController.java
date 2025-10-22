package com.spring.springbootapplication.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.spring.springbootapplication.config.CUD;

@Controller
public class TopController {
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
