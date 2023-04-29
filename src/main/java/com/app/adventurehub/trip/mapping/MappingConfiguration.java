package com.app.adventurehub.trip.mapping;

import com.app.adventurehub.reservation.mapping.ReservationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("tripMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public TripMapper tripMapper() {
        return new TripMapper();
    }

    @Bean
    public ReservationMapper reservationMapper() { return new ReservationMapper(); }

    @Bean
    public SeasonMapper seasonMapper(){ return new SeasonMapper(); }
}
