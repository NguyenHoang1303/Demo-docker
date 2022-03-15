package com.example.authenticationservice.service;


import com.example.authenticationservice.Dto.AccountDto;
import com.example.authenticationservice.entity.*;
import com.example.authenticationservice.exception.BadRequestException;
import com.example.authenticationservice.exception.ConflictException;
import com.example.authenticationservice.exception.NotFoundException;
import com.example.authenticationservice.exception.UnauthorizedException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.authenticationservice.constant.KeyCloakConstant.*;

@Service
@Log4j2
public class KeycloakAccountServiceImpl implements AccountService {

    private String keyCloakTokenAdmin;

    public void loginClientKeyCloak() {
        Credential credential = login(ACCOUNT_ADMIN, ACCOUNT_PASSWORD);
        keyCloakTokenAdmin = credential.getAccess_token();
    }

    @Override
    public List<AccountDto> findAll() throws IOException {
        loginClientKeyCloak();
        Document document = Jsoup
                .connect(ROOT_USER)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .header(AUTHORIZATION, BEARER + keyCloakTokenAdmin).execute().parse();
        Type listType = new TypeToken<List<AccountDto>>() {
        }.getType();
        return new Gson().fromJson(document.text(), listType);
    }

    @Override
    public AccountDto findById(String id) {
        loginClientKeyCloak();
        AccountDto account = null;
        try {
            Document document = Jsoup.connect(ROOT_USER + "/" + id)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .header(AUTHORIZATION, BEARER + keyCloakTokenAdmin).execute().parse();
            account = new Gson().fromJson(document.text(), AccountDto.class);
        } catch (IOException e) {
            HttpStatusException http = (HttpStatusException) e;
            if (http.getStatusCode() == 404) {
                throw new NotFoundException("Không tìm thấy tài khoản");
            }
            if (http.getStatusCode() == 401 || http.getStatusCode() == 403) {
                throw new UnauthorizedException("Bạn không có quyền");
            }
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public boolean addRoleToUser(String id, RoleKeyCloak roles) {
        loginClientKeyCloak();
        Document document = null;
        try {
            Jsoup.connect(ROOT_ROLE + "/" + id + "/role-mappings/realm")
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .requestBody(new Gson().toJson(roles))
                    .header(AUTHORIZATION, BEARER + keyCloakTokenAdmin)
                    .execute().parse();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public AccountDto save(Account account) {
        Document document = null;
        try {
            loginClientKeyCloak();
            document = Jsoup.connect(ROOT_USER)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .requestBody(new Gson().toJson(new AccountKeyCloak(account)))
                    .header(AUTHORIZATION, BEARER + keyCloakTokenAdmin)
                    .execute().parse();
            return new Gson().fromJson(document.text(), AccountDto.class);
        } catch (IOException e) {
            HttpStatusException myException = (HttpStatusException) e;
            if (myException.getStatusCode() == 409) {
                throw new ConflictException("Tài khoản tồn tại");
            }
        }
        return null;
    }

    @Override
    public boolean update(String id, Account account) {
        try {
            Jsoup.connect(ROOT_USER + "/" + id)
                    .method(Connection.Method.PUT)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .ignoreContentType(true)
                    .requestBody(new Gson().toJson(account))
                    .header(AUTHORIZATION, BEARER + keyCloakTokenAdmin).execute().parse();

            return true;
        } catch (IOException e) {
            HttpStatusException myException = (HttpStatusException) e;
            if (myException.getStatusCode() == 404) {
                throw new BadRequestException("không tìm thấy tài khoản.");
            }
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            Jsoup.connect(ROOT_USER + "/" + id)
                    .method(Connection.Method.DELETE)
                    .ignoreContentType(true)
                    .header(AUTHORIZATION, BEARER + keyCloakTokenAdmin).execute().parse();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Credential login(String username, String password) {
        Credential credential = new Credential();
        try {
            Document document = Jsoup
                    .connect(LOGIN_URL)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .data(CLIENT_ID, CLIENT_AUTHENTICATION_SERVICE)
                    .data(USERNAME, username)
                    .data(PASSWORD, password)
                    .data(GRANT_TYPE, PASSWORD).execute().parse();
            credential = new Gson().fromJson(document.text(), Credential.class);
            keyCloakTokenAdmin = credential.getAccess_token();
        } catch (IOException e) {
            HttpStatusException myException = (HttpStatusException) e;
            if (myException.getStatusCode() == 401) {
                throw new UnauthorizedException("Kiểm tra lại tài khoản và mật khẩu");
            }
            if (myException.getStatusCode() == 400) {
                throw new BadRequestException(e.getMessage());
            }
        }
        return credential;
    }

    @Override
    public AccountDto getProfile() {
        AccountDto account = new AccountDto();
        try {
            Document document = Jsoup
                    .connect(PROFILE_URL)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .header(AUTHORIZATION, BEARER + keyCloakTokenAdmin).execute().parse();
            ProfileKeyCloak profile = new Gson().fromJson(document.text(), ProfileKeyCloak.class);
            account = new AccountDto(profile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public boolean seedRole() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("view_product"));
        roles.add(new Role("edit_product"));
        roles.add(new Role("delete_product"));
        roles.add(new Role("view_order"));
        roles.add(new Role("edit_order"));
        roles.add(new Role("delete_order"));
        roles.add(new Role("view_user"));
        roles.add(new Role("edit_user"));
        roles.add(new Role("delete_user"));
        try {
            loginClientKeyCloak();
            for (Role role : roles) {
                Jsoup.connect(ROOT_ROLE)
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .method(Connection.Method.POST)
                        .ignoreContentType(true)
                        .requestBody(new Gson().toJson(role))
                        .header(AUTHORIZATION, BEARER + keyCloakTokenAdmin)
                        .execute().parse();
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
