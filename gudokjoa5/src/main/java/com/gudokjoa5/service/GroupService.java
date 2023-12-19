package com.gudokjoa5.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.gudokjoa5.dto.GroupDTO;

public interface GroupService {
	public ResponseEntity <GroupDTO> getGroup(long id);

	public ResponseEntity <List<GroupDTO>> getGroupList(long id); 
}
