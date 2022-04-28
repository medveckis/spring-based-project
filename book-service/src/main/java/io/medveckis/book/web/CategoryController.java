package io.medveckis.book.web;

import io.medveckis.book.service.CategoryService;
import io.medveckis.book.web.dto.CategoryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController(value = "categoryController")
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryData> getAllCategories() {
        return categoryService.getAllCategories()
                .stream()
                .map(category -> new CategoryData(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

}