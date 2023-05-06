package com.app.adventurehub.trip.domain.persistence;

import com.app.adventurehub.trip.domain.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAllByPrice(Double price);
    @Query("SELECT t FROM Trip t WHERE t.name = :name")
    Trip findByName(@Param("name") String name);
    List<Trip> findAllBySeason(String name);
}
