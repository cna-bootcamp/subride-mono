package com.gudokjoa5.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gudokjoa5.dto.GroupDTO;
import com.gudokjoa5.service.GroupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="GROUP API", description="GROUP API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class GroupController {

	@Autowired
	private final GroupService groupService;
	
	@Operation(operationId="groups", summary="그룹 정보 가져오기", description="하나의 그룹 정보 상세 내용을 제공합니다.")
	@GetMapping("/groups")
	public ResponseEntity <GroupDTO> getGroupById(long id) {	
		return groupService.getGroup(id);
	}
}