package com.app.adventurehub.trip.domain.persistence;

import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.domain.model.enumeration.Seasons;
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
    @Query("SELECT t FROM Trip t WHERE t.season.name = :name")
    List<Trip> findAllBySeason(@Param("name") String name);
    @Query("SELECT t FROM Trip t WHERE t.destination.name = :name")
    List<Trip> findAllByDestination(@Param("name") String name);

    @Query("SELECT t FROM Trip t WHERE (t.season.name = :season OR :season IS NULL) AND (t.destination.name = :destination OR :destination IS NULL) AND  t.price >= :minPrice AND t.price <= :maxPrice")
    List<Trip> findAllByFilter(String destination, Seasons season, Double minPrice, Double maxPrice);
}
