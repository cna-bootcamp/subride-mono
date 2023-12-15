package com.gudokjoa5.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.gudokjoa5.model.User;

@Mapper
@Repository
public interface UserDao {

	User selectUser(long id) throws Exception;
}
