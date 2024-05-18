package com.subride.service;

import com.subride.dao.GroupDao;
import com.subride.dao.SubscribeDao;
import com.subride.dao.UserDao;
import com.subride.dto.*;
import com.subride.model.Group;
import com.subride.entity.User;
import com.subride.model.UserGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SubscribeDao subscribeDao;

	private final RandomValueGenerator randomValueGenerator;
	@Autowired
	public GroupServiceImpl(RandomValueGenerator randomValueGenerator) {
		this.randomValueGenerator = randomValueGenerator;
	}

	@Override
	@Transactional
	public ResponseEntity<GroupDTO> getGroup(long id, List<String> includes) {
		GroupDTO groupDTO = groupDTO(id, includes);
		return new ResponseEntity<GroupDTO> (groupDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<GroupDTO>> getGroupList(String userId, List<String> includes) {
		List<GroupDTO> groupDTOList = new LinkedList<>();
		try {
			List<Group> groupList = groupDao.getGroupList(userId);
			for(Group group: groupList) {
				groupDTOList.add(groupDTO(group.getId(), includes));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<GroupDTO>> (groupDTOList, HttpStatus.OK);
	}

	private GroupDTO groupDTO(long id, List<String> includes) {
		GroupDTO groupDTO = null;
		try {
			Group group = null;
			List<User> users = null;
			SubscribeDTO subscribeDTO = null;
			List<UserPayDTO> payHistory = null;

			group = groupDao.getGroup(id);
			if(group != null) {
				if (includes != null && !includes.isEmpty()) {
					for (String include : includes) {
						switch (include) {
							case "subscribe":
								subscribeDTO = subscribeDao.getSubscribeDetail(group.getSubscribeId());
								break;
							case "users":
								users = userDao.getUserByGroupId(id);
								break;
							case "pays":
								payHistory = groupDao.getPayHistory(id);
								break;
							default:
								throw new IllegalArgumentException("Unsupported include parameter: " + include);
						}
					}
				}
			}

			groupDTO = new GroupDTO(
					group != null ? group.getId() : 0,
					group != null ? group.getGroupName() : null,
					group != null ? group.getBillingDate() : 0,
					group != null ? group.getInvitationCode() : null,
					group != null ? group.getLeaderUserId() : null,
					subscribeDTO,
					users,
					payHistory);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupDTO;
	}

	@Override
	@Transactional
	public ResponseEntity<Object> insertGroup(GroupCreateDTO groupCreateDTO) {
		//String invitationCode = randomString();
		String invitationCode = randomValueGenerator.generateUniqueRandomValue();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			Group group = null;
			UserGroup userGroup = null;
			SubscribeDTO subscribeDTO = subscribeDao.getSubscribeByName(groupCreateDTO.getSubscribeName()); 			
			group = new Group(
					0,
					subscribeDTO.getServiceId(),
					groupCreateDTO.getGroupAccount(),
					groupCreateDTO.getLeaderUserId(),
					1,
					groupCreateDTO.getGroupName(),
					groupCreateDTO.getBillingDate(),
					invitationCode
			);
			
			
			// 새로운 그룹을 만들기 전에 해당유저가 같은 이름을 만든 방이 있는지 확인.
			List<Group> groupList = groupDao.getGroupList(groupCreateDTO.getLeaderUserId());
			
			for(Group group1: groupList) {
				if (groupCreateDTO.getGroupName().equals(group1.getGroupName())) {
					map.put("massage", "이미 존재하는 방이름 입니다.");
					return new ResponseEntity<Object> (map, HttpStatus.BAD_REQUEST);	
				}
			}
			
			groupDao.insertGroup(group);
			
			Group createdGroup = groupDao.getGroupByGroupName(groupCreateDTO.getGroupName(), groupCreateDTO.getLeaderUserId());
			userGroup = new UserGroup(
					0,
					groupCreateDTO.getLeaderUserId(),
					createdGroup.getId(), // 방의 아이디를 받는 거임.
					1
			);
			
			groupDao.insertUserGroup(userGroup);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("invitationCode", invitationCode);
		
		return new ResponseEntity<Object> (map, HttpStatus.OK);	
	}

	@Override
	@Transactional
	public ResponseEntity<Object> joinGroup(GroupJoinDTO groupJoinDTO) {
		String msg = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		UserGroup userGroup = null;
		List<GroupDTO> groupDTOList = null; // 현재 내가 속해있는 그룹 리스트 받을 변수
		
		try {
			Group createdGroup = groupDao.getGroupByInvitationCode(groupJoinDTO.getInvitationCode());
			if(createdGroup == null){
				msg = "해당코드를 가진 방이 없습니다.";
				map.put("message", msg);
				return new ResponseEntity<Object> (map, HttpStatus.NOT_FOUND);
			}

			// 구독서비스의 최대 인원 수를 초과 했는지 검사
			boolean isExceedMaxUser = groupDao.isExceedMaxUser(createdGroup.getId());
			if(!isExceedMaxUser) {
				msg = "모든 멤버가 이미 구성되어 참여할 수 없습니다.";
				map.put("message", msg);
				return new ResponseEntity<Object> (map, HttpStatus.BAD_REQUEST);
			}

			// 방에 조인하기 전에, 내가 현재 그방에 들어가 있는 지 확인.
			List<Group> groupList = groupDao.getGroupList(groupJoinDTO.getUserId()); // 유저의 아이디를 통해 해당 유저가 현재 어떤 방에 있는지 확인.
			System.out.println(groupList);
			for(Group group: groupList) {
				System.out.println("for문 돌기 시작");
				System.out.println(group.getInvitationCode());
				if (groupJoinDTO.getInvitationCode().equals(group.getInvitationCode()) ) { // 들어가려는 방이 현재 리스트에 있다면..
					msg = "이미 방에 초대되어 있습니다.";
					map.put("message", msg); 
					return new ResponseEntity<Object> (map, HttpStatus.NOT_FOUND);
				}
			}
			
			userGroup = new UserGroup(
					0,
					groupJoinDTO.getUserId(), //유저 아이디
					createdGroup.getId(), //다인팟 아이디
					1
			);
			
			groupDao.insertUserGroup(userGroup);

			//-- 유저의 구독 리스트에 없으면 자동 추가
			if(!subscribeDao.isSubscribed(groupJoinDTO.getUserId(), createdGroup.getSubscribeId())) {
				SubscribeEnrollDTO subscribeEnrollDTO = new SubscribeEnrollDTO(groupJoinDTO.getUserId(), createdGroup.getSubscribeId(), createdGroup.getBillingDate());
				subscribeDao.setSubscribeInsert(subscribeEnrollDTO);
			}

			msg = "그룹 들어가기 성공 ";			
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("message", msg);
		return new ResponseEntity<Object> (map, HttpStatus.OK);
	}

}
