package com.app.adventurehub.booking.domain.model.entity;

import com.app.adventurehub.booking.enums.BookingStatus;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.user.domain.model.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private Long numberOfPeople;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @ManyToOne
    @JoinColumn(name="trip_id", nullable=false)
    private Trip trip;

    // @OneToMany(mappedBy = "booking", cascade = CascadeType.PERSIST)
    // private Set<Payment> payments = new HashSet<>();
}