package com.gudokjoa5.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.gudokjoa5.model.Group;

@Mapper
@Repository
public interface GroupDao {
	Group getGroup(long id) throws Exception; // 그룹의 간단한 정보만 가져오기
	List<Group> getGroupList() throws Exception;
}
