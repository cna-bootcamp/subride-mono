package com.gudokjoa5.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.gudokjoa5.dto.SubscribeListDTO;

@Mapper
@Repository
public interface SubscribeDao {

	List<SubscribeListDTO> getSusbscribeList(long id) throws Exception;
}
