package com.gudokjoa5.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ExpenseHistory {
	
	private long id;
	private long categoryId;
	private String expenseDetail;//지출상세
	private long price; // 가격 
	private Date paidAt; //결제시각
}
