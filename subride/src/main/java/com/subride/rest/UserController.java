package com.subride.rest;

import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.subride.entity.User;
import com.subride.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="회원 API", description="회원 API입니다. ")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private final UserService userService;
	
	@Operation(operationId="users", summary="사용자 정보 가져오기", description="사용자정보를 제공합니다.")
	@Parameters({
		@Parameter(name = "userId", in = ParameterIn.PATH, description = "user의 id", required=true)
	})
	@GetMapping("/{userId}")
	public ResponseEntity <User> getUserById(@PathVariable String userId) {
		return userService.getUserById(userId);
	}

	@Operation(operationId="userlist", summary="사용자 전체 정보 가지고 오기", description="사용자 전체 정보를 제공합니다.")
	@GetMapping
	public ResponseEntity <List<User>> getUserList() {
		return userService.getUserList(); // 주석
	}

}
