package com.app.adventurehub.payment.domain.model.entity;

import com.app.adventurehub.booking.domain.model.entity.Booking;
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
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String paymentMethod;

    private Double amount;

    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    // @ManyToOne
    // @JoinColumn(name="booking_id", nullable=false)
    // private Booking booking;

}