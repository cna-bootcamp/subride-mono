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
	
	
//	public Object getSusbscribeList(@RequestParam(value = "id") long id) {
//		
//		ResponseEntity<List<SubscribeListDTO>> list = null;
//		
//		list = subscribeService.getSusbscribeList(id);
//		
//		//response할 객체 생성
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		
//		map.put("subscribeList", list);
//		map.put("message", "사용자가 구독하고 있는 리스트 가져오기 성공");
//		return map;
//	}
	
}
