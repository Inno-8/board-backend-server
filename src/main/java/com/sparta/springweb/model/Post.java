package com.sparta.springweb.model;

import lombok.Getter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    private String filePath;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

    protected Post() {}

    private Post(String username, String title, String content, String filePath) {
        this.name = username;
        this.title = title;
        this.content = content;
        this.filePath = filePath;
    }

    public static Post createPost(String username, String title, String content, String filePath) {
        return new Post(username, title, content, filePath);
    }

    public void updatePost(String title, String content, String filePath) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;
    }
}
