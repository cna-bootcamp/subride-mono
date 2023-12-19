package com.gudokjoa5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SubscribeListDTO {

	private long serviceId; // 구독서비스 id
	private String serviceName; // 서비스명
	private String categoryName; // 카테고리 이름 ex) OTT, 반려동물
	private long fee; // 구독료
	private String description;// 설명
	private String logo; // 로고
}
