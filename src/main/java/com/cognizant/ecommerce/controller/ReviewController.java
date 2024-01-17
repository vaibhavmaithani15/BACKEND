package com.cognizant.ecommerce.controller;

import com.cognizant.ecommerce.exception.ProductException;
import com.cognizant.ecommerce.exception.UserException;
import com.cognizant.ecommerce.model.Review;
import com.cognizant.ecommerce.model.User;
import com.cognizant.ecommerce.request.ReviewRequest;
import com.cognizant.ecommerce.service.ReviewService;
import com.cognizant.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request, @RequestHeader("Authorization")String jwt)throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(request, user);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductReview(@PathVariable Long productId)throws UserException,ProductException{
        List<Review> reviews=reviewService.getAllReview(productId);
        return new ResponseEntity<>(reviews,HttpStatus.CREATED);
    }
}
