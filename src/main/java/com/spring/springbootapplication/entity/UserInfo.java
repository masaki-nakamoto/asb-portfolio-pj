package com.spring.springbootapplication.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
// テキスト内容の保持
import org.attoparser.dom.Text;
// 画像読み込み保持
import java.awt.image.BufferedImage;
// import javax.imageio.ImageIO;


// user情報
@Data
public class UserInfo implements Serializable{

  private Long id;
  private String name;
  private String email;
  private String password;
  private Text introduce;
  private BufferedImage image;
  private Date updateDate;
  private Date createDate;
  private Date deleteDate;
}