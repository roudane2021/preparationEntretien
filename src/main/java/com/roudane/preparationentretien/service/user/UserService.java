package com.roudane.preparationentretien.service.user;

import com.roudane.preparationentretien.domain.user.UserDomain;
import java.util.List;

public interface UserService {

    UserDomain createUser(UserDomain userDomain);

    List<UserDomain> getAllUsers();

    UserDomain getUserById(Long id);

    UserDomain updateUser(Long id, UserDomain userDomain);

    void deleteUser(Long id);
}
