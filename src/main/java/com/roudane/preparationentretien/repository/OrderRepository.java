package com.roudane.preparationentretien.repository;

import com.roudane.preparationentretien.domain.order.Order;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Nonnull
    @Override
    @EntityGraph(attributePaths = {"user", "orderLines"})
    @Query("select o from Order o order by o.createdAt desc")
    List<Order> findAll();

    @EntityGraph(attributePaths = {"user", "orderLines"})
    @Query("select o from Order o where o.id = :id")
    Optional<Order> findByIdWithDetails(@Param("id") Long id);
}
