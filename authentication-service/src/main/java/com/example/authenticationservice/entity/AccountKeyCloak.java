package com.example.authenticationservice.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountKeyCloak {

    private String id;
    private String username;
    private String firstName;
    private boolean enabled;
    private List<String> realmRoles;
    private String lastName;
    private List<Credentials> credentials = new ArrayList<>();

    public AccountKeyCloak(Account account) {
        enabled = true;
        username = account.getUsername();
        firstName = account.getFirstName();
        lastName = account.getLastName();
        realmRoles = account.getRoles();
        credentials.add(new Credentials().setPassword(account.getPassword()));
    }

    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    private static class Credentials{

        private boolean temporary;
        private String type;
        private String value;

        public Credentials setPassword(String password){
            type = "password";
            temporary = false;
            value = password;
            return this;
        }
    }

}
