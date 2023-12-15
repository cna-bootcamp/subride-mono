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

public class Group {
	private long id; // userID
	private long subscribeserviceId; // 구독서비스 ID
	private String groupAccount; // 대표계좌
	private long leaderUser;  // 그룹장
	private int isActive; // 활성화 여부
	private Timestamp createdAt; // 생성시간(날짜) 
	private Timestamp updatedAt; // 수정시간(날짜)
}
