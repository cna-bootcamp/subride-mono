package com.gudokjoa5.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.gudokjoa5.dto.SubscribeDTO;

@Mapper
@Repository
public interface SubscribeDao {

	List<SubscribeDTO> getSusbscribeList(long id) throws Exception;
}
