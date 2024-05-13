package com.subride.service;

import com.subride.dao.UserRepository;
import com.subride.dao.AccountRepository;
import com.subride.entity.Account;
import com.subride.entity.User;
import com.subride.dto.SignupRequestDTO;
import com.subride.dto.JwtTokenDTO;
import com.subride.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void signup(SignupRequestDTO signupRequestDTO) {
        if (accountRepository.existsByUserId(signupRequestDTO.getUserId())) {
            throw new RuntimeException("User is already taken");
        }

        Account account = new Account();
        account.setUserId(signupRequestDTO.getUserId());
        account.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
        account.setRoles(signupRequestDTO.getRoles());

        accountRepository.save(account);

        User user = new User();
        user.setUserId(signupRequestDTO.getUserId());
        user.setUserName(signupRequestDTO.getUserName());
        user.setBankName(signupRequestDTO.getBankName());
        user.setBankAccount(signupRequestDTO.getBankAccount());
        user.setProfileImg(generateRandomProfileImg());
        user.setIsActive(1);
        userRepository.save(user);
    }

    public JwtTokenDTO login(String userId, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userId, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtTokenProvider.createToken(user, authentication.getAuthorities());
    }

    public int validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    public JwtTokenDTO refreshToken(String refreshToken) {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            String userId = jwtTokenProvider.getUserIdFromRefreshToken(refreshToken);
            User user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 사용자의 계정 정보 가져오기
            Account account = accountRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Account not found"));
            // 사용자의 권한 정보 생성
            Collection<? extends GrantedAuthority> authorities = account.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // 새로운 액세스 토큰 생성
            JwtTokenDTO newTokens = jwtTokenProvider.createToken(user, authorities);

            return newTokens;
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }

    private String generateRandomProfileImg() {
        int randomNumber = (int) (Math.random() * 4) + 1;
        return String.valueOf(randomNumber);
    }
}
