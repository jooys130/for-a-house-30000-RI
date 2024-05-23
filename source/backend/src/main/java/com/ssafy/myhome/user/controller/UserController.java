package com.ssafy.myhome.user.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.myhome.apt.model.FavDto;
import com.ssafy.myhome.apt.model.service.AptService;
import com.ssafy.myhome.user.model.UserDto;
import com.ssafy.myhome.user.model.service.UserService;
import com.ssafy.myhome.util.JWTUtil;
import com.ssafy.myhome.util.SHAUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	private UserService userService;
	private AptService aptService;
	private JWTUtil jwtUtil;
	private SHAUtil shaUtil;

	public UserController(UserService userService, AptService aptService, JWTUtil jwtUtil, SHAUtil shaUtil) {
		super();
		this.userService = userService;
		this.aptService = aptService;
		this.jwtUtil = jwtUtil;
		this.shaUtil = shaUtil;
	}

	@ApiOperation(value = "회원가입", notes = "회원 정보를 받아 회원 가입 처리")
	@PostMapping("/regist")
	public ResponseEntity<Map<String, Object>> regist(
			@RequestBody @ApiParam(value =" 회원가입을 위한 회원정보(아이디, 비밀번호).", required = true) UserDto userDto) {
		System.out.println("이전 pwd " + userDto.getPassword());
		System.out.println("암호화 pwd " + shaUtil.sha256(userDto.getPassword()));
		
		// 서버에 요청을 받음
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			// 회원가입
			String p_pwd = userDto.getPassword();
			userDto.setPassword(shaUtil.sha256(p_pwd));
			System.out.println("(****) regist user : " + userDto);
			userService.regist(userDto);
			status = HttpStatus.CREATED;
		} catch (Exception e) {
			log.debug("회원가 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) UserDto userDto) {
		log.debug("login user : {}", userDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			String input_pwd = userDto.getPassword();
			userDto.setPassword(shaUtil.sha256(input_pwd));
			UserDto loginUser = userService.login(userDto);
			System.out.println(loginUser);
			if(loginUser != null) {
				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
				log.info("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);
//				발급받은 refresh token을 DB에 저장.
				//System.out.println("access : " + accessToken);
				//System.out.println("refresh : " + refreshToken);
				userService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				
//				JSON으로 token 전달.
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				
				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			} 
			
		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDto UserDto = userService.userInfo(userId);
				resultMap.put("userInfo", UserDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable ("userId") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto UserDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, UserDto : {}", token, UserDto);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(UserDto.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(UserDto.getUserId());
				log.debug("token : {}", accessToken);
				log.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			log.debug("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원 정보 삭제", notes = "해당 유저의 정보를 삭제한다", response = Map.class)
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> delete(@PathVariable ("userId") @ApiParam(value = "삭제할 회원의 아이디", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		System.out.println("delete 시도" + userId);
		try {
			userService.remove(userId);
			status = HttpStatus.OK;
			resultMap.put("status", 200);
		} catch (Exception e) {
			log.error("변경 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}
	
	@ApiOperation(value = "회원 정보 변경", notes = "해당 유저의 정보를 번경한다", response = Map.class)
	@PostMapping("/update")
	public ResponseEntity<?>update(@RequestBody UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			String p_pwd = userDto.getPassword();
			userDto.setPassword(shaUtil.sha256(p_pwd));
			userService.update(userDto);
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("변경 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}
	
	@ApiOperation(value = "좋아요 리스트 표시", notes = "유저가 하트를 누른 매물을 보여준다", response = Map.class)
	@GetMapping("/mylike/{userId}")
	public ResponseEntity<?>showlike(@PathVariable("userId") @ApiParam(value = "조회할 회원의 아이디", required = true) String userId){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		System.out.println("조회할 회원의 아이디 : " + userId);
		try {
			return new ResponseEntity<List<FavDto>>(aptService.likeSearchAll(userId), status);
		} catch (Exception e) {
			log.error("변경 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(null, status);
		
	}
	
}

