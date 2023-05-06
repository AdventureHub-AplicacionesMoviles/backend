package com.app.adventurehub.trip.service;

import com.app.adventurehub.shared.exception.ResourceValidationException;
import com.app.adventurehub.trip.domain.model.entity.Destination;
import com.app.adventurehub.trip.domain.persistence.DestinationRepository;
import com.app.adventurehub.trip.domain.service.DestinationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class DestinationServiceImpl implements DestinationService {
    @Autowired
    private final DestinationRepository destinationRepository;
    private final Validator validator;
    private static final String ENTITY = "Destinations";

    private static Destination[] destinations = {
            new Destination("Machu Picchu", "An Incan citadel set high in the Andes Mountains in Peru, above the Urubamba River valley."),
            new Destination("Amazon Rainforest", "The Amazon Rainforest is the largest tropical rainforest in the world, located in South America."),
            new Destination("Nazca Lines", "A series of ancient geoglyphs located in the Nazca Desert in southern Peru."),
            new Destination("Colca Canyon", "A canyon located in the Andes Mountains, known for its hiking trails and birdwatching opportunities."),
            new Destination("Lake Titicaca", "The highest navigable lake in the world, located on the border of Peru and Bolivia."),
            new Destination("Huacachina", "An oasis in the desert of southwestern Peru, known for its sand dunes and opportunities for sandboarding and dune buggy rides.")
    };

    @Override
    public void seed() {
        for (Destination destination : destinations) {
            if(!destinationRepository.existsByName(destination.getName())) {
                destinationRepository.save(destination);
            }
        }
    }

    @Override
    public List<Destination> getAll() {
        return destinationRepository.findAll();
    }

    @Override
    public Destination create(Destination destination) {
        Set<ConstraintViolation<Destination>> violations = validator.validate(destination);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        Destination destinationWithName = destinationRepository.findByName(destination.getName());

        if(destinationWithName != null)
            throw new ResourceValidationException(ENTITY, "Name already exists");

        return destinationRepository.save(destination);
    }


}
