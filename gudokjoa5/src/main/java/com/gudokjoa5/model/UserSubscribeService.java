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
public class UserSubscribeService {

	private long id;
	private long userId;
	private long subscribeserviceId;
	private int billingDate; //정기결제일
	private int isActive; // 활성화 여부
}
