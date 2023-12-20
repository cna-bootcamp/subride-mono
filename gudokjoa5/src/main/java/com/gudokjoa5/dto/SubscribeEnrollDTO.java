package com.gudokjoa5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SubscribeEnrollDTO {

	private long userId; // 사용자 아이디 
	private long subscribeId; // 구독서비스 아이디
	private int billingDate; // 결제일
}
