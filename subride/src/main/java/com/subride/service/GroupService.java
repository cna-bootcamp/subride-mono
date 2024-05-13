package com.subride.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.subride.dto.GroupCreateDTO;
import com.subride.dto.GroupDTO;
import com.subride.dto.GroupJoinDTO;

public interface GroupService {
	public ResponseEntity <GroupDTO> getGroup(long id);

	public ResponseEntity <List<GroupDTO>> getGroupList(String userId);
	
	public ResponseEntity <Object> insertGroup(GroupCreateDTO groupCreateDTO);
	
	public ResponseEntity <Object> joinGroup(GroupJoinDTO groupJoinDTO);

}
