package com.app.adventurehub.trip.resource;

import com.app.adventurehub.user.resource.UserResource;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResource {
    private Long id;
    private String comment;
    private int rating;
    private UserResource user;
    private Date createdAt;
    private Date updatedAt;
}
