package org.example.repository;

import org.example.entity.Payment;
import org.example.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

        Payment findPaymentByProductItemAndDeletedFalse(ProductItem productItem);
}
