package com.abbrevio.abbrevio.payload;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    @NotBlank(message = "username cannot be blank")
    @Size(min = 5, message = "username must be at least 5 characters long")
    @Pattern(regexp = "^\\S+$", message = "username cannot contain spaces")
    private String username;

    @NotBlank(message = "e-mail cannot be blank")
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "invalid e-mail format")
    private String email;

    @NotBlank(message = "password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "password must contain minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character"
    )
    private String password;
    private Integer departmentId;
}