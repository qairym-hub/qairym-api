package com.qairym.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class Group {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    private String description;

    @Deprecated
    private String location;

    @ManyToOne
    @JoinColumn(
        name = "user_id",
        nullable = false
    )
    private User owner;

    @ManyToMany(mappedBy = "subscriptions")
    private Collection<User> subscribers;

    @OneToMany(mappedBy = "author")
    private Collection<Post<Group>> posts;
}
