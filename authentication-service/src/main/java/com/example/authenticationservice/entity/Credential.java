package com.example.authenticationservice.entity;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credential {

    private String access_token;
    private String refresh_token;
    private String token_type;

}
