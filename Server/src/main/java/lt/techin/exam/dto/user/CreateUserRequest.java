package lt.techin.exam.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateUserRequest(

        @NotNull(message = "Email cannot be null")
        @NotBlank(message = "Email cannot be empty or consist only of spaces")
        @Pattern(regexp = "^(?=.{3,254}$)(?=.{1,64}@)(?!\\.)(?!.*\\.\\.)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]{1,253}\\.[A-Za-z]{2,}$",
                message = "Invalid email format. It must be in the format 'user@example.com'")
        String email,

        @NotNull(message = "Password cannot be null")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,128}$",
                message = "Password must contain at least one lowercase letter, one uppercase letter and be 8-128 characters long"
        )
        String password
) {
}
