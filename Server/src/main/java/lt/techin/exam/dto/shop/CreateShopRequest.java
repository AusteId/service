package lt.techin.exam.dto.shop;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateShopRequest(
        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "Name cannot be empty or consist only of spaces")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        @Pattern(regexp = "^[A-Za-zĄČĘĖĮŠŲŪŽąčęėįšųūž0-9\\s'-]+$", message = "Name can only contain letters and numbers")
        String name,

        @NotNull(message = "Address cannot be null")
        @NotBlank(message = "Address cannot be empty or consist only of spaces")
        @Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters")
        @Pattern(regexp = "^[A-Za-z0-9\\s,.-]+$", message = "Address can only contain letters and numbers")
        String address,

        @NotNull(message = "Phone cannot be null")
        @NotBlank(message = "Phone cannot be empty or consist only of spaces")
        @Size(min = 4, max = 15, message = "Phone must be between 4 and 15 characters")
        String phone,

        @NotNull(message = "Email cannot be null")
        @NotBlank(message = "Email cannot be empty or consist only of spaces")
        @Pattern(regexp = "^(?=.{3,254}$)(?=.{1,64}@)(?!\\.)(?!.*\\.\\.)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]{1,253}\\.[A-Za-z]{2,}$",
                message = "Invalid email format. It must be in the format 'user@example.com'")
        String email,

        @NotNull(message = "City cannot be null")
        @NotBlank(message = "City cannot be empty or consist only of spaces")
        @Size(min = 2, max = 100, message = "City must be between 2 and 100 characters")
        String city,

        @NotNull(message = "Description cannot be null")
        @NotBlank(message = "Description cannot be empty or consist only of spaces")
        @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
        String description,

        @NotNull(message = "Working hours cannot be null")
        @NotBlank(message = "Working hours cannot be empty or consist only of spaces")
        @Size(min = 5, max = 200, message = "Working hours must be between 5 and 200 characters")
        String workingHours,

        @NotNull(message = "Image path cannot be null")
        @NotBlank(message = "Image path cannot be empty or consist only of spaces")
        String imagePath,

        List<Long> employeesIds
) {
}
