package com.qairym.entities;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    private String password;

    private String about;

    @Deprecated
    private String location;

    private Date createdAt;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference(value = "user-posts")
    private Collection<Post> posts;

    @ManyToMany(
        mappedBy = "following",
        cascade = CascadeType.ALL
    )
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
    private Collection<User> following;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference(value = "user-comments")
    private Collection<Comment> comments;
}
 