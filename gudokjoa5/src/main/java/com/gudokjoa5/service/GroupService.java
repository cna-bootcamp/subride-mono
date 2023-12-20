package com.gudokjoa5.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.gudokjoa5.dto.GroupCreateDTO;
import com.gudokjoa5.dto.GroupDTO;
import com.gudokjoa5.dto.GroupJoinDTO;

public interface GroupService {
	public ResponseEntity <GroupDTO> getGroup(long id);

	public ResponseEntity <List<GroupDTO>> getGroupList(long id); 
	
	public ResponseEntity <String> insertGroup(GroupCreateDTO groupCreateDTO);
	
	public ResponseEntity <String> joinGroup(GroupJoinDTO groupJoinDTO);
}
