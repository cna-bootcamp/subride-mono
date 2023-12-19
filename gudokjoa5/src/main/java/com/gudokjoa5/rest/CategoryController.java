package com.gudokjoa5.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gudokjoa5.model.Category;
import com.gudokjoa5.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="Catergory API", description="Catergory API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class CategoryController {

	@Autowired
	private final CategoryService categoryService;
	
	@Operation(operationId="categories", summary="카테고리 목록 가져오기", description="카테고리 목록을 제공합니다.")
	@GetMapping("/categories")
	public ResponseEntity <List<Category>> getCategoryList() {
		
		return categoryService.getCategoryList();
	}
}
