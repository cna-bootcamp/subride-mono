package com.gudokjoa5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TotalFeeDTO {

	private long userId; // 사용자 id
	private int totalfee; // 총 결제하는 금액
}
