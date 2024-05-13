package com.subride.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.subride.entity.User;

public interface UserService {

	public ResponseEntity <List<User>> getUserList();
	public ResponseEntity <User> getUserById(String userId);
	public ResponseEntity <List<User>> getUserListByGroupId(long id);
}
