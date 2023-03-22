package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.PaymentMethod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

    @Query("SELECT pm FROM PaymentMethod pm WHERE status = 1")
    List<PaymentMethod> findPaymentInUse();

}
