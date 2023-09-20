package com.example.elkredis.constant;

public class CommonConstant {
    public static class Code {
        public static final String SUCCESS = "00";
        public static final String LOGIN_INVALID = "01";
        public static final String USER_INACTIVE = "02";
        public static final String NOT_FOUND = "03";
        public static final String ALREADY_EXISTS = "04";
        public static final String PERMISSION_DENIED = "05";
    }

    public static class Http {
        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String DELETE = "DELETE";
        public static final String UPDATE = "UPDATE";
    }

    public static class MethodProduct {
        public static final String FINDBYID = "findById";
        public static final String FINDALL = "findAll";
        public static final String UPDATEPRODUCT = "updateProduct";

    }
    public static class MethodUser {
        public static final String GETBYID = "getById";
        public static final String FINDALLUSER = "findAllUser";
        public static final String UPDATEUSER = "updateUser";
        public static final String ADDROLERFORUSER = "addRoleForUser";
        public static final String DELETEUSER = "deleteUser";
        public static final String SIGNUP = "signUp";
        public static final String SIGNIN = "signIn";
        public static final String UPDATEALLUSER = "updateAllUser";


    }
}
