package com.roudane.preparationentretien.mapper;

import com.roudane.preparationentretien.domain.user.UserDomain;
import com.roudane.preparationentretien.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntity toEntity(UserDomain domain);

    UserDomain toDomain(UserEntity entity);
}
