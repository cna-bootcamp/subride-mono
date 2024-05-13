package com.subride.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserPayDTO {
	private long id;
	private String userId;
	private String username;
	private long groupId;
	private LocalDateTime payDateTime; //지불일시
}
