package com.subride.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	private String userId;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "bank_name", nullable = false)
	private String bankName;

	@Column(name = "bank_account", nullable = false)
	private String bankAccount;

	@Column(name = "profile_img")
	private String profileImg;

	@Column(name = "is_active")
	private int isActive;
}
