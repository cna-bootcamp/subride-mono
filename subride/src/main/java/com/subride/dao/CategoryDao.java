package com.subride.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.subride.model.Category;


@Mapper
@Repository
public interface CategoryDao {
	List<Category> getCategoryList() throws Exception;
}
