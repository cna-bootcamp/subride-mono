package com.subride.rest;

import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.subride.entity.User;
import com.subride.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="Springboot sample API", description="Spring boot 구독좋아5 User API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private final UserService userService;
	
	@Operation(operationId="users", summary="사용자 정보 가져오기", description="사용자정보를 제공합니다.")
	@Parameters({
		@Parameter(name = "id", in = ParameterIn.QUERY, description = "user의 id", required=true)
	})
	@GetMapping("/users")
	public ResponseEntity <User> getUserById(@RequestParam(value="id") String userId) {
		
		return userService.getUserById(userId);
	}

	@GetMapping(value="/userlist", produces = "application/json")
	@Operation(operationId="userlist", summary="사용자 전체정보 가지고 오기", description="사용자 전체정보를 제공합니다.")
	public ResponseEntity <List<User>> getUserList() {
		return userService.getUserList(); // 주석
	}

	@Operation(operationId="users-group", summary ="그룹에 속한 사용자 목록 가져오기", description="  그룹에 속한 사용자 목록을 제공합니다.")
	@Parameters({
		@Parameter(name = "groupId", in = ParameterIn.QUERY, description = "group id", required=true)
	})
	@GetMapping("/users/group")
	public ResponseEntity<List<User>> getUserListByGroupId(@RequestParam(value="id") long id) {
		return userService.getUserListByGroupId(id);
	}

}
