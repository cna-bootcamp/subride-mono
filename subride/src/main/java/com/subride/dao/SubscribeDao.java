package com.subride.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.subride.dto.SubscribeDTO;
import com.subride.dto.SubscribeEnrollDTO;
import com.subride.dto.TotalFeeDTO;

@Mapper
@Repository
public interface SubscribeDao {

	List<SubscribeDTO> getSusbscribeList(String userId) throws Exception;
	
	TotalFeeDTO getTotalFee(String userId) throws Exception;
	
	SubscribeDTO getSubscribeDetail(long id) throws Exception;
	
	SubscribeDTO getSubscribeByName(String name) throws Exception;

	List<SubscribeDTO> getCanSubList(String userId) throws Exception;
	
	List<SubscribeDTO> getCanEnrollSubscribe(String userId, Long categoryId) throws Exception;

	boolean isSubscribed(String userId, long subscribeId) throws Exception;

	int setSubscribeInsert(SubscribeEnrollDTO subscribeEnrollDTO) throws Exception;

}
