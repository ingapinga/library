package com.example.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @Column(name="author_id", nullable=false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_gen")
    @SequenceGenerator(name = "author_id_gen", sequenceName = "author_id_gen",  allocationSize=1)
    private Integer authorId;

    @Column(name="author_name", nullable=false)
    private String authorName;

    @Column(name="author_lastname", nullable=false)
    private String authorLastname;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorLastname() {
        return authorLastname;
    }

    public void setAuthorLastname(String authorLastname) {
        this.authorLastname = authorLastname;
    }

    public Author() {
    }

    public Author(Integer authorId, String authorName, String authorLastname) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorLastname = authorLastname;
    }

}
