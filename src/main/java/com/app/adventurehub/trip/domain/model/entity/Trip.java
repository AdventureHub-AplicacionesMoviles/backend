package com.app.adventurehub.trip.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String name;
    private String description;

    @Column(precision = 10, scale = 2)
    private Double price;

    private Date start_date;
    private Date end_date;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "season_id", nullable = false)
    @JsonIgnore
    private Season season;
}
