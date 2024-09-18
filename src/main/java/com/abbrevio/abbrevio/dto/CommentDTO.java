package com.abbrevio.abbrevio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;

    @NotBlank(message = "comment cannot be blank")
    private String content;
    private UserDTO user;
}
