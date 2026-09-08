package com.roudane.preparationentretien.mapper;

import com.roudane.preparationentretien.domain.order.OrderDomain;
import com.roudane.preparationentretien.domain.order.OrderLineDomain;
import com.roudane.preparationentretien.dto.order.OrderLineRequest;
import com.roudane.preparationentretien.dto.order.OrderLineResponse;
import com.roudane.preparationentretien.dto.order.OrderRequest;
import com.roudane.preparationentretien.dto.order.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderWebMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "orderLines", source = "lines")
    OrderDomain toDomain(OrderRequest request);

    @Mapping(target = "id", ignore = true)
    OrderLineDomain toLineDomain(OrderLineRequest request);

    @Mapping(target = "customerName", source = "domain", qualifiedByName = "formatCustomerName")
    @Mapping(target = "lines", source = "orderLines")
    OrderResponse toResponse(OrderDomain domain);

    @Mapping(target = "totalAmount", expression = "java(domain.getLineTotal())")
    OrderLineResponse toLineResponse(OrderLineDomain domain);

    @Named("formatCustomerName")
    default String formatCustomerName(OrderDomain domain) {
        if (domain == null || domain.getUser() == null) {
            return null;
        }
        String firstName = domain.getUser().getFirstName() != null ? domain.getUser().getFirstName() : "";
        String lastName = domain.getUser().getLastName() != null ? domain.getUser().getLastName() : "";
        return (firstName + " " + lastName).trim();
    }
}
