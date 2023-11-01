package com.app.adventurehub.trip.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trip_details")
public class TripDetails {

	public TripDetails(String imageUrl, Trip trip) {
		this.imageUrl = imageUrl;
		this.trip = trip;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String imageUrl;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "trip_id", nullable = false)
	@JsonIgnore
	private Trip trip;
}
