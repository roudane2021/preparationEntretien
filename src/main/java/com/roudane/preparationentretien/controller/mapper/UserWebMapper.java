package com.roudane.preparationentretien.controller.mapper;

import com.roudane.preparationentretien.domain.user.UserDomain;
import com.roudane.preparationentretien.controller.dto.user.UserRequest;
import com.roudane.preparationentretien.controller.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserWebMapper {

    UserDomain toDomain(UserRequest request);

    UserResponse toResponse(UserDomain domain);

    void updateDomainFromRequest(UserRequest request, @MappingTarget UserDomain domain);
}
