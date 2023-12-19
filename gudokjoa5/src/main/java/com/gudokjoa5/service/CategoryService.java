package com.gudokjoa5.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.gudokjoa5.model.Category;

public interface CategoryService {
	public ResponseEntity <List<Category>> getCategoryList();
}
