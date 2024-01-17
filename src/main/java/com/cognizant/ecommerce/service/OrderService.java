package com.cognizant.ecommerce.service;

import com.cognizant.ecommerce.exception.OrderException;
import com.cognizant.ecommerce.model.Address;
import com.cognizant.ecommerce.model.Order;
import com.cognizant.ecommerce.model.User;

import java.util.List;

public interface OrderService{
    public Order createOrder(User user, Address shippingAddress);
    public Order findOrderById(Long orderId) throws OrderException;
    public List<Order> userOrderHistory(Long userId);
    public Order placedOrder(Long orderId) throws OrderException;
    public Order confirmedOrder(Long orderId)throws OrderException;
    public Order shippedOrder(Long orderId)throws OrderException;
    public Order deliveredOrder(Long orderId)throws OrderException;
    public Order cancledOrder(Long orderId)throws OrderException;
    public List<Order> getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;
}
