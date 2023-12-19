package com.gudokjoa5.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.gudokjoa5.dto.SubscribeDTO;
import com.gudokjoa5.dto.TotalFeeDTO;

@Mapper
@Repository
public interface SubscribeDao {

	List<SubscribeDTO> getSusbscribeList(long id) throws Exception;
	
	TotalFeeDTO getTotalFee(long id) throws Exception;
	
	SubscribeDTO getSubscribeDetail(long id) throws Exception;
	
	List<SubscribeDTO> getCanSubList(long id) throws Exception;
	
	List<SubscribeDTO> getCanEnrollSubscribe(long id) throws Exception;
}
