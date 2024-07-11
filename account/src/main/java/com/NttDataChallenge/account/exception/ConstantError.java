package com.NttDataChallenge.account.exception;

public class ConstantError {
    public static final ErrorApp errorApp = new ErrorApp("AE000","Account does not exist");
    public static final ErrorApp errorApp1 = new ErrorApp("AE001","Balance not available");
    public static final ErrorApp errorApp2 = new ErrorApp("ME002","Movement does not exist");
    public static final ErrorApp errorApp3 = new ErrorApp("ME002","No transaction exists");
}
