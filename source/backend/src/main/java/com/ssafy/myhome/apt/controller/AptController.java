package com.ssafy.myhome.apt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.filters.ExpiresFilter.XServletOutputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.myhome.apt.model.AptDto;
import com.ssafy.myhome.apt.model.FavDto;
import com.ssafy.myhome.apt.model.InfoDto;
import com.ssafy.myhome.apt.model.service.AptService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/apt")
@Api("APT 컨트롤러 API V1")
public class AptController {
	private AptService aptService;

	public AptController(AptService aptService) {
		super();
		this.aptService = aptService;
	}
	
	
	@ApiOperation(value = "아파트 정보", notes = "특정 지역의 아파트 정보를 반환한다.", response = List.class)
	@GetMapping("/keySearch")
	public ResponseEntity<List<AptDto>> getApt(@RequestParam @ApiParam(value="검색할 지역 정보,검색어") Map<String, String> map ) throws Exception {
		System.out.println(map.toString());
		return new ResponseEntity<List<AptDto>>(aptService.getApt(map), HttpStatus.OK);
	}
	
	@ApiOperation(value = "검색어 저장", notes = "사용자 키워드 검색 정보를 저장한다")
	@PostMapping("/saveKeyword")
	public void saveKeyword(@RequestParam @ApiParam(value = "검색 키워드", required = true) String keyword,
			@RequestParam @ApiParam(value = "사용자 아이디", required = true) String userId) throws Exception
	{
		System.out.println(keyword+" "+userId);
		aptService.saveKeyword(keyword,userId);
	}
	
	@ApiOperation(value = "아파트 거래 정보", notes = "특정 아파트 거래 정보를 최신순으로 반환한다.", response = List.class)
	@GetMapping("/dealSearch/{aptCode}")
	public ResponseEntity<Map<String, Object>> getApt(@PathVariable("aptCode") @ApiParam(value="검색할 아파트 코드") String aptCode ) throws Exception {
		System.out.println(aptCode);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("aptDealList", aptService.dealSearch(aptCode));
		resultMap.put("validYear", aptService.getValidYear(aptCode));
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	/*
	 * @ApiOperation(value = "아파트 좋아요 입력", notes = "특정 아파트를 좋아요 처리한다")
	 * 
	 * @PostMapping("/likeApt") public void likeApt(String userId,String aptCode)
	 * throws Exception //aptCode db상에서는 bigint이나 java int범위를 넘어가기 때문에 String 처리합니다.
	 * { System.out.println(userId+" "+aptCode); aptService.likeApt(userId,
	 * aptCode); }
	 */
	
	@ApiOperation(value = "아파트 거래의 년도만 반환", notes = "아파트 매매 내역이 있는 년도만 조회.", response = List.class)
	@GetMapping("/dealSearch/validyear/{aptCode}")
	public ResponseEntity<List<Integer>> getValidYear(@PathVariable("aptCode") @ApiParam(value="검색할 아파트 코드") String aptCode ) throws Exception {
		System.out.println(aptCode);
		return new ResponseEntity<List<Integer>>(aptService.getValidYear(aptCode), HttpStatus.OK);
	}
	
	// 아파트 정보 좋아요
	@ApiOperation(value = "좋아요 저장", notes = "사용자가 선택한 아파트 좋아요를 저장한다.")
	@PostMapping("/fav")
	public void registFav(@RequestBody FavDto favDto) throws Exception
	{
		System.out.println(favDto);
		//db에 아이디 - 아파트코드 중복 여부 확인.
		if(aptService.likeSearch(favDto.getUserId(), favDto.getAptCode())!=null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", favDto.getUserId());
			map.put("aptCode", favDto.getAptCode());
			aptService.updatelikeApt(map);
		}else {
			aptService.likeApt(favDto.getUserId(), favDto.getAptCode());
		}
		
		
		System.out.println("좋아요 저장"+favDto.getUserId()+" "+favDto.getAptCode());
//		System.out.println(map);

		
	}

	
	@ApiOperation(value = "좋아요 삭제", notes = "사용자가 선택한 아파트 좋아요를 삭제한다.")
	@PostMapping("/fav/{userId}/{aptCode}")
	public void deleteFav(@PathVariable("userId") @ApiParam(value="검색할 아파트 코드") String userId,
			@PathVariable("aptCode") @ApiParam(value="검색할 아파트 코드") String aptCode) throws Exception
	{
		System.out.println("삭제하게 해주세요..."+userId+" "+aptCode);
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("aptCode", aptCode);
		aptService.deletelikeApt(map);
	}
	
	@ApiOperation(value = "아파트 이름 검색", notes = "아파트 이름 검색 결과를 반환한다.", response = List.class)
	@GetMapping("/searchByName/{aptName}")
	public ResponseEntity<List<InfoDto>> searchByName(@PathVariable("aptName") @ApiParam(value="아파트 이름") String aptName) throws Exception {
		System.out.println("아파트 이름 >>>>>> " + aptName);
		return new ResponseEntity<List<InfoDto>>(aptService.searchByName(aptName), HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트 코드 기반 정보 검색", notes = "아파트 코드 검색 결과를 반환한다.", response = List.class)
	@GetMapping("/searchByCode")
	public ResponseEntity<List<String>> searchByCode(@RequestParam(value="aptCodes", required=false, defaultValue="") @ApiParam(value="아파트 코드 리스트") List<String> aptCodes) throws Exception {
		System.out.print("아파트 코드 리스트 >>>>>> " + aptCodes);
		for (String aptCode : aptCodes) {
			System.out.print(aptCode + ", ");
		}
		System.out.println();
		return new ResponseEntity<List<String>>(aptService.searchByCode(aptCodes), HttpStatus.OK);
	}
	
}
