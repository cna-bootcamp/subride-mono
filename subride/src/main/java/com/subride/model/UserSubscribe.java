package com.subride.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserSubscribe {
	private long id;
	private String userId;
	private long subscribeserviceId;
	private int billingDate; //정기결제일
	private int isActive; // 활성화 여부
}
