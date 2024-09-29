package com.abbrevio.abbrevio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meanings", uniqueConstraints = {@UniqueConstraint(columnNames = "description")})
public class Meaning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Long countOfVotes;

    @ManyToOne
    @JoinColumn(name = "abbreviation_id")
    private Abbreviation abbreviation;

    @OneToMany(mappedBy = "meaning", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "meaning", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Vote> vote;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public void setCountOfVotes(boolean isVoting)
    {
        if (isVoting) {
            this.countOfVotes++;
        } else this.countOfVotes--;
    }

    public void setCountOfVotesInitially()
    {
        this.countOfVotes = (long) 0;
    }
}
