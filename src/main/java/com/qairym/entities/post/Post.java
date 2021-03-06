package com.qairym.entities.post;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.qairym.entities.Comment;
import com.qairym.entities.Like;
import com.qairym.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;
    private String text;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(
        name = "author_id",
        nullable = false
    )
    //@JsonBackReference(value = "user-posts")
    private User author;

    @OneToMany(mappedBy = "post")
    //@JsonManagedReference(value = "post-comments")
    @JsonIgnore
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "post")
    //@JsonManagedReference(value = "post-likes")
    @JsonIgnore
    private Collection<Like> likes;
}
 