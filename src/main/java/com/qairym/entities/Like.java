package com.qairym.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
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
}
