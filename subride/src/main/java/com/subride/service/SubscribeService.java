package com.subride.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.subride.dto.SubscribeDTO;
import com.subride.dto.SubscribeEnrollDTO;
import com.subride.dto.TotalFeeDTO;



public interface SubscribeService {

	/**
	 * @작성자 : 곽승규
	 * */
	public ResponseEntity <List<SubscribeDTO>> getSusbscribeList(String userId);
	
	public ResponseEntity<TotalFeeDTO> getTotalFee(String userId);
	
	public ResponseEntity<SubscribeDTO> getSubscribeDetail(long id);
	
	public ResponseEntity<List<SubscribeDTO>> getCanSubList(String userId);
	
	public ResponseEntity<List<SubscribeDTO>> getEnrollServicesByCategory(Long categoryId, String userId);
	
	public ResponseEntity<Object> setSubscribeInsert(SubscribeEnrollDTO subscribeEnrollDTO);
}
