package com.subride.rest;

import java.time.format.DateTimeFormatter;
import java.util.*;

import com.subride.dao.GroupDao;
import com.subride.dto.UserPayDTO;
import com.subride.entity.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.subride.dto.GroupCreateDTO;
import com.subride.dto.GroupDTO;
import com.subride.dto.GroupJoinDTO;
import com.subride.service.GroupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Tag(name="썹그룹 API", description="썹 그룹 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class GroupController {

	@Autowired
	private final GroupService groupService;
	
	@Operation(operationId="groupsdetail", summary="그룹 정보 가져오기", description="하나의 그룹 정보 상세 내용을 제공합니다.")
	@Parameters({
			@Parameter(name = "id", in = ParameterIn.PATH, description = "썹그룹 ID", required=true),
			@Parameter(name = "include", in = ParameterIn.QUERY, description = "응답 포함 리소스", required=false)
	})
	@GetMapping(value = "/{id}", params = "include")
	public ResponseEntity<GroupDTO> getGroupById(@PathVariable long id,
												 @RequestParam(value = "include", required = false) String includeParam) {

		List<String> includes = Arrays.asList(includeParam.replaceAll("\\s", "").split(","));
		return groupService.getGroup(id, includes);
	}

	@Operation(operationId="groupslist", summary="그룹 목록 가져오기", description=" 사용자가 가입한 그룹 목록을 제공합니다.")
	@Parameters({
			@Parameter(name = "userId", in = ParameterIn.QUERY, description = "사용자ID", required=true),
			@Parameter(name = "include", in = ParameterIn.QUERY, description = "응답 포함 리소스", required=false)
	})
	@GetMapping
	public ResponseEntity <List<GroupDTO>> getGroupListById(@RequestParam(value = "userId") String userId,
															@RequestParam(value = "include", required = false) String includeParam) {
		List<String> includes = Arrays.asList(includeParam.replaceAll("\\s", "").split(","));
		return groupService.getGroupList(userId, includes);
	}

	@Operation(operationId="groupCreate", summary="그룹 생성하기", description="그룹을 생성합니다 ")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content(
					schema = @Schema(implementation = GroupCreateDTO.class),
					examples = {
							@ExampleObject(
									name = "그룹 생성 요청 예시",
									value = "{\"groupName\":\"그룹명\",\"groupAccount\":\"그룹계정\",\"leaderUserId\":\"리더유저ID\",\"subscribeName\":\"구독서비스명\",\"billingDate\":\"구독료 납부일\"}"
							)
					}
			)
	)
	@PostMapping
	public ResponseEntity<Object> createGroup(@RequestBody GroupCreateDTO groupCreateDTO) {	
		return groupService.insertGroup(groupCreateDTO);
	} 
	
	@Operation(operationId="groupJoin", summary="그룹 참여하기", description="그룹에 참여합니다 ")
	@PostMapping("/members")
	public ResponseEntity <Object> joinGroup(@RequestBody GroupJoinDTO groupJoinDTO) {
		return groupService.joinGroup(groupJoinDTO);
	}

	/*
	썹그룸 참여 취소
	 */
	@Operation(operationId="cancel-join", summary="썹그룹 참여취소",
			description="썹그룹 참여를 취소 합니다. ")
	@Parameters({
			@Parameter(name = "userId", in = ParameterIn.QUERY, description = "사용자의 id", required=true),
			@Parameter(name = "groupId", in = ParameterIn.PATH, description = "썹그룹 id", required=true)
	})
	@DeleteMapping("/{groupId}")
	public ResponseEntity<String> cancelJoin(@RequestParam String userId, @PathVariable long groupId) {
		return groupService.cancelJoin(userId, groupId);
	}

	@Autowired
	private GroupDao groupDao;
	/**
	 * @설명 : 테스트로 12개월치 납부 내역 데이터 생성
	 * @param: user_id - 사용자 아이디, group_id - 썹 그룹 id
	 * */
	@Operation(operationId="createPayHistory", summary="테스트 페이 내역 생성",
			description="썹 그룹에 테스트 납부 내역 생성")
	@Parameters({
			@Parameter(name = "userId", in = ParameterIn.QUERY, description = "사용자의 id", required=true),
			@Parameter(name = "groupId", in = ParameterIn.QUERY, description = "썹 그룹 id", required=true)
	})
	@PostMapping("/test-data/pay-history")
	public ResponseEntity<String> createPayHistory(@RequestParam(value="userId") String userId, @RequestParam(value="groupId", required=true) long groupId) {
		ArrayList<UserPayDTO> list = new ArrayList<UserPayDTO>();
		UserPayDTO userPayDTO = null;
		String payDatetime;

		LocalDateTime startDateTime = LocalDateTime.now().minusMonths(12).withDayOfMonth(7).withHour(0).withMinute(0).withSecond(0);

		for (int i = 0; i < 12; i++) {
			userPayDTO = new UserPayDTO();

			userPayDTO.setUserId(userId);
			userPayDTO.setGroupId(groupId);
			userPayDTO.setPayDateTime(startDateTime.plusMonths(i));

			list.add(userPayDTO);
		}
		/*
		for (UserPayDTO pay : list) {
			System.out.println(pay.getUserId()+":"+pay.getGroupId()+"->"+pay.getPayDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		}
		*/

		try {
			groupDao.createPayHistory(list);
			return ResponseEntity.status(HttpStatus.OK)
					.body("테스트 데이터 생성 성공");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("테스트 데이터 생성 실패: " + e.getMessage());
		}
	}
}