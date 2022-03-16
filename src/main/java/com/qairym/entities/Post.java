package com.qairym.entities;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @JsonBackReference(value = "user-posts")
    private User author;

    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    private Collection<Comment> comments;
}
 