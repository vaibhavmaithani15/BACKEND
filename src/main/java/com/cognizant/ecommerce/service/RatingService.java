package com.cognizant.ecommerce.service;

import com.cognizant.ecommerce.exception.ProductException;
import com.cognizant.ecommerce.model.Rating;
import com.cognizant.ecommerce.model.User;
import com.cognizant.ecommerce.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest req, User user)throws ProductException;
    public List<Rating> getProductsRating(Long productId);

}
