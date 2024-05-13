package com.subride.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Subscribe {
	private String userId; // userID
	private String serviceName; // 서비스네임
	private long categoryId;  // 카테고리 id
	private long fee; // 구독료
	private String logo; // 로고
	private String description;// 설명
	private int maxUser;	//최대 멤버수
	private int isActive; // 활성화 여부
}
