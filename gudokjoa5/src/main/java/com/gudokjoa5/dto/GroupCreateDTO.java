package com.gudokjoa5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class GroupCreateDTO {
	private String groupAccount;
	private long leaderUser;
	private String groupName;
	private String subscribeName;
	private int billingDate;
}