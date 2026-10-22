package com.elkabani.mydb;

import com.elkabani.mydb.entities.Category;
import com.elkabani.mydb.entities.Product;
import com.elkabani.mydb.repositories.CategoryRepository;
import com.elkabani.mydb.repositories.ProductRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductCatalogRunner implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductCatalogRunner(
            CategoryRepository categoryRepository,
            ProductRepository productRepository) {

        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {

        // OPERATION A: Create a new category and product
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics = categoryRepository.save(electronics);

        Product camera = new Product();
        camera.setName("Camera");
        camera.setDescription("Digital Camera");
        camera.setPrice(new BigDecimal("499.99"));
        camera.setCategory(electronics);
        camera = productRepository.save(camera);

        System.out.println("=== OPERATION A ===");
        System.out.println("Created category: " + electronics.getName());
        System.out.println("Created product: " + camera.getName());
        System.out.println("Category ID: " + electronics.getId());
        printDatabaseState();

        // OPERATION B: Reuse existing category
        Category existingCategory = categoryRepository
                .findById(electronics.getId())
                .orElseThrow();

        Product headphones = new Product();
        headphones.setName("Headphones");
        headphones.setDescription("Wireless Headphones");
        headphones.setPrice(new BigDecimal("99.99"));
        headphones.setCategory(existingCategory);
        headphones = productRepository.save(headphones);

        System.out.println("=== OPERATION B ===");
        System.out.println("Created product: " + headphones.getName());
        printDatabaseState();

        // OPERATION C: Delete Headphones
        productRepository.deleteById(headphones.getId());

        System.out.println("=== OPERATION C ===");
        System.out.println("Deleted product: Headphones");
        System.out.println("Headphones still exists: "
                + productRepository.existsById(headphones.getId()));
        printDatabaseState();
    }

    private void printDatabaseState() {

        System.out.println("--- Categories ---");

        categoryRepository.findAll().forEach(category ->
                System.out.println(
                        category.getId() + " | " + category.getName()
                )
        );

        System.out.println("--- Products ---");

        productRepository.findAll().forEach(product ->
                System.out.println(
                        product.getId() + " | "
                                + product.getName() + " | "
                                + product.getPrice() + " | "
                                + product.getCategory().getId()
                )
        );
    }
}
