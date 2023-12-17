package com.gudokjoa5.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor

/**
 * 유저 정보
 * */

public class User {

	private long id; // userID
	private String username; // 닉네임
	private String bankAccount; // 계좌번호
	private String profileImg; // 프로필 사진
	private int isActive; // 활성화 여부
}
