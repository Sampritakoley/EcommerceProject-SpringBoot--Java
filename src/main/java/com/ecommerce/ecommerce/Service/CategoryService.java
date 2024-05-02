package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Entities.Category;
import com.ecommerce.ecommerce.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getCategory(Pageable pePageable){
        return categoryRepository.getAllCategories(pePageable);
    }
}
