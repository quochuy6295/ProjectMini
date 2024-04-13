package org.example.repository;

import org.example.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Suresh Stalin on 24 / Nov / 2020.
 */

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}
