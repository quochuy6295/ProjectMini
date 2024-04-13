package org.example.mapper;

import org.example.dto.CategoryInfo;
import org.example.entity.Category;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/*
 * Created by Suresh Stalin on 23 / Nov / 2020.
 */

@Mapper(implementationPackage = "mapper.impl")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category categoryInfoToCategory(CategoryInfo categoryInfo);

    @InheritInverseConfiguration
    CategoryInfo categoryToCategoryInfo(Category category);

}
