package com.app.adventurehub.trip.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("tripMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public TripMapper tripMapper() {
        return new TripMapper();
    }
}
