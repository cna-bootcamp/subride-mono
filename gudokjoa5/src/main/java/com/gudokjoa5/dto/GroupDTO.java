package com.gudokjoa5.dto;

import java.util.List;

import com.gudokjoa5.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class GroupDTO {
	private long id;
	private String groupName;
	private int billingDate;
	private String leaderUsername;
	private SubscribeDTO subscribeDTO;
	private List<User> users; 
}
