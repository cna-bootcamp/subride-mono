package com.gudokjoa5.service;


import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gudokjoa5.dao.SubscribeDao;
import com.gudokjoa5.dao.UserDao;
import com.gudokjoa5.dto.LoginRequestDTO;
import com.gudokjoa5.dto.LoginResponseDTO;
import com.gudokjoa5.dto.SubscribeEnrollDTO;
import com.gudokjoa5.model.User;



@Service
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDao userDao; //Dao 객체 이용함.
	@Autowired
	private SubscribeDao subscribeDao; // Dao 객체
	
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

	@Override
	@Transactional
	public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO) {
		LoginResponseDTO loginResponseDTO = null;
		User user = null;
		
		SubscribeEnrollDTO subscribeEnrollDTO1 = null; // 새로운 유저의 경우 구독서비스 디폴트로 2개 넣기
		SubscribeEnrollDTO subscribeEnrollDTO2 = null;
		
		int profileRandom = (int)((Math.random()*10000)%5) + 1; // 1에서 5까지
		int subscribeRandomID1 = 0;
		int subscribeRandomID2 = 0;
		
		//구독서비스 id가 같은게 나오는 걸 방지하기 위해
		while(true) {
			subscribeRandomID1 = (int)((Math.random()*10000)%24) + 1; // 1에서 24까지 (현재는 구독서비스가 24개 있음)
			subscribeRandomID2 = (int)((Math.random()*10000)%24) + 1; // 1에서 24까지			
			
			if (subscribeRandomID1 != subscribeRandomID2) {
				break;
			}
		}
		
		
		try {
			loginResponseDTO = userDao.isUserNameExist(loginRequestDTO);
			if (loginResponseDTO == null) { // 랜덤생성 후, insert
				user = new User(
						0,
						loginRequestDTO.getUserName(),
						bankAccountRandom(), // 함수 만들어 주기 (bank_Account)
						Integer.toString(profileRandom), // 프로필 이미지 랜덤
						1 // is_active
				);
				
				userDao.createUser(user); // insert 했음.
				
				loginResponseDTO = userDao.isUserNameExist(loginRequestDTO);
				
				//default로 구독서비스 2개정도 구독하는 걸로 하기
				subscribeEnrollDTO1 = new SubscribeEnrollDTO(
						loginResponseDTO.getId(),
						subscribeRandomID1, 
						21);
				subscribeEnrollDTO2 = new SubscribeEnrollDTO(
						loginResponseDTO.getId(),
						subscribeRandomID2, 
						21);
				subscribeDao.setSubscribeInsert(subscribeEnrollDTO1);
				subscribeDao.setSubscribeInsert(subscribeEnrollDTO2); // 랜덤으로 구독서비스 insert 끝
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<LoginResponseDTO> (loginResponseDTO, HttpStatus.OK);
	}

	private String bankAccountRandom() {
		int leftLimit = 49; // numeral '1'
		int rightLimit = 57; // letter '9'
		int targetStringLength1 = 6;
		int targetStringLength2 = 2;
		
		Random random = new Random();

		String generatedString1 = random.ints(leftLimit,rightLimit + 1)
		  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		  .limit(targetStringLength1)
		  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		  .toString();
		
		String generatedString2 = random.ints(leftLimit,rightLimit + 1)
			.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			.limit(targetStringLength2)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
		
		String generatedString3 = random.ints(leftLimit,rightLimit + 1)
			.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			.limit(targetStringLength1)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();

		return generatedString1 + "-" +generatedString2 + "-" + generatedString3;
	}
}
