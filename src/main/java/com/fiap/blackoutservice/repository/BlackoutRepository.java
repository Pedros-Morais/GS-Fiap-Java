package com.fiap.blackoutservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.blackoutservice.model.Blackout;

@Repository
public interface BlackoutRepository extends JpaRepository<Blackout, Long> {
    // Add custom query methods if needed
}
