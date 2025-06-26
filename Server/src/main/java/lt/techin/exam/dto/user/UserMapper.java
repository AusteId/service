package lt.techin.exam.dto.user;

import lt.techin.exam.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

  public UserResponse toUserResponse(User user) {
    return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet())
    );
  }

  public User toUser(CreateUserRequest dto) {
    User user = new User();
    user.setEmail(dto.email());
    user.setPassword(dto.password());
    return user;
  }
}
