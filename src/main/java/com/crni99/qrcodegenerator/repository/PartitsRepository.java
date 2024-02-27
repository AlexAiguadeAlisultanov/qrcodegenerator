package com.crni99.qrcodegenerator.repository;

import com.crni99.qrcodegenerator.model.Partits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartitsRepository extends JpaRepository<Partits, Integer> {
    Optional<Partits> findById(int idPartit);
}