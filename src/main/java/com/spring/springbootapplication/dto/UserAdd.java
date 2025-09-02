package com.spring.springbootapplication.dto;

import java.io.Serializable;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Data
public class UserAdd implements Serializable {

  @NotBlank(message = "氏名は必ず入力してください")
  @Size(max = 255, message = "氏名は255文字以内で入力してください")
  private String name;

  @NotBlank(message = "メールアドレスは必ず入力してください")
  @Size(max = 255, message = "メールアドレスは255文字以内で入力してください")
  @Pattern(regexp = "^[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]+(\\.[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]+)*@[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]+(\\.[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]+)*$", message = "メールアドレスが正しい形式ではありません")
  private String email;

  @NotBlank(message = "パスワードは必ず入力してください")
  @Size(min = 8, max = 24, message="8文字以上24文字以内で入力してください")
  @Pattern(regexp = "^(?=.*[a-z]).*$", message = "英数字8文字以上で入力してください")
  private String password;


  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
}
