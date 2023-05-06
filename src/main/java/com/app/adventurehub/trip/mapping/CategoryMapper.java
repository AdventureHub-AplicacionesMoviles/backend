package com.app.adventurehub.trip.mapping;


import com.app.adventurehub.shared.mapping.EnhancedModelMapper;
import com.app.adventurehub.trip.domain.model.entity.Category;
import com.app.adventurehub.trip.resource.CategoryResource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@AllArgsConstructor
public class CategoryMapper implements Serializable {

    @Autowired
    EnhancedModelMapper modelMapper;

    public CategoryMapper categoryMapper() {return new CategoryMapper(modelMapper);}


    public CategoryResource toResource(Category model) {
        CategoryResource resource = new CategoryResource();
        resource.setId(model.getId());
        resource.setName(model.getName());
        return resource;
    }
}
