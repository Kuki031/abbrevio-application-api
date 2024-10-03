package com.abbrevio.abbrevio.payload;

import com.abbrevio.abbrevio.payload.user.UserDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbbreviationDTO {

    private Long id;

    @Pattern(regexp = "^[a-zA-Z]+(?:\\.[a-zA-Z]+)*\\.?(?:\\s[a-zA-Z]+(?:\\.[a-zA-Z]+)*\\.?)*$",
            message = "abbreviation can only contain letters with dots or spaces between, but none in front")
    @NotBlank(message = "abbreviation's name cannot be blank")
    private String name;
    private UserDTO user;
}
