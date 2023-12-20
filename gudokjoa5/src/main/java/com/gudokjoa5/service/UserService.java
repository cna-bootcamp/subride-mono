package com.gudokjoa5.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.gudokjoa5.model.User;

public interface UserService {

	public ResponseEntity <List<User>> getUserList();
	public ResponseEntity <User> getUserById(long id);
	public ResponseEntity <List<User>> getUserListByGroupId(long id);

}
