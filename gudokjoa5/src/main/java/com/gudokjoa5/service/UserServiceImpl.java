package com.gudokjoa5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gudokjoa5.dao.UserDao;
import com.gudokjoa5.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao; //Dao 객체 이용함.
	
	@Override
	public ResponseEntity<User> getUserById(long id) {
		User user = null;
		
		try {
			user = userDao.selectUser(id);
			System.out.println(user);
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<User> (user, HttpStatus.OK);
	}

}
