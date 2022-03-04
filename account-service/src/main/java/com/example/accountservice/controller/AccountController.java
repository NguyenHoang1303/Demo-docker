package com.example.accountservice.controller;

import com.example.accountservice.service.AccountServiceInterface;
import com.example.accountservice.service.KeycloakAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.io.*;

@RestController
@Log4j2
@RequestMapping("accounts")
public class AccountController {

    @Bean
    AccountServiceInterface accountServiceInterface() {
        return new KeycloakAccountService();
    }

    @Autowired
    AccountServiceInterface accountServiceInterface;

    @RequestMapping(method = RequestMethod.GET)
    @RolesAllowed("user")
    public String getList() {
        return "List Order";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/detail")
    public String getDetail() {
        String command = "curl -d 'client_id=account-service' -d 'username=user' -d 'password=123' -d 'grant_type=password' 'http://keycloak_container:8080/auth/realms/springboot-quickstart/protocol/openid-connect/token' | python -m json.tool";

        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            process.destroy();
            log.warn("content: " + content.toString());
            log.warn("command: " + command);
            return "result: " +  content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "xit roi.";
    }

    @RequestMapping( method = RequestMethod.GET, path = "connect")
    public String connect(){
        return "Connect success";
    }
}
