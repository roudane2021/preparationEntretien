package com.roudane.preparationentretien.mapper;

import com.roudane.preparationentretien.domain.order.OrderDomain;
import com.roudane.preparationentretien.domain.order.OrderLineDomain;
import com.roudane.preparationentretien.entity.OrderEntity;
import com.roudane.preparationentretien.entity.OrderLineEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserEntityMapper.class})
public interface OrderEntityMapper {

    @Mapping(target = "user", ignore = true)
    OrderEntity toEntity(OrderDomain domain);

    @Mapping(target = "order", ignore = true)
    OrderLineEntity toLineEntity(OrderLineDomain domain);

    @Mapping(target = "userId", source = "user.id")
    OrderDomain toDomain(OrderEntity entity);

    OrderLineDomain toLineDomain(OrderLineEntity entity);

    @AfterMapping
    default void linkOrderLines(@MappingTarget OrderEntity orderEntity) {
        if (orderEntity.getOrderLines() != null) {
            orderEntity.getOrderLines().forEach(line -> line.setOrder(orderEntity));
        }
    }
}
