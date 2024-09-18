package com.abbrevio.abbrevio.entity;

import com.abbrevio.abbrevio.utils.VoteId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "votes")
public class Vote {

    @EmbeddedId
    private VoteId id;

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("meaning")
    @JoinColumn(name = "meaning_id")
    private Meaning meaning;
}
