package com.example.demo.Repository;

import com.example.demo.Models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProviderRepository extends JpaRepository<Provider, Long> {}