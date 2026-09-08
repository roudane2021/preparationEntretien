package com.roudane.preparationentretien.service.user;

import com.roudane.preparationentretien.dto.user.UserRequest;
import com.roudane.preparationentretien.dto.user.UserResponse;
import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}
