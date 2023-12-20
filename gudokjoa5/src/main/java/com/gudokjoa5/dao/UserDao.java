package com.gudokjoa5.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.gudokjoa5.dto.LoginRequestDTO;
import com.gudokjoa5.dto.LoginResponseDTO;
import com.gudokjoa5.model.User;

@Mapper
@Repository
public interface UserDao {

	List<User> selectUserAll() throws Exception;
	
	User selectUser(long id) throws Exception;
	
	List<User> getUserByGroupId(long id) throws Exception;
	
	LoginResponseDTO isUserNameExist(LoginRequestDTO loginRequestDTO) throws Exception;
	
	int createUser(User user) throws Exception;
}
