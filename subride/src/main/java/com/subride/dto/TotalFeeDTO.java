package com.subride.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TotalFeeDTO {
	private String userId; // 사용자 id
	private int totalfee; // 총 결제하는 금액
	private int totalSavedAmount;	// 총 절감할 수 있는 금액
	private int feelevel;	//구독액에 따른 등급
}
