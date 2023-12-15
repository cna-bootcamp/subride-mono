package com.gudokjoa5.service;

import org.springframework.http.ResponseEntity;

import com.gudokjoa5.model.User;

public interface UserService {

	public ResponseEntity <User> getUserById(long id);
}
