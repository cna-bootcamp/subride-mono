package com.gudokjoa5.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gudokjoa5.dao.SubscribeDao;
import com.gudokjoa5.dto.SubscribeDTO;
import com.gudokjoa5.dto.SubscribeEnrollDTO;
import com.gudokjoa5.dto.TotalFeeDTO;

/**
 * @작성자 : 곽승규
 * */

@Service
public class SubscribeServiceImpl implements SubscribeService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SubscribeDao subscribeDao; // Dao 객체
	
	
	/**
	 * @작성자 : 곽승규
	 * @param : id : 사용자의 아이디
	 * */
	@Override
	public ResponseEntity<List<SubscribeDTO>> getSusbscribeList(long id) {
		List<SubscribeDTO> list = null;
		
		try {
			log.info("Start db select");
			list = subscribeDao.getSusbscribeList(id);
			System.out.println("list : " + list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<SubscribeDTO>> (list, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<TotalFeeDTO> getTotalFee(long id) {
		
		TotalFeeDTO totalFeeDTO = null;
		try {
			log.info("Start db select");
			totalFeeDTO = subscribeDao.getTotalFee(id);
			System.out.println("list : " + totalFeeDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<TotalFeeDTO> (totalFeeDTO, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<SubscribeDTO> getSubscribeDetail(long id) {
		SubscribeDTO subscribeDTO = null;
		try {
			subscribeDTO = subscribeDao.getSubscribeDetail(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity <SubscribeDTO> (subscribeDTO, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<List<SubscribeDTO>> getCanSubList(long id) {
		List<SubscribeDTO> list = null;
		try {
			list = subscribeDao.getCanSubList(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<SubscribeDTO>> (list, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<List<SubscribeDTO>> getCanEnrollSubscribe(long id) {
		List<SubscribeDTO> list = null;
		try {
			list = subscribeDao.getCanEnrollSubscribe(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<SubscribeDTO>> (list, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<Object> setSubscribeInsert(SubscribeEnrollDTO subscribeEnrollDTO) {
		log.info("Start db insert");
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			System.out.println(subscribeEnrollDTO);
			int re  = subscribeDao.setSubscribeInsert(subscribeEnrollDTO);
			log.debug("result :"+ re);
			if (re > 0) {
	            map.put("message", "사용자의 새로운 구독서비스 등록 성공");
	            return new ResponseEntity<Object>(map, HttpStatus.OK);  // 성공 시 메세지를 설정하고 바로 반환
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		// 실패할 경우에만 실패 메세지를 설정
	    map.put("message", "사용자의 새로운 구독서비스 등록 실패");
	    return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	
}
