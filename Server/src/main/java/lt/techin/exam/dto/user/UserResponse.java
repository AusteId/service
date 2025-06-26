package lt.techin.exam.dto.user;

import lt.techin.exam.model.Role;

import java.util.Set;

public record UserResponse(
        Long id,
        String email,
        Set<String> roles
) {
}
