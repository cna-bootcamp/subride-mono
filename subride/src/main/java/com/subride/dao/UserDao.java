package com.subride.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.subride.dto.LoginRequestDTO;
import com.subride.entity.User;

@Mapper
@Repository
public interface UserDao {

	List<User> selectUserAll() throws Exception;
	
	User selectUser(String userId) throws Exception;
	
	List<User> getUserByGroupId(long id) throws Exception;
	
	User isUserNameExist(String userId) throws Exception;

}
