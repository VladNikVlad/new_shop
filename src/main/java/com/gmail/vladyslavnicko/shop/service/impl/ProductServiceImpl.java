package com.gmail.vladyslavnicko.shop.service.impl;

import com.gmail.vladyslavnicko.shop.exception.ConflictException;
import com.gmail.vladyslavnicko.shop.model.Category;
import com.gmail.vladyslavnicko.shop.model.Product;
import com.gmail.vladyslavnicko.shop.repository.ProductRepository;
import com.gmail.vladyslavnicko.shop.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public Product saveProduct(Product product) {
       Product product1 = productRepository.save(product);
        return product1;
    }

    @Transactional
    @Override
    public void deleteProductId(long id) {
    }

    @Override
    public Product findProductById(long id) {
        return findProductById(id);
    }

    @Override
    public List<Product> findByNameProduct(String name) {
        return findByNameProduct(name);
    }

    @Transactional
    @Override
    public Product updateProduct(Product product) {
        Product oldProduct = findProductById(product.getId());
        if (oldProduct == null) {
            throw new ConflictException("Invalid id");
        }
        return productRepository.save(product);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> findByPrice(double minPrice, double maxPrice) {
        return productRepository.findByPrice(minPrice, maxPrice);
    }

    @Override
    public List<Product> findByAvailability(boolean available) {
        return null;
    }

    @Override
    public int countProducts() {
        //return productRepository.countProducts();
        return  1;
    }
}
