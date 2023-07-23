package com.chaggle.oauth2.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "user_role")
    private String userRole;

}
