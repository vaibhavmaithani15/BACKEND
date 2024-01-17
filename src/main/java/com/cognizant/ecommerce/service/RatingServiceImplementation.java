package com.cognizant.ecommerce.service;

import com.cognizant.ecommerce.exception.ProductException;
import com.cognizant.ecommerce.model.Product;
import com.cognizant.ecommerce.model.Rating;
import com.cognizant.ecommerce.model.User;
import com.cognizant.ecommerce.repository.RatingRepository;
import com.cognizant.ecommerce.request.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImplementation implements RatingService{
    @Autowired
   private RatingRepository ratingRepository;
    @Autowired
   private ProductService productService;

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreateAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
