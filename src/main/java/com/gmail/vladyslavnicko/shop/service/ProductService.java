package com.gmail.vladyslavnicko.shop.service;

import com.gmail.vladyslavnicko.shop.model.Category;
import com.gmail.vladyslavnicko.shop.model.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    void deleteProductId(long id);
    Product findProductById(long id);
    List<Product> findByNameProduct(String name);
    Product updateProduct(Product product);
    List<Product> findByCategory(Category category);
    List<Product> findByPrice(double minPrice, double maxPrice);
    //List<Product> findByRating(double minRating);
    List<Product> findByAvailability(boolean available);  //возвращает список продуктов которые находятся в наличии (или нет, в зависимости от значения параметра).
    int countProducts();
}
