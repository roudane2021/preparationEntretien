package com.roudane.preparationentretien.service.user;

import com.roudane.preparationentretien.domain.user.User;
import com.roudane.preparationentretien.dto.user.UserRequest;
import com.roudane.preparationentretien.dto.user.UserResponse;
import com.roudane.preparationentretien.exception.DuplicateResourceException;
import com.roudane.preparationentretien.exception.ResourceNotFoundException;
import com.roudane.preparationentretien.mapper.UserMapper;
import com.roudane.preparationentretien.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Un utilisateur avec cet email existe déjà : " + request.email());
        }

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
            .map(userMapper::toResponse)
            .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", id));

        if (!user.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Un utilisateur avec cet email existe déjà : " + request.email());
        }

        userMapper.updateEntity(user, request);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", id);
        }
        userRepository.deleteById(id);
    }
}
