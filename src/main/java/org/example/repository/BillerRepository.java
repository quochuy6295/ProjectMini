package org.example.repository;

import org.example.entity.Biller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillerRepository extends JpaRepository<Biller,Long> {
}
