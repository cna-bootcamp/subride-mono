package com.gudokjoa5.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gudokjoa5.dao.GroupDao;
import com.gudokjoa5.dao.SubscribeDao;
import com.gudokjoa5.dao.UserDao;
import com.gudokjoa5.dto.GroupDTO;
import com.gudokjoa5.dto.SubscribeDTO;
import com.gudokjoa5.model.Group;
import com.gudokjoa5.model.User;

@Service
public class GroupServiceImpl implements GroupService {
	
private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private GroupDao groupDao; // Dao 객체
	
	@Autowired
	private UserDao userDao; // Dao 객체
	
	@Autowired
	private SubscribeDao subscribeDao; // Dao 객체
	
	@Override
	@Transactional
	public ResponseEntity<GroupDTO> getGroup(long id) {
		GroupDTO groupDTO = null;
		Group group = null;
		List<User> users = null;
		SubscribeDTO subscribeDTO = null;
		try {
			group = groupDao.getGroup(id);
			long subscribeId = group.getSubscribeserviceId();
			subscribeDTO = subscribeDao.getSubscribeDetail(subscribeId);
			//users = userDao.

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<GroupDTO> (groupDTO, HttpStatus.OK);
	}

}
