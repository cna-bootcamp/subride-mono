package com.gudokjoa5.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gudokjoa5.dao.GroupDao;
import com.gudokjoa5.dao.SubscribeDao;
import com.gudokjoa5.dao.UserDao;
import com.gudokjoa5.dto.GroupCreateDTO;
import com.gudokjoa5.dto.GroupDTO;
import com.gudokjoa5.dto.GroupJoinDTO;
import com.gudokjoa5.dto.SubscribeDTO;
import com.gudokjoa5.model.Group;
import com.gudokjoa5.model.User;
import com.gudokjoa5.model.UserGroup;

@Service
public class GroupServiceImpl implements GroupService {
	
private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SubscribeDao subscribeDao;
	
	@Override
	@Transactional
	public ResponseEntity<GroupDTO> getGroup(long id) {
		GroupDTO groupDTO = groupDTO(id);		
		return new ResponseEntity<GroupDTO> (groupDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<GroupDTO>> getGroupList(long id) {
		List<GroupDTO> groupDTOList = new LinkedList<>();
		try {
			List<Group> groupList = groupDao.getGroupList(id);
			for(Group group: groupList) {
				groupDTOList.add(groupDTO(group.getId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<GroupDTO>> (groupDTOList, HttpStatus.OK);
	}
	
	@Transactional
	private GroupDTO groupDTO(long id) {
		GroupDTO groupDTO = null;
		try {
			Group group = null;
			List<User> users = null;
			SubscribeDTO subscribeDTO = null;
			User leaderUser = null;
			group = groupDao.getGroup(id);
			subscribeDTO = subscribeDao.getSubscribeDetail(group.getSubscribeId());
			users = userDao.getUserByGroupId(group.getId());
			leaderUser = userDao.selectUser(group.getLeaderUser());

			groupDTO = new GroupDTO(
						group.getId(),
						group.getGroupName(),
						group.getBillingDate(),
						leaderUser.getUsername(),
						subscribeDTO,
						users
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupDTO;
	}

	@Override
	@Transactional
	public ResponseEntity<Object> insertGroup(GroupCreateDTO groupCreateDTO) {
		String invitationCode = randomString();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			Group group = null;
			UserGroup userGroup = null;
			SubscribeDTO subscribeDTO = subscribeDao.getSubscribeByName(groupCreateDTO.getSubscribeName()); 			
			group = new Group(
					0,
					subscribeDTO.getServiceId(),
					groupCreateDTO.getGroupAccount(),
					groupCreateDTO.getLeaderUser(),
					1,
					groupCreateDTO.getGroupName(),
					groupCreateDTO.getBillingDate(),
					invitationCode
			);
			
			
			// 새로운 그룹을 만들기 전에 해당유저가 같은 이름을 만든 방이 있는지 확인.
			List<Group> groupList = groupDao.getGroupList(groupCreateDTO.getLeaderUser());
			
			for(Group group1: groupList) {
				if (groupCreateDTO.getGroupName().equals(group1.getGroupName())) {
					map.put("massage", "이미 존재하는 방이름 입니다.");
					return new ResponseEntity<Object> (map, HttpStatus.BAD_REQUEST);	
				}
			}
			
			groupDao.insertGroup(group);
			
			Group createdGroup = groupDao.getGroupByGroupName(groupCreateDTO.getGroupName(), groupCreateDTO.getLeaderUser()); // 여기가 문제임.
			userGroup = new UserGroup(
					0,
					groupCreateDTO.getLeaderUser(),
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
	
	private String randomString() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 4;
		Random random = new Random();

		String generatedString = random.ints(leftLimit,rightLimit + 1)
		  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		  .limit(targetStringLength)
		  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		  .toString();

		return generatedString;
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
			
			// 방에 조인하기 전에, 내가 현재 그방에 들어가 있는 지 확인.
			List<Group> groupList = groupDao.getGroupList(groupJoinDTO.getId()); // 유저의 아이디를 통해 해당 유저가 현재 어떤 방에 있는지 확인.
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
					groupJoinDTO.getId(), //유저 아이디
					createdGroup.getId(), //다인팟 아이디
					1
			);
			
			groupDao.insertUserGroup(userGroup);
			msg = "그룹 들어가기 성공 ";			
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("message", msg);
		return new ResponseEntity<Object> (map, HttpStatus.OK);
	}

}
