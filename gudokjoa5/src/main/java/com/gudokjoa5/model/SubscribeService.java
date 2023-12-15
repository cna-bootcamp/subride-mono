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
public class SubscribeService {

	private long id; // userID
	private String serviceName; // 서비스네임
	private long categoryId;  // 카테고리 id
	private long fee; // 구독료
	private String logo; // 로고
	private String description;// 설명
	private int isActive; // 활성화 여부
	private Timestamp createdAt; // 생성시간(날짜) 
	private Timestamp updatedAt; // 수정시간(날짜)
}
