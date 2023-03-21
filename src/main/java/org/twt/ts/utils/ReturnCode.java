package org.twt.ts.utils;

/*
code
1xxxx - success
2xxxx - failed
3xxxx - Privilege Problems
5xxxx - error
9xxxx - test
 */
public enum ReturnCode {
    //success
    Success(10000, "success"),

    // failed
    UnknownFailed(20000, "Unknown Failed"),
    AccountInactivated(20001, "Account Inactivated"),

    // Privilege Error
    NoPrivileges(30000, "No Privilege"),
    UserForbidden(30001, "You are blocked to login"),
    CaptchaError(30002, "Captcha Error"),
    UsernamePasswordNotMatch(30003, "username or password is not correct"),
    PasswordError(30004, "Password Error"),
    WrongSecurityAnswer(30005, "Security Answer Error"),
    UsernameExist(30006, "Username Exist"),
    UserNotExist(30007, "User Id Not Exist!"),

    // Error
    UnknownError(50000, "Server Unknown Error"),
    InvalidMethod(50001, "Invalid Method"),
    InvalidParams(50002, "Invalid Request Params"),

    //API TESTER
    testAPI(99999, "Test API");
    private final int code;
    private final String message;

    ReturnCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
