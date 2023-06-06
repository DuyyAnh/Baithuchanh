package com.example.Lab05.validator;

import com.example.Lab05.entity.Category;
import com.example.Lab05.validator.annotation.ValidCategoryId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class ValidCategoryIdValidator implements ConstraintValidator<ValidCategoryId, Category> {
    @Override
    public boolean isValid(Category category, ConstraintValidatorContext context ){
        return category != null && category.getId() != null;
    }
}
