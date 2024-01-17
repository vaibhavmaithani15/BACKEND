package com.cognizant.ecommerce.service;

import com.cognizant.ecommerce.exception.ProductException;
import com.cognizant.ecommerce.model.Category;
import com.cognizant.ecommerce.model.Product;
import com.cognizant.ecommerce.repository.CategoryRepository;
import com.cognizant.ecommerce.repository.ProductRepository;
import com.cognizant.ecommerce.request.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Product createProduct(CreateProductRequest request) {
        Category topLevel=categoryRepository.findByName(request.getTopLevelCategory());
        if(topLevel==null){
            Category topLevelCategory=new Category();
            topLevelCategory.setName(request.getTopLevelCategory());
            topLevelCategory.setLevel(1);
            topLevel=categoryRepository.save(topLevelCategory);
        }
        Category secondLevel=categoryRepository.findByNameAndParent(request.getSecondLevelCategory(),topLevel.getName());
        if(secondLevel==null){
            Category secondLevelCategory=new Category();
            secondLevelCategory.setName(request.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);
            secondLevel=categoryRepository.save(secondLevelCategory);
        }
        Category thirLevel=categoryRepository.findByNameAndParent(request.getThirdLevelCategory(),secondLevel.getName());
        if(thirLevel==null){
            Category thirdLevelCategory=new Category();
            thirdLevelCategory.setName(request.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirLevel=categoryRepository.save(thirdLevelCategory);
        }
        Product product=new Product();
        product.setTitle(request.getTitle());
        product.setColor(request.getColor());
        product.setDescription(request.getDescription());
        product.setDiscountedPrice(request.getDiscountedPrice());
        product.setDiscountPersent(request.getDiscountPercent());
        product.setImageUrl(request.getImageUrl());
        product.setBrand(request.getBrand());
        product.setPrice(String.valueOf(request.getPrice()));
        product.setSizes(request.getSize());
        product.setQuantity(request.getQuantity());
        product.setCategory(thirLevel);
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);


    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product=findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product request) throws ProductException {
        Product productById = findProductById(productId);
        if(request.getQuantity()!=0){
            productById.setQuantity(request.getQuantity());
        }
        return productRepository.save(productById);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt=productRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new ProductException("Product not found with id -"+id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable= PageRequest.of(pageNumber,pageSize);

        List<Product> products= productRepository.filterProducts(category,minPrice,maxPrice,minDiscount,sort);
//                productRepository.findAll();


        if(!colors.isEmpty()){
            products=products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }
        if(stock!=null){
            if(stock.equalsIgnoreCase("in_stock")){
                products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            }else if(stock.equalsIgnoreCase("out_of_stock")){
                products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
            }
        }
        int startIndex=(int)pageable.getOffset();
        int endIndex=Math.min(startIndex + pageable.getPageSize(),products.size());
        List<Product> pageContent=products.subList(startIndex,endIndex);
        Page<Product> filteredProducts=new PageImpl<>(pageContent,pageable, products.size());
        return filteredProducts;
    }

    @Override
    public List<Product> findAllProduct() {
       return productRepository.findAll();
    }
}
