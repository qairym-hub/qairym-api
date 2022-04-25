package com.qairym.entities;

import com.qairym.entities.post.Post;
import com.qairym.entities.user.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class Like {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long likeId;

    @ManyToOne
    @JoinColumn(
        name = "liker_id",
        nullable = false
    )
    private User liker;

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            nullable = false
    )
    //@JsonBackReference(value = "post-likes")
    private Post post;
}
