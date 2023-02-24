package com.yhlin.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yhlin.enums.UserStatus;
import com.yhlin.util.Sha256Hasher;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties({"rawPassword", "encryptedPassword", "confirmCode"})
public class User {
    private Integer id;

    private String rawPassword;

    private String encryptedPassword;


    private String firstName;


    private String lastName;

    private String email;

    private UserStatus status;

    private String confirmCode;

    private Cart cart;

    private List<Order> orders = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User() {
    }

    public User(String email, String rawPassword, String firstName, String lastName, UserStatus status) {
        setRawPassword(rawPassword);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setStatus(status);
        this.orders = new ArrayList<>();
    }

    public User(RegisterForm registerForm) {
        this(registerForm.getEmail(), registerForm.getPassword(), registerForm.getFirstName(), registerForm.getLastName(), UserStatus.NORMAL);
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public Cart getCart() {
        return cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Order getOrder(int orderId) {
        for (Order order : orders) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
        Sha256Hasher sha256Hasher = new Sha256Hasher();
        setEncryptedPassword(sha256Hasher.hash(rawPassword));

    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", rawPassword='" + rawPassword + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", confirmCode='" + confirmCode + '\'' +
                ", cart=" + cart +
                '}';
    }
}