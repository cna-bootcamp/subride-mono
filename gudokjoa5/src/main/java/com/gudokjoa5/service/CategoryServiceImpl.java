package com.gudokjoa5.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gudokjoa5.dao.CategoryDao;
import com.gudokjoa5.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CategoryDao categoryDao; 
	
	@Override
	public ResponseEntity<List<Category>> getCategoryList() {
		List<Category> list = null;
		
		try {
			log.info("Start db select");
			list = categoryDao.getCategoryList();
			System.out.println("list : " + list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<Category>> (list, HttpStatus.OK);
	}

	
}
