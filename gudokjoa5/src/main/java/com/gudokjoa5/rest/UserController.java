package com.gudokjoa5.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gudokjoa5.dto.LoginRequestDTO;
import com.gudokjoa5.dto.LoginResponseDTO;
import com.gudokjoa5.model.User;
import com.gudokjoa5.service.UserService;

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
@CrossOrigin(origins="*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private final UserService userService;
	
	@Operation(operationId="users", summary="사용자 정보 가져오기", description="사용자정보를 제공합니다.")
	@Parameters({
		@Parameter(name = "id", in = ParameterIn.QUERY, description = "user의 id", required=true)
	})
	@GetMapping("/users")
	public ResponseEntity <User> getUserById(@RequestParam(value="id") long id) {
		
		return userService.getUserById(id);
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
	public ResponseEntity<List<User>> getUserListByGroupId(@RequestParam(value="groupId") long id) {
		return userService.getUserListByGroupId(id);
	}
	
	
	@Operation(operationId="users-login", summary ="사용자 로그인하기", description="입력한 유저네임을 통해 로그인합니다.")
	@PostMapping("/users/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		return userService.login(loginRequestDTO);
	}
}
