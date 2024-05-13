package com.subride.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserGroup {
	private long id;
	private String userId;
	private long groupId;
	private int isActive; // 활성화 여부
}
