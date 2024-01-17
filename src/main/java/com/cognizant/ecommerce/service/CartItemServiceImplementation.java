package com.cognizant.ecommerce.service;

import com.cognizant.ecommerce.exception.CartItemException;
import com.cognizant.ecommerce.exception.UserException;
import com.cognizant.ecommerce.model.Cart;
import com.cognizant.ecommerce.model.CartItem;
import com.cognizant.ecommerce.model.Product;
import com.cognizant.ecommerce.model.User;
import com.cognizant.ecommerce.repository.CartItemRepository;
import com.cognizant.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImplementation implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(Integer.parseInt(cartItem.getProduct().getPrice())*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item=findCartItemById(id);
        User user=userService.findUserById(item.getUserId());
        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(Integer.valueOf(cartItem.getProduct().getPrice()));
            item.setDiscountedPrice(cartItem.getDiscountedPrice()*item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        return cartItemRepository.isCartItemExist(cart,product,size,userId);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
            CartItem cartItem=findCartItemById(cartItemId);
        User user = userService.findUserById(cartItem.getUserId());
        User requestUser=userService.findUserById(userId);
            if(requestUser.getId().equals(user.getId())){
                cartItemRepository.deleteById(cartItemId);
            }else {
                throw new UserException("You can't remove another users item");
            }

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> optional = cartItemRepository.findById(cartItemId);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new CartItemException("CartItem not found with id : "+cartItemId);
    }
}
