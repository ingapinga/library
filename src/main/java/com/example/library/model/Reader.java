package com.example.library.model;

import jakarta.persistence.*;

@Entity
public class Reader {
    @Id
    @Column(name="reader_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reader_id_gen")
    @SequenceGenerator(name = "reader_id_gen", sequenceName = "reader_id_gen",  allocationSize=1)
    private Integer readerId;

    @Column(name="reader_name", nullable = false)
    private String readerName;

    @Column(name="reader_lastname", nullable = false)
    private String readerLastname;

    @Column(name="reader_phone", nullable = false, unique = true)
    private String  readerPhone;

    public Integer getReaderId() {
        return readerId;
    }

    public void setReaderId(Integer readerId) {
        this.readerId = readerId;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReaderLastname() {
        return readerLastname;
    }

    public void setReaderLastname(String readerLastname) {
        this.readerLastname = readerLastname;
    }

    public String getReaderPhone() {
        return readerPhone;
    }

    public void setReaderPhone(String readerPhone) {
        this.readerPhone = readerPhone;
    }

    public Reader() {
    }

    public Reader(Integer readerId, String readerName, String readerLastname, String readerPhone) {
        this.readerId = readerId;
        this.readerName = readerName;
        this.readerLastname = readerLastname;
        this.readerPhone = readerPhone;
    }
}
