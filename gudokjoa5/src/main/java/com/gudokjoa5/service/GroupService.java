package com.gudokjoa5.service;

import org.springframework.http.ResponseEntity;

import com.gudokjoa5.dto.GroupDTO;

public interface GroupService {
	public ResponseEntity <GroupDTO> getGroup(long id);
}
