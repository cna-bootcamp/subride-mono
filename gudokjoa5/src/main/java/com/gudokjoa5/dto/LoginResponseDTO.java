package com.gudokjoa5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginResponseDTO {

	private long id; // 유저 아이디
	private String userName;
	private String bandkAccount;
	private int profileImg;
}
