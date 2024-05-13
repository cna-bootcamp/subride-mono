package com.subride.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.subride.dto.JwtTokenDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private final Algorithm algorithm;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration-time}") long accessTokenValidityInMilliseconds,
            @Value("${jwt.refresh-token-expiration-time}") long refreshTokenValidityInMilliseconds) {
        this.algorithm = Algorithm.HMAC512(secretKey);
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds * 1000;
    }

    public JwtTokenDTO createToken(com.subride.entity.User user, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        Date accessTokenValidity = new Date(now.getTime() + accessTokenValidityInMilliseconds);
        Date refreshTokenValidity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);

        String accessToken = JWT.create()
                .withSubject(user.getUserId())
                .withClaim("userId", user.getUserId())
                .withClaim("userName", user.getUserName())
                .withClaim("bankName", user.getBankName())
                .withClaim("bankAccount", user.getBankAccount())
                .withClaim("profileImg", user.getProfileImg())
                .withClaim("auth", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withIssuedAt(now)
                .withExpiresAt(accessTokenValidity)
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUserId())
                .withIssuedAt(now)
                .withExpiresAt(refreshTokenValidity)
                .sign(algorithm);

        return JwtTokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(refreshToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserIdFromRefreshToken(String refreshToken) {
        try {
            DecodedJWT decodedJWT = JWT.decode(refreshToken);
            return decodedJWT.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid refresh token");
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public int validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return 1; // 검사 성공 시 1 반환
        } catch (TokenExpiredException e) {
            return 10; // 토큰 만료 시 10 반환
        } catch (SignatureVerificationException e) {
            return 20; // 서명 검증 실패 시 20 반환
        } catch (AlgorithmMismatchException e) {
            return 30; // 알고리즘 불일치 시 30 반환
        } catch (InvalidClaimException e) {
            return 40; // 유효하지 않은 클레임일 경우 40 반환
        } catch (Exception e) {
            return 50; // 그 외 예외 발생 시 50 반환
        }
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        String username = decodedJWT.getSubject();
        String[] authStrings = decodedJWT.getClaim("auth").asArray(String.class);
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(authStrings)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails userDetails = new User(username, "", authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
}