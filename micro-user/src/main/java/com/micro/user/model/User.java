package com.micro.user.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    private Long id;

    private String username;

    private String password;

    private String loginName;

    private String phoneNumber;

    private String email;
}
