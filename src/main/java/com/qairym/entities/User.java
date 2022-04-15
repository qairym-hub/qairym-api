package com.qairym.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String about;

    private Date createdAt;

    @OneToMany(mappedBy = "author")
    //@JsonManagedReference(value = "user-posts")
    @JsonIgnore
    private Collection<Post> posts;

    @OneToMany(mappedBy = "author")
    //@JsonManagedReference(value = "user-comments")
    @JsonIgnore
    private Collection<Comment> comments;

    @ManyToMany(
            mappedBy = "following",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private Collection<User> followers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = {
                    @JoinColumn(name = "follower_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "following_id")
            }
    )
    @JsonIgnore
    private Collection<User> following;

    @ManyToOne
    @JoinColumn(
            name = "location_id",
            nullable = false
    )
    @JsonBackReference(value = "city-users")
    private City location;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
 