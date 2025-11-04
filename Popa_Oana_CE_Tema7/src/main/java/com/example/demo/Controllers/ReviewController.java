package com.example.demo.Controllers;

import java.util.List;

import com.example.demo.Models.Review;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepo;
    private final ProductRepository productRepo;

    public ReviewController(ReviewRepository reviewRepo, ProductRepository productRepo) {
        this.reviewRepo = reviewRepo;
        this.productRepo = productRepo;
    }

    @GetMapping("/product/{productId}")
    public List<Review> getByProduct(@PathVariable Long productId) {
        return reviewRepo.findByProductId(productId);
    }

    @PostMapping
    public ResponseEntity<Review> create(@RequestBody Review review) {
        if(review.getProduct() != null && review.getProduct().getId() != null) {
            productRepo.findById(review.getProduct().getId())
                    .ifPresent(review::setProduct);
        }
        Review saved = reviewRepo.save(review);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!reviewRepo.existsById(id)) return ResponseEntity.notFound().build();
        reviewRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}