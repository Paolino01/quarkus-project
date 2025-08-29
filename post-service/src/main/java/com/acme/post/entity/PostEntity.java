package com.social.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class PostEntity {
    @Id
    @GeneratedValue
    public Long id;

    public String content;
    public Long userId;  // Reference to the author of the post
}