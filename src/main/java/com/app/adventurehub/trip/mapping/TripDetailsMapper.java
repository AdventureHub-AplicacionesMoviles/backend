package com.app.adventurehub.trip.mapping;

import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.trip.domain.model.entity.Trip;
import com.app.adventurehub.trip.domain.model.entity.TripDetails;
import com.app.adventurehub.trip.resource.TripDetailsResource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TripDetailsMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EnhancedModelMapper modelMapper;

    public TripDetailsMapper tripDetailsMapper() {
        return new TripDetailsMapper(modelMapper);
    }

    public TripDetails toModel(String imageUrl, Trip trip){
        TripDetails model = new TripDetails(imageUrl,trip);
        return model;
    }

    public String toResource(TripDetails model){
        return model.getImageUrl();
    }

    public Set<String> toResourceList(Set<TripDetails> modelList){
        return modelList.stream().map(this::toResource).collect(Collectors.toSet());
    }

}
