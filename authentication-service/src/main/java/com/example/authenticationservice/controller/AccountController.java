package com.example.authenticationservice.controller;

import com.example.authenticationservice.entity.Account;
import com.example.authenticationservice.entity.RoleKeyCloak;
import com.example.authenticationservice.response.RESTResponse;
import com.example.authenticationservice.service.AccountService;
import com.example.authenticationservice.service.KeycloakAccountServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;

@RestController
@Log4j2
@CrossOrigin("*")
@RequestMapping("api/v1/auth")
public class AccountController {

    @Bean
    AccountService accountService() {
        return new KeycloakAccountServiceImpl();
    }

    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    @RolesAllowed("view_user")
    public ResponseEntity getList() throws IOException {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(accountService.findAll())
                .buildData(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path ="{id}" )
    @RolesAllowed("delete_user")
    public ResponseEntity delete(@PathVariable String id){
        if (!accountService.delete(id)){
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setMessage("Xoá thất bại.")
                    .build(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Xoá thành công.")
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path ="/{id}" )
    @RolesAllowed("view_user")
    public ResponseEntity getAccountById(@PathVariable String id){
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(accountService.findById(id))
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path ="{id}" )
    @RolesAllowed("edit_user")
    public ResponseEntity updateAccountById(@PathVariable String id, @RequestBody Account account){
        if (!accountService.update(id, account)){
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setMessage("Cập nhật thất bại")
                    .build(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Cập nhật thành công")
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path ="profile" )
    public ResponseEntity getProfile(){
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(accountService.getProfile())
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path ="{id}/role-mapping" )
    @RolesAllowed("edit_user")
    public ResponseEntity addRoleToUser(@PathVariable String id, @RequestBody RoleKeyCloak roleKeyCloak){
        if (!accountService.addRoleToUser(id, roleKeyCloak)){
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setMessage("Cập nhật thất bại")
                    .build(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Cập nhật thành công")
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "login")
    public ResponseEntity login(@RequestParam String username,
                                @RequestParam String password) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(accountService.login(username, password))
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "register")
    public ResponseEntity register(@RequestBody Account account){
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(accountService.save(account))
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "migrate-role")
    public ResponseEntity migrateRole(){
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(accountService.seedRole())
                .build(), HttpStatus.OK);
    }

}
