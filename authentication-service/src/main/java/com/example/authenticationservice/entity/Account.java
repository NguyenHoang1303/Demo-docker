package com.example.authenticationservice.entity;

import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private List<String> roles;

}
