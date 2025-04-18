package com.leon.cache_example;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableCaching
public class CacheExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheExampleApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(ProductService productService) {
        return args -> {
            System.out.println("\n\n=====================");
            System.out.println("Id 1: " + productService.getById(1L));
            System.out.println("Id 2: " + productService.getById(2L));
            System.out.println("Id 1: " + productService.getById(1L));
            System.out.println("Id 1: " + productService.getById(1L));
            System.out.println("Id 1: " + productService.getById(1L));
        };
    }

}

record Product(Long id, String name, String description) {
}

@Service
class ProductService {

    Map<Long, Product> products = new HashMap() {
        {
            put(1L, new Product(1L, "Product 1", "Description for product 1"));
            put(2L, new Product(2L, "Product 2", "Description for product 2"));
            put(3L, new Product(3L, "Product 3", "Description for product 3"));
            put(4L, new Product(4L, "Product 4", "Description for product 4"));
            put(5L, new Product(5L, "Product 5", "Description for product 5"));
        }
    };

    @Cacheable("products")
    public Product getById(Long id) {
        System.out.println("Buscando produto com id " + id);
        simulateLatency();
        return products.get(id);
    }

    private void simulateLatency() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===================================");
    }

}
