package lt.techin.exam.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lt.techin.exam.dto.user.CreateUserRequest;
import lt.techin.exam.dto.user.UserMapper;
import lt.techin.exam.exception.EmailAlreadyExistsException;
import lt.techin.exam.model.Role;
import lt.techin.exam.model.User;
import lt.techin.exam.repository.RoleRepository;
import lt.techin.exam.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;

  public User registerUser(CreateUserRequest dto) {

    if (userRepository.existsByEmail(dto.email())) {
      throw new EmailAlreadyExistsException("Email already exists");
    }

    Role roleUser = roleRepository.findByName("USER").orElseThrow();
    User newUser = userMapper.toUser(dto);
    newUser.setPassword(passwordEncoder.encode(dto.password()));
    newUser.setRoles(Set.of(roleUser));

    return userRepository.save(newUser);
  }

  public User findById(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
  }

  public void deleteById(Long id) {
    if (!userRepository.existsById(id)) {
      throw new EntityNotFoundException("User not found with id: " + id);
    }
    userRepository.deleteById(id);
  }

  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
