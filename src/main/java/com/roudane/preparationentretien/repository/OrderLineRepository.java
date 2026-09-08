package com.roudane.preparationentretien.repository;

import com.roudane.preparationentretien.domain.order.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
