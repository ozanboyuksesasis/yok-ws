package com.sesasis.donusum.yok.core.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesasis.donusum.yok.core.domain.BaseModel;
import com.sesasis.donusum.yok.core.security.dto.UserDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User extends BaseModel<UserDTO> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String ad;
  private String soyad;
  @Column(unique = true)
  private String username;
  private String email;
  @Column(length = 10000)
  @JsonIgnore
  private String password;
  private boolean aktif;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<Role> roleList = new ArrayList<>();

  public User() {
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  @Override
  public UserDTO toDTO() {
    return null;//TODO
    //return getModelMapper().map(this, UserDTO.class);
  }

}
