package com.example.library.dto;

import jakarta.persistence.Id;

public class AuthorDTO {
    @Id
    private Integer authorId;

    private String authorName;

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

    public AuthorDTO() {
    }

    public AuthorDTO(Integer authorId, String authorName, String authorLastname) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorLastname = authorLastname;
    }

}
