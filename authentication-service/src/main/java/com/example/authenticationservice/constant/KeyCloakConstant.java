package com.example.authenticationservice.constant;

public class KeyCloakConstant {


    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    // thông tin đăng nhập vào keycloak để xử lý các request từ người dùng
    public static final String ACCOUNT_ADMIN = "admin"; // tài khoản đăng nhập keycloak có tất cả quyền quản lý user
    public static final String ACCOUNT_PASSWORD = "123"; // mật khẩu đăng nhập keycloak có tất cả quyền quản lý user
    public static final String CLIENT_ID = "client_id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String GRANT_TYPE = "grant_type";

    public static final String CLIENT_AUTHENTICATION_SERVICE = "authentication-client";
    public static final String SERVER_URL = "keycloak_container";
    public static final String PORT_DOCKER = "8080";
    public static final String REALM = "master";

    // API KEYCLOAK
    public static final String BASE_URL = String.format("http://%s:%s/auth/", SERVER_URL, PORT_DOCKER);
    public static final String LOGIN_URL = String.format(BASE_URL + "realms/%s/protocol/openid-connect/token", REALM);
    public static final String ROOT_USER = String.format(BASE_URL + "admin/realms/%s/users", REALM);
    public static final String PROFILE_URL = String.format(BASE_URL + "realms/%s/protocol/openid-connect/userinfo", REALM);
    public static final String ROOT_ROLE = String.format(BASE_URL + "admin/realms/%s/roles", REALM);


}
