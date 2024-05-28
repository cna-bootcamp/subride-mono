package com.subride.rest;

import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.subride.dto.SubscribeDTO;
import com.subride.dto.SubscribeEnrollDTO;
import com.subride.dto.TotalFeeDTO;
import com.subride.service.SubscribeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;



@Tag(name="구독 추천 API", description="구독 추천 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class SubscribeController {
	@Autowired
	private final SubscribeService subscribeService;
	
	/**
	 * @설명 : 사용자가 구독한 서비스 목록 보여주기
	 * @param: id - 사용자아이디
	 * */
	@Operation(operationId="subscribelist", summary="구독한 서비스리스트 가져오기", 
			description="구독한 서비스리스트를 가져옵니다.")
	@Parameters({
		@Parameter(name = "userId", in = ParameterIn.QUERY, description = "user의 id", required=true)
	})
	@GetMapping
	public ResponseEntity <List<SubscribeDTO>> getSusbscribeList(@RequestParam(value = "userId") String userId) {
		return subscribeService.getSusbscribeList(userId);
	}
	
	/**
	 * @설명 : 사용자가 구독한 서비스들의 총 결제 금액
	 * @param: id - 사용자아이디
	 * */
	@Operation(operationId="totalfee", summary="자신이 구독하고 있는 서비스 총 결제금액 가져오기", 
			description="총 결제 금액을 가져옵니다.")
	@Parameters({
		@Parameter(name = "userId", in = ParameterIn.QUERY, description = "user의 id", required=true)
	})
	@GetMapping("/totalfee")
	public ResponseEntity<TotalFeeDTO> getTotalFee(@RequestParam(value="userId") String userId){
		return subscribeService.getTotalFee(userId);
	}
	
	/**
	 * @설명 : 구독서비스 하나에 대한 정보 보여주기
	 * @param : id - 구독서비스 아이디
	 * */
	@Operation(operationId="detail", summary="구독서비스 하나에 대한 정보 보여주기", 
			description="하나의 구독서비스 상세내용을 가져옵니다.")
	@Parameters({
		@Parameter(name = "id", in = ParameterIn.PATH, description = "구독서비스의 id", required=true)
	})
	@GetMapping("/{id}")
	public ResponseEntity<SubscribeDTO> getSubscribeDetail(@PathVariable long id) {
		return subscribeService.getSubscribeDetail(id);
	}
	
	/**
	 * @설명 : 사용자가 구독하고 있는 서비스들 중 SUB탈 수 있는 구독리스트 보여주기
	 * @param: id - 사용자아이디
	 * */
	@Operation(operationId="canSub", summary="사용자가 구독하고 있는 서비스들 중 SUB탈 수 있는 구독리스트 보여주기", 
			description="사용자가 SUB탈 수 있는 구독 리스트 보여주기")
	@Parameters({
		@Parameter(name = "userId", in = ParameterIn.QUERY, description = "사용자의 id", required=true)
	})
	@GetMapping("/sub-candidates")
	public ResponseEntity<List<SubscribeDTO>> CanSubList(@RequestParam(value="userId") String userId) {
		return subscribeService.getCanSubList(userId);
	}

	/**
	 * @설명 : 카테고리에 해당하는 구독서비스 목록 리턴
	 * @param: userId - 사용자아이디: 사용자가 이미 구독한 구독서비스는 제외함
	 * */
	@Operation(operationId="canEnroll", summary="사용자가 신규등록 가능한 구독서비스 리스트 보여주기",
			description="사용자가 새로운 구독 서비스를 구독하였다면 이를 등록하기 위해, 현재 구독하고 있지 않은 서비스들 보여주기")
	@Parameters({
			@Parameter(name = "categoryId", in = ParameterIn.QUERY, description = "구독카테고리 id", required=true),
			@Parameter(name = "userId", in = ParameterIn.QUERY, description = "사용자의 id", required=true)
	})
	@GetMapping("/enroll-subscriptions")
	public ResponseEntity<List<SubscribeDTO>> getEnrollServicesByCategory(@RequestParam(value="categoryId") Long categoryId,
														@RequestParam(value="userId") String userId) {
		return subscribeService.getEnrollServicesByCategory(categoryId, userId);
	}
	
	/**
	 * @설명 : 사용자가 새로 가입한 구독서비스 추가하기
	 * @param: id - 사용자아이디
	 * */
	@Operation(operationId="enroll", summary="사용자가 새로 가입한 구독서비스 추가하기", 
			description="사용자가 새로 가입한 구독서비스를 내가 구독한 서비스 목록에 추가하기")
	
	@PostMapping("/members")
	public ResponseEntity<Object> setSubscribeInsert(
			@RequestBody  SubscribeEnrollDTO subscribeEnrollDTO
		) throws Exception { 
		
		return subscribeService.setSubscribeInsert(subscribeEnrollDTO);
	}

	/*
	구독서비스 가입 취소
	 */
	@Operation(operationId="unsubscribe", summary="구독취소",
			description="구독 취소를 합니다. ")
	@Parameters({
			@Parameter(name = "userId", in = ParameterIn.QUERY, description = "사용자의 id", required=true),
			@Parameter(name = "subscribeId", in = ParameterIn.PATH, description = "서비스 id", required=true)
	})
	@DeleteMapping("/{subscribeId}")
	public ResponseEntity<String> unsubscribeSub(@RequestParam String userId, @PathVariable long subscribeId) {
		return subscribeService.unsubscribeSub(userId, subscribeId);
	}
}
