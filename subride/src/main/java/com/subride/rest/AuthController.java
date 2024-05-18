package com.subride.rest;

import com.subride.dto.*;
import com.subride.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Auth API", description="인증/인가 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(operationId="auth-signup", summary ="회원가입", description="회원가입을 처리합니다.")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDTO signupRequestDTO) {
        try {
            authService.signup(signupRequestDTO);
            return ResponseEntity.ok("Account registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(operationId="auth-login", summary ="로그인", description="로그인 처리")
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        JwtTokenDTO tokenDTO = authService.login(loginRequestDTO.getUserId(), loginRequestDTO.getPassword());
        return ResponseEntity.ok(tokenDTO);
    }

    @Operation(operationId="validate-token", summary ="인증 토큰 검증", description="인증 토큰을 검증합니다.")
    @PostMapping("/verify")
    public ResponseEntity<Integer> validate(@RequestBody JwtTokenVarifyDTO jwtTokenVarifyDTO) {
        int result = authService.validateToken(jwtTokenVarifyDTO.getToken());
        return ResponseEntity.ok(result);
    }

    @Operation(operationId="refresh-token", summary ="인증 토큰 갱신", description="인증 토큰을 갱신합니다.")
    @PostMapping("/refresh")
    public ResponseEntity<JwtTokenDTO> refresh(@RequestBody JwtTokenRefreshDTO jwtTokenRefreshDTO) {
        JwtTokenDTO tokenDTO = authService.refreshToken(jwtTokenRefreshDTO.getRefreshToken());
        return ResponseEntity.ok(tokenDTO);
    }
}
