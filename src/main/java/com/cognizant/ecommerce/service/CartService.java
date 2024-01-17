package com.cognizant.ecommerce.service;

import com.cognizant.ecommerce.exception.ProductException;
import com.cognizant.ecommerce.model.Cart;
import com.cognizant.ecommerce.model.User;
import com.cognizant.ecommerce.request.AddItemRequest;

public interface CartService {
    public Cart createCart(User user);
    public String addCartItem(Long userId, AddItemRequest request)throws ProductException;
    public Cart findUserCart(Long userId);
}
