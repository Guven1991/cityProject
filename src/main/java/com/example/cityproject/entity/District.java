package com.example.cityproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "district", schema = "public")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "district_name")
    private String districtName;

    @ManyToOne
    @JoinColumn(name="city_id", nullable=false)
    private City city;
}
