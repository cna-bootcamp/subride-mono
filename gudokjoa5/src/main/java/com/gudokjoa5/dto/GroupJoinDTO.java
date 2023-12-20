package com.gudokjoa5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class GroupJoinDTO {
	private long id;
	private String invitationCode;
}