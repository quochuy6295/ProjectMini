package org.example.repository;

import org.example.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Suresh Stalin on 23 / Nov / 2020.
 */

public interface TaxRepository extends JpaRepository<Tax,Long> {
}
