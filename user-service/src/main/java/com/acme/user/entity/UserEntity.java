package com.social.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue
    public Long id;

    public String username;
    public String email;
}