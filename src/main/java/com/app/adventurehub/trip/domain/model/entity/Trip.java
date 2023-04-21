package com.app.adventurehub.trip.domain.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String status;
    private String name;
    private String description;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private Double price;

    private String start_date;
    private String end_date;
}
