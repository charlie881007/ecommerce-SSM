package com.yhlin.service;

import com.yhlin.bean.*;
import com.yhlin.exception.*;
import jakarta.mail.MessagingException;

public interface UserService {
    User registerUser(RegisterForm user) throws MessagingException;

    boolean checkEmailUsed(String email);

    User login(LoginForm loginForm);

    String sendConfirmMail(String email) throws MessagingException;

    boolean checkVerificationCode(User user, String verificationCode);

    void updateUser(User user);

    void addItemToCart(User user, Listing listing, int quantity) throws ClosedListingException, DuplicateItemException, InsufficientItemException, DatabaseException;

    void removeFromCart(User user, Listing listing) throws NotInCartException, DatabaseException;

    void removeClosedListingsFromCart(User user) throws DatabaseException;


    void reviseCart(User user, Integer listingId, Integer quantity) throws NotInCartException, DatabaseException;

    void checkout(User user) throws InsufficientItemException, ClosedListingException, EmptyCartException, DatabaseException;

    Order viewOrder(User user, Integer orderId) throws NoOrderFound;

    void cancelOrder(User user, Integer orderId) throws NoOrderFound, DatabaseException, UncancellableException;
}
