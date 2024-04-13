package org.example.repository;

import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Suresh Stalin on 10 / Nov / 2020.
 */

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmailId(String emailId);
    User findByMobileNo(String mobileNo);
}
