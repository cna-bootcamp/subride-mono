package com.subride.dao;

import java.util.ArrayList;
import java.util.List;

import com.subride.dto.UserPayDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.subride.model.Group;
import com.subride.model.UserGroup;

@Mapper
@Repository
public interface GroupDao {
	Group getGroup(long id) throws Exception; // 그룹의 간단한 정보만 가져오기
	
	List<Group> getGroupList(String userId) throws Exception;
	
	int insertGroup(Group group) throws Exception;
	
	int insertUserGroup(UserGroup userGroup) throws Exception;
	
	Group getGroupByGroupName(String groupname, String leaderUserId) throws Exception;
	
	Group getGroupByInvitationCode(String invitationcode) throws Exception;

	List<UserPayDTO> getPayHistory(long id) throws Exception;

	int createPayHistory(ArrayList<UserPayDTO> list) throws Exception;

	boolean isExceedMaxUser(long groupId) throws Exception;
}
