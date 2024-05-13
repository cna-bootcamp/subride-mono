package com.subride.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.subride.dao.SubscribeDao;
import com.subride.dao.UserDao;
import com.subride.entity.User;



@Service
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDao userDao; //Dao 객체 이용함.
	@Autowired
	private SubscribeDao subscribeDao; // Dao 객체
	
	@Override
	public ResponseEntity<User> getUserById(String userId) {
		User user = null;
		
		try {
			user = userDao.selectUser(userId);
			System.out.println(user);
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<User> (user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<User>> getUserList() {
		List<User> list = null;
		try {
			log.info("Start db select");
			list = userDao.selectUserAll();
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>> (list, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<User>> getUserListByGroupId(long id) {
		List<User> list = null;
		try {
			log.info("Start db select");
			list = userDao.getUserByGroupId(id);
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>> (list, HttpStatus.OK);
	}

}
