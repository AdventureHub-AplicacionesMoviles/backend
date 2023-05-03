package com.app.adventurehub.trip.mapping;

import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.trip.domain.model.entity.Season;
import com.app.adventurehub.trip.resource.SeasonResource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SeasonMapper implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private EnhancedModelMapper modelMapper;

    public SeasonMapper seasonMapper() {
        return new SeasonMapper(modelMapper);
    }

    public SeasonResource toResource(Season model) {
        SeasonResource resource = new SeasonResource();
        resource.setId(model.getId());
        resource.setName(model.getName());
        return resource;
    }

    public List<SeasonResource> toResourceList(List<Season> modelList){
        return modelList.stream().map(this::toResource).collect(Collectors.toList());
    }
}
