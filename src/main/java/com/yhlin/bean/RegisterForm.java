package com.yhlin.bean;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RegisterForm {
    @NotEmpty
    @Email(message = "Email格式不正確")
    private String email;

    @NotEmpty(message = "您尚未填寫驗證碼")
    private String code;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$", message = "密碼至少需要1個數字、1個字母、1個特殊符號，總長8到20字")
    private String password;

    @NotNull
    @Pattern(regexp = "^\\S{1,20}$", message = "名字格式錯誤（總長1到20字）")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^\\S{1,20}$", message = "名字格式錯誤（總長1到20字）")
    private String lastName;

    public RegisterForm() {
    }

    public RegisterForm(String email, String code, String password, String firstName, String lastName) {
        this.email = email;
        this.code = code;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "RegisterForm{" +
                "email='" + email + '\'' +
                ", code='" + code + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
