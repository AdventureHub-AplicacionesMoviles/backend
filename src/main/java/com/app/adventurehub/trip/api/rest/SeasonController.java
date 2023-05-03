package com.app.adventurehub.trip.api.rest;

import com.app.adventurehub.trip.domain.service.SeasonService;

import com.app.adventurehub.trip.mapping.SeasonMapper;

import com.app.adventurehub.trip.resource.SeasonResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seasons")
@AllArgsConstructor
public class SeasonController {

    private final SeasonService seasonService;
    private final SeasonMapper seasonMapper;

    @GetMapping
    public List<SeasonResource> getAllSeasons(){
        return seasonMapper.toResourceList(seasonService.getAll());
    }

}
