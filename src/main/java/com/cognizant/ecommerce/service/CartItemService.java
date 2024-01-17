package com.cognizant.ecommerce.service;

import com.cognizant.ecommerce.exception.CartItemException;
import com.cognizant.ecommerce.exception.UserException;
import com.cognizant.ecommerce.model.Cart;
import com.cognizant.ecommerce.model.CartItem;
import com.cognizant.ecommerce.model.Product;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId,Long id, CartItem cartItem)throws CartItemException, UserException;
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
    public void removeCartItem(Long userId, Long cartItemId) throws  CartItemException,UserException;
    public CartItem findCartItemById(Long cartItemId)throws CartItemException;
}
