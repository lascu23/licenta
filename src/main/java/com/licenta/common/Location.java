package com.licenta.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    protected String name;

    protected String address;

    protected String city;

    protected String country;

    @Lob
    @Column(name = "profile_picture")
    protected byte[] profileImage;
}
