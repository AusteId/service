package lt.techin.exam.init;

import lombok.AllArgsConstructor;
import lt.techin.exam.model.Role;
import lt.techin.exam.model.User;
import lt.techin.exam.repository.RoleRepository;
import lt.techin.exam.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class UsersAndRolesLoader implements CommandLineRunner {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    Role userRole = roleRepository.findByName("USER")
            .orElseGet(() -> roleRepository.save(new Role("USER")));

    Role adminRole = roleRepository.findByName("ADMIN")
            .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

    if (userRepository.findByEmail("user@user.com").isEmpty()) {
      User user = new User();
      user.setEmail("user@user.com");
      user.setPassword(passwordEncoder.encode("Password"));
      user.setRoles(Set.of(userRole));
      userRepository.save(user);
    }

    if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
      User admin = new User();
      admin.setEmail("admin@admin.com");
      admin.setPassword(passwordEncoder.encode("Password"));
      admin.setRoles(Set.of(userRole, adminRole));
      userRepository.save(admin);
    }
  }
}
