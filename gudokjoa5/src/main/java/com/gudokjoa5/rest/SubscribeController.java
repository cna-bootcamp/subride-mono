package com.gudokjoa5.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gudokjoa5.dto.SubscribeDTO;
import com.gudokjoa5.dto.TotalFeeDTO;
import com.gudokjoa5.service.SubscribeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;



@Tag(name="Subscribe API", description="Spring boot 구독좋아5 Subscribe API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins="*", allowedHeaders = "*")


/**
 * @작성자 : 곽승규
 * */

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
		@Parameter(name = "id", in = ParameterIn.QUERY, description = "user의 id", required=true)
	})
	@GetMapping(value = "/subscribe/mylist", produces = "application/json")
	public ResponseEntity <List<SubscribeDTO>> getSusbscribeList(@RequestParam(value = "id") long id) {
		return subscribeService.getSusbscribeList(id);
	}
	
	/**
	 * @설명 : 사용자가 구독한 서비스들의 총 결제 금액
	 * @param: id - 사용자아이디
	 * */
	@Operation(operationId="totalfee", summary="자신이 구독하고 있는 서비스 총 결제금액 가져오기", 
			description="총 결제 금액을 가져옵니다.")
	@Parameters({
		@Parameter(name = "id", in = ParameterIn.QUERY, description = "user의 id", required=true)
	})
	@GetMapping(value="/subscribe/totalfee")
	public ResponseEntity<TotalFeeDTO> getTotalFee(@RequestParam(value="id") long id){
		
		return subscribeService.getTotalFee(id);
	}
	
	/**
	 * @설명 : 구독서비스 하나에 대한 정보 보여주기
	 * @param : id - 구독서비스 아이디
	 * */
	@Operation(operationId="detail", summary="구독서비스 하나에 대한 정보 보여주기", 
			description="하나의 구독서비스 상세내용을 가져옵니다.")
	@Parameters({
		@Parameter(name = "id", in = ParameterIn.QUERY, description = "구독서비스의 id", required=true)
	})
	@GetMapping(value="/subscribe/detail")
	public ResponseEntity<SubscribeDTO> getSubscribeDetail(@RequestParam(value="id") long id) {
		return subscribeService.getSubscribeDetail(id);
	}
	
	/**
	 * @설명 : 사용자가 구독하고 있는 서비스들 중 SUB탈 수 있는 구독리스트 보여주기
	 * @param: id - 사용자아이디
	 * */
	@Operation(operationId="canSub", summary="사용자가 구독하고 있는 서비스들 중 SUB탈 수 있는 구독리스트 보여주기", 
			description="사용자가 SUB탈 수 있는 구독 리스트 보여주기")
	@Parameters({
		@Parameter(name = "id", in = ParameterIn.QUERY, description = "사용자의 id", required=true)
	})
	@GetMapping(value="/subscribe/cansub")
	public ResponseEntity<List<SubscribeDTO>> CanSubList(@RequestParam(value="id") long id) {
		return subscribeService.getCanSubList(id);
	}
	
	/**
	 * @설명 : 사용자가 신규등록 가능한 구독서비스 리스트 보여주기
	 * @param: id - 사용자아이디
	 * */
	
}
