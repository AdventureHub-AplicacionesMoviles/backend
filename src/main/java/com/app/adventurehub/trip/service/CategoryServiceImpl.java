package com.app.adventurehub.trip.service;

import com.app.adventurehub.trip.domain.model.entity.Category;
import com.app.adventurehub.trip.domain.persistence.CategoryRepository;
import com.app.adventurehub.trip.domain.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;

    private static String[] DEFAULT_CATEGORY = {"Adventure", "Beach", "City Break", "Cultural", "Family", "Honeymoon", "Luxury", "Nature", "Safari", "Sports", "Wellness"};

    @Override
    public void seed() {
        Arrays.stream(DEFAULT_CATEGORY)
                .forEach(name -> {
									if(!categoryRepository.existsByName(name)) {
                        categoryRepository.save((new Category()).withName(name));
                    }
                });
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
