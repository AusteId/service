package lt.techin.exam.dto.employee;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateEmployeeRequest(
        @NotNull(message = "Firstname cannot be null")
        @NotBlank(message = "Firstname cannot be empty or consist only of spaces")
        @Size(min = 2, max = 100, message = "Firstname must be between 2 and 100 characters")
        @Pattern(regexp = "^[A-Za-zĄČĘĖĮŠŲŪŽąčęėįšųūž\\s'-]+$", message = "Firstname can only contain letters")
        String firstname,

        @NotNull(message = "Lastname cannot be null")
        @NotBlank(message = "Lastname cannot be empty or consist only of spaces")
        @Size(min = 2, max = 100, message = "Lastname must be between 2 and 100 characters")
        @Pattern(regexp = "^[A-Za-zĄČĘĖĮŠŲŪŽąčęėįšųūž\\s'-]+$", message = "Lastname can only contain letters")
        String lastname,

        @Past(message = "Birthdate must be in the past")
        LocalDate birthDate,

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

        @NotNull(message = "Specialization cannot be null")
        @NotBlank(message = "Specialization cannot be empty or consist only of spaces")
        @Size(min = 2, max = 500, message = "Specialization must be between 2 and 500 characters")
        String specialization,

        @Size(max = 1000, message = "Description must be less than 1000 characters")
        String description
) {
}
