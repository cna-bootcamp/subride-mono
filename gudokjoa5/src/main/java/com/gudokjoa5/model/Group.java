package com.gudokjoa5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@AllArgsConstructor

public class Group {
	private long id; // userID
	private long subscribeserviceId; // 구독서비스 ID
	private String groupAccount; // 대표계좌
	private long leaderUser;  // 그룹장
	private int isActive; // 활성화 여부
	private String groupName;
	private int billigDate;
	private String invitationCode;
}
