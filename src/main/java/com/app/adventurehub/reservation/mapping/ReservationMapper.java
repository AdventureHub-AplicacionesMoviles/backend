package com.app.adventurehub.reservation.mapping;

import com.app.adventurehub.reservation.resource.CreateReservationResource;
import com.app.adventurehub.reservation.resource.ReservationResource;
import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.reservation.domain.model.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public ReservationResource toResource(Reservation model){
        ReservationResource resource = new ReservationResource();
        resource.setId(model.getId());
        resource.setNumber_of_people(model.getNumber_of_people());

        return resource;
    }

    public Reservation toModel(CreateReservationResource resource) {
        Reservation reservation = new Reservation();


        return reservation;

    }

    public List<ReservationResource> toResourceList(List<Reservation> modelList){
        return modelList.stream().map(this::toResource).collect(Collectors.toList());
    }
}
