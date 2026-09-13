package com.roudane.preparationentretien.repository;

import com.roudane.preparationentretien.repository.entity.OrderEntity;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Nonnull
    @Override
    @EntityGraph(attributePaths = {"user", "orderLines"})
    @Query("select o from OrderEntity o order by o.createdAt desc")
    List<OrderEntity> findAll();

    @EntityGraph(attributePaths = {"user", "orderLines"})
    @Query("select o from OrderEntity o where o.id = :id")
    Optional<OrderEntity> findByIdWithDetails(@Param("id") Long id);
}
