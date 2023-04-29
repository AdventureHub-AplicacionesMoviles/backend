package com.app.adventurehub.trip.domain.persistence;

import com.app.adventurehub.trip.domain.model.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
}
