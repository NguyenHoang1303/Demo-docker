package com.example.authenticationservice.Dto;

import com.example.authenticationservice.entity.ProfileKeyCloak;
import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDto {

    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;

    public AccountDto(ProfileKeyCloak profile) {
        id = profile.getSub();
        username = profile.getPreferred_username();
        firstName = profile.getGiven_name();
        lastName = profile.getFamily_name();
    }
}
