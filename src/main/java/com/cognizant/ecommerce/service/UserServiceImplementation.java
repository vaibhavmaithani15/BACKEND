package com.cognizant.ecommerce.service;

import com.cognizant.ecommerce.config.JwtProvider;
import com.cognizant.ecommerce.exception.UserException;
import com.cognizant.ecommerce.model.User;
import com.cognizant.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }

        throw new UserException("user not found with id: " + userId);
    }


    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email=jwtProvider.getEmailFromToken(jwt);
        User user=userRepository.findByEmail(email);
        return user;
    }
}
