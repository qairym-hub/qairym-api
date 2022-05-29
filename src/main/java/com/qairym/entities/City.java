package com.qairym.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.qairym.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cities")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class City {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long cityId;

    private String name;

    @OneToMany(mappedBy = "location")
    @JsonManagedReference(value = "city-users")
    private Collection<User> users;
}

