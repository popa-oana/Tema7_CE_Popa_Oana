package com.example.demo.Controllers;

import java.util.List;

import com.example.demo.Models.Product;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.ProviderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ProviderRepository providerRepo;

    public ProductController(ProductRepository productRepo, CategoryRepository categoryRepo, ProviderRepository providerRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.providerRepo = providerRepo;
    }

    @GetMapping
    public List<Product> getAll() { return productRepo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        if(product.getCategory() != null && product.getCategory().getId() != null) {
            categoryRepo.findById(product.getCategory().getId()).ifPresent(product::setCategory);
        }
        if(product.getProvider() != null && product.getProvider().getId() != null) {
            providerRepo.findById(product.getProvider().getId()).ifPresent(product::setProvider);
        }
        return productRepo.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productRepo.findById(id).map(existingProduct -> {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());

            if(updatedProduct.getCategory() != null && updatedProduct.getCategory().getId() != null) {
                categoryRepo.findById(updatedProduct.getCategory().getId()).ifPresent(existingProduct::setCategory);
            }

            if(updatedProduct.getProvider() != null && updatedProduct.getProvider().getId() != null) {
                providerRepo.findById(updatedProduct.getProvider().getId()).ifPresent(existingProduct::setProvider);
            }

            Product saved = productRepo.save(existingProduct);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!productRepo.existsById(id)) return ResponseEntity.notFound().build();
        productRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}