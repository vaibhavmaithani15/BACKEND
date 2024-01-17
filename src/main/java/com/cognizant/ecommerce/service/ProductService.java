package com.cognizant.ecommerce.service;

import com.cognizant.ecommerce.exception.ProductException;
import com.cognizant.ecommerce.model.Product;
import com.cognizant.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ProductService {
    public Product createProduct(CreateProductRequest request);
    public String deleteProduct(Long productId)throws ProductException;
    public Product updateProduct(Long productId,Product request) throws ProductException;
    public  Product findProductById(Long id) throws ProductException;
    public List<Product> findProductByCategory(String category);

    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice,Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);

    public List<Product> findAllProduct();
}
