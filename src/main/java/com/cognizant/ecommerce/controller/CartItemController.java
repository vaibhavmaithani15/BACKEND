package com.cognizant.ecommerce.controller;

import com.cognizant.ecommerce.exception.CartItemException;
import com.cognizant.ecommerce.exception.UserException;
import com.cognizant.ecommerce.model.CartItem;
import com.cognizant.ecommerce.model.User;
import com.cognizant.ecommerce.request.ApiResponse;
import com.cognizant.ecommerce.service.CartItemService;
import com.cognizant.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart_items")
public class CartItemController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse>deleteCartItem(@PathVariable Long cartItemId, @RequestHeader("Authorization")String jwt)throws UserException, CartItemException{
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(),cartItemId);
        ApiResponse response=new ApiResponse();
        response.setMessage("Delete added to cart");
        response.setStatus(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem,@PathVariable Long cartItemId,@RequestHeader("Authorization") String jwt)throws UserException,CartItemException{
        User user = userService.findUserProfileByJwt(jwt);
        CartItem udpatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        return new ResponseEntity<>(udpatedCartItem,HttpStatus.OK);
    }

}
