package com.spring.springbootapplication.entity;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="users")

// user情報
@Data
public class UserInfo implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Email
  @NotBlank
  @Column(name = "EMAIL")
  private String email;

  @NotBlank
  @Column(name = "PASSWORD")
  private String password;

  private String name;
  private String introduce;

  @Column(name = "UPDATED_AT")
  private Date updateDate;
  @Column(name = "CREATED_AT")
  private Date createDate;

  private Date deleteDate;

}