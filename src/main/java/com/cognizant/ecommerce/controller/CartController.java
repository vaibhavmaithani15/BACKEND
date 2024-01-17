package com.cognizant.ecommerce.controller;

import com.cognizant.ecommerce.exception.ProductException;
import com.cognizant.ecommerce.exception.UserException;
import com.cognizant.ecommerce.model.Cart;
import com.cognizant.ecommerce.model.User;
import com.cognizant.ecommerce.request.AddItemRequest;
import com.cognizant.ecommerce.request.ApiResponse;
import com.cognizant.ecommerce.service.CartService;
import com.cognizant.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization")String jwt) throws UserException{
        User user=userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @PutMapping("/add")
    public ResponseEntity<ApiResponse>addingToCart(@RequestBody AddItemRequest req,@RequestHeader("Authorization")String jwt)throws UserException, ProductException{
        User user=userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(),req);
        ApiResponse response=new ApiResponse();
        response.setMessage("Item added to cart");
        response.setStatus(true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
