package com.example.cityproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "city", schema = "public")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "city_name")
    private String cityName;

    @Column(name = "imageURL",length = 1000)
    private String imageURL;

    @Column(length = 3)
    private Integer plateNumber;

    private String area;

}
