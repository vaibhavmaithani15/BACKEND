package com.cognizant.ecommerce.service;

import com.cognizant.ecommerce.exception.UserException;
import com.cognizant.ecommerce.model.User;
import org.springframework.stereotype.Service;

public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
