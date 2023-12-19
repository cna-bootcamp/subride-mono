package com.gudokjoa5.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.gudokjoa5.dto.SubscribeDTO;



public interface SubscribeService {

	/**
	 * @작성자 : 곽승규
	 * */
	public ResponseEntity <List<SubscribeDTO>> getSusbscribeList(long id);
}
