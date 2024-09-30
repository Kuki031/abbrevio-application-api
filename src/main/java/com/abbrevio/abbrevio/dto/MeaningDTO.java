package com.abbrevio.abbrevio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeaningDTO {

    private Long id;

    @NotBlank(message = "description of meaning cannot be blank")
    private String description;
    private Long countOfVotes;
    private UserDTO user;
    private Long abbreviationId;
    private List<CommentDTO> comments;
}
