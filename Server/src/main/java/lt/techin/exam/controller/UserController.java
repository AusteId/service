package lt.techin.exam.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lt.techin.exam.dto.user.CreateUserRequest;
import lt.techin.exam.dto.user.UserMapper;
import lt.techin.exam.dto.user.UserResponse;
import lt.techin.exam.model.User;
import lt.techin.exam.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping(value = "/register")
  public ResponseEntity<UserResponse> addUser(@Valid @RequestBody CreateUserRequest dto) {

    User newUser = userService.registerUser(dto);
    UserResponse savedUser = userMapper.toUserResponse(newUser);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.id())
                            .toUri())
            .body(savedUser);
  }

  @GetMapping("/me")
  public ResponseEntity<UserResponse> getCurrentUser() {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserResponse userResponse = userMapper.toUserResponse(currentUser);
    return ResponseEntity.ok(userResponse);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(userMapper.toUserResponse(userService.findById(id)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteById(id);
    return ResponseEntity.noContent().build();
  }


}
