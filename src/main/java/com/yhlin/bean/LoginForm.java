package com.yhlin.bean;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class LoginForm {
    @NotEmpty
    @Email(message = "Email格式不正確")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*])(?!.*\\s).{8,20}$", message = "密碼至少需要1個數字、1個字母、1個特殊符號，總長8到20字")
    private String password;


    public LoginForm() {
    }

    public LoginForm(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterForm{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
