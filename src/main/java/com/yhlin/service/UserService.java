package com.yhlin.service;

import com.yhlin.bean.Listing;
import com.yhlin.bean.LoginForm;
import com.yhlin.bean.RegisterForm;
import com.yhlin.bean.User;
import com.yhlin.exception.*;
import jakarta.mail.MessagingException;

public interface UserService {
    User registerUser(RegisterForm user) throws MessagingException;

    boolean checkEmailUsed(String email);

    User login(LoginForm loginForm);

    String sendConfirmMail(String email) throws MessagingException;

    boolean checkVerificationCode(User user, String verificationCode);

    void updateUser(User user);

    void addItemToCart(User user, Listing listing, int quantity) throws CartException;

    void removeFromCart(User user, Listing listing) throws CartException, ServerError;

    void removeClosedListingsFromCart(User user) throws CartRemoveException;


    void reviseCart(User user, Integer listingId, Integer quantity) throws CartException;

    void checkout(User user) throws InsufficientItemException, ClosedListingException, EmptyCartException, DatabaseException;

    void cancelOrder(User user, Integer orderId) throws DatabaseException;


}
