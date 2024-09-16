package com.abbrevio.abbrevio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeaningDTO {

    private Long id;
    private String description;
    private Long countOfVotes;
}
