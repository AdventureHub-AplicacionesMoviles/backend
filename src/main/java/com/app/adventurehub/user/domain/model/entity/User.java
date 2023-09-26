package com.app.adventurehub.user.domain.model.entity;

import com.app.adventurehub.chat.domain.model.entity.Conversation;
import com.app.adventurehub.notification.domain.model.entity.Notification;
import com.app.adventurehub.payment.domain.model.entity.Payment;
import com.app.adventurehub.booking.domain.model.entity.Booking;
import com.app.adventurehub.shared.domain.model.AuditModel;
import com.app.adventurehub.trip.domain.model.entity.Review;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends AuditModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String password;
	private String username;
	private String mobile_token;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Booking> bookings = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Payment> payments = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Notification> notifications = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Review> reviews = new HashSet<>();

	@ManyToMany(mappedBy = "users")
	private Set<Conversation> conversations = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Trip> trips = new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();
}