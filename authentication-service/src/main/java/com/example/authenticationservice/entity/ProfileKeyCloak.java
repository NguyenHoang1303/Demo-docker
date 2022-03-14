package com.example.authenticationservice.entity;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileKeyCloak {

    private String sub; //id
    private boolean email_verified;
    private String name; //full name
    private String preferred_username; //username
    private String given_name; // first name
    private String family_name; //last name
    private String email; //email
}
