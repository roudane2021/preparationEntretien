package com.roudane.preparationentretien.service.user;

import com.roudane.preparationentretien.domain.user.UserDomain;
import com.roudane.preparationentretien.entity.UserEntity;
import com.roudane.preparationentretien.exception.DuplicateResourceException;
import com.roudane.preparationentretien.exception.ResourceNotFoundException;
import com.roudane.preparationentretien.mapper.UserEntityMapper;
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
    private final UserEntityMapper userEntityMapper;

    @Override
    @Transactional
    public UserDomain createUser(UserDomain userDomain) {
        if (userRepository.existsByEmail(userDomain.getEmail())) {
            throw new DuplicateResourceException("Un utilisateur avec cet email existe déjà : " + userDomain.getEmail());
        }

        UserEntity userEntity = userEntityMapper.toEntity(userDomain);
        UserEntity savedEntity = userRepository.save(userEntity);
        return userEntityMapper.toDomain(savedEntity);
    }

    @Override
    public List<UserDomain> getAllUsers() {
        return userRepository.findAll().stream()
            .map(userEntityMapper::toDomain)
            .toList();
    }

    @Override
    public UserDomain getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return userEntityMapper.toDomain(userEntity);
    }

    @Override
    @Transactional
    public UserDomain updateUser(Long id, UserDomain userDomain) {
        UserEntity existingEntity = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", id));

        if (!existingEntity.getEmail().equals(userDomain.getEmail()) && userRepository.existsByEmail(userDomain.getEmail())) {
            throw new DuplicateResourceException("Un utilisateur avec cet email existe déjà : " + userDomain.getEmail());
        }

        existingEntity.setFirstName(userDomain.getFirstName());
        existingEntity.setLastName(userDomain.getLastName());
        existingEntity.setEmail(userDomain.getEmail());
        existingEntity.setPhone(userDomain.getPhone());

        UserEntity updatedEntity = userRepository.save(existingEntity);
        return userEntityMapper.toDomain(updatedEntity);
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
