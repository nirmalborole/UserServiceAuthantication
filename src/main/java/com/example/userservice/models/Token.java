package com.example.userservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String value;
    @ManyToOne
    private User user;
    private int expireAt;

}
