package com.app.adventurehub.booking.domain.persistence;

import com.app.adventurehub.booking.domain.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	@Query("SELECT b FROM Booking b WHERE b.user.id = ?1")
	List<Booking> findAllByTravelerId(Long id);

	@Query("SELECT b FROM Booking b WHERE b.trip.user.id = ?1")
	List<Booking> findAllByAgencyId(Long id);

	// Upcoming
	@Query("SELECT b FROM Booking b WHERE b.user.id = :id AND :currentDate <= b.trip.start_date")
	List<Booking> findAllUpcomingBookings(@Param("id") Long id, @Param("currentDate") Date currentDate);

	// Past
	@Query("SELECT b FROM Booking b WHERE b.user.id = :id AND :currentDate > b.trip.start_date")
	List<Booking> findAllPastBookings(@Param("id") Long id, @Param("currentDate") Date currentDate);

}