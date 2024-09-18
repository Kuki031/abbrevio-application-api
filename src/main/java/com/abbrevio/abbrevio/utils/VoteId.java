package com.abbrevio.abbrevio.utils;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
@Component
public class VoteId implements Serializable {

    private Long user;
    private Long meaning;
}
