package com.subride.dto;

import java.util.List;

import com.subride.entity.User;

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
	private String invitationCode;
	private String leaderUserId;
	private SubscribeDTO subscribeDTO;
	private List<User> users;
	private List<UserPayDTO> pays;
}
