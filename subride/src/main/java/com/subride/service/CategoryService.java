package com.subride.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.subride.model.Category;

public interface CategoryService {
	public ResponseEntity <List<Category>> getCategoryList();
}
