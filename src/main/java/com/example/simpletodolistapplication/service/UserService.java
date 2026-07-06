package com.example.simpletodolistapplication.service;

import com.example.simpletodolistapplication.assembler.UserAssembler;
import com.example.simpletodolistapplication.dto.UserDTO;
import com.example.simpletodolistapplication.dto.UserRegistrationDTO;
import com.example.simpletodolistapplication.model.User;
import com.example.simpletodolistapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        return userAssembler.toDTOList(userRepository.findAll());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userAssembler::toDTO);
    }

    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userAssembler::toDTO);
    }

    public UserDTO registerUser(UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RuntimeException("A user with email '" + registrationDTO.getEmail() + "' already exists");
        }

        User user = userAssembler.toEntity(registrationDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        return userAssembler.toDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UserRegistrationDTO registrationDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (!existingUser.getEmail().equals(registrationDTO.getEmail()) &&
                userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RuntimeException("A user with email '" + registrationDTO.getEmail() + "' already exists");
        }

        userAssembler.updateEntity(existingUser, registrationDTO);

        // The assembler copies the raw password onto the entity only when one was supplied.
        // Re-encode it here before persisting; otherwise the existing hash is left untouched.
        if (registrationDTO.getPassword() != null && !registrationDTO.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        }

        User savedUser = userRepository.save(existingUser);
        return userAssembler.toDTO(savedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Verifies an email/password combination.
     * Returns the matching UserDTO if the credentials are valid, otherwise empty.
     */
    public Optional<UserDTO> authenticate(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .map(userAssembler::toDTO);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
