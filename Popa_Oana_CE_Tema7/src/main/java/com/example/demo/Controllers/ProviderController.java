package com.example.demo.Controllers;

import java.util.List;

import com.example.demo.Models.Provider;
import com.example.demo.Repository.ProviderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    private final ProviderRepository repo;

    public ProviderController(ProviderRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Provider> getAll() { return repo.findAll(); }

    @PostMapping
    public Provider create(@RequestBody Provider provider) {
        return repo.save(provider);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}