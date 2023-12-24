package com.gudokjoa5.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.gudokjoa5.model.Group;
import com.gudokjoa5.model.UserGroup;

@Mapper
@Repository
public interface GroupDao {
	Group getGroup(long id) throws Exception; // 그룹의 간단한 정보만 가져오기
	
	List<Group> getGroupList(long id) throws Exception;
	
	int insertGroup(Group group) throws Exception;
	
	int insertUserGroup(UserGroup userGroup) throws Exception;
	
	Group getGroupByGroupName(String groupname, long leaderId) throws Exception;
	
	Group getGroupByInvitationCode(String invitationcode) throws Exception;
}
