package com.ssafy.myhome.user.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.myhome.exception.UserException;
import com.ssafy.myhome.user.model.UserDto;
import com.ssafy.myhome.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	UserMapper mapper; 
	
	public UserServiceImpl(UserMapper mapper) {
		super();
		this.mapper = mapper;
	}

	@Override
	public UserDto login(UserDto userDto) throws Exception {
		try {
			System.out.println(userDto);
			System.out.println("login Service!!!!!!!!!");
			return mapper.login(userDto);
		} catch (SQLException e) {
            throw new UserException("로그인 처리 중 오류 발생");
        }
	}

	@Override
	public UserDto userInfo(String userId) throws Exception {
        try {
        	// search
    		System.out.println("userInfo Service!!!!!!!!!");
    		return mapper.userInfo(userId);
        } catch (SQLException e) {
            throw new UserException("회원 조회 중 오류 발생");
        }
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		System.out.println("saveRefreshToken Service!!!!!!!!!");
		Map<String, String> map = new HashMap<String, String>();
		
		
		map.put("userId", userId);
		map.put("token", refreshToken);
		
		System.out.println("(*)" + map.toString());
		
		mapper.saveRefreshToken(map);

	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		System.out.println("getRefreshToken Service!!!!!!!!!");
		return mapper.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		System.out.println("deleRefreshToken Service!!!!!!!!!");
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		mapper.deleteRefreshToken(map);
	}

	@Override
	public void regist(UserDto userDto) throws Exception {
		try {
        	UserDto find = mapper.userInfo(userDto.getUserId());
			if (find != null) {
				throw new UserException("등록된 회원 정보입니다.");
			}
			System.out.println("Regist Service");
			mapper.regist(userDto); 
        } catch (SQLException e) {
            throw new UserException("회원 등록 중 오류 발생");
        }
	}

	@Override
	public void remove(String userId) throws Exception {
        try {
        	UserDto find = mapper.userInfo(userId);
        	if (find == null)
                throw new UserException("등록되지 않은 아이디입니다.");
        	mapper.remove(userId);
        } catch (SQLException e) {
            throw new UserException("회원 삭제 중 오류 발생");
        }
	}

	@Override
	public void update(UserDto userDto) throws Exception {
		try {
        	UserDto find = mapper.userInfo(userDto.getUserId());
			if (find == null) {
				throw new UserException("등록되지 않은 회원 정보입니다.");
			}
			System.out.println("!!!!!" + userDto);
			mapper.update(userDto);
        } catch (SQLException e) {
            throw new UserException("회원 정보 업데이트 중 오류 발생");
        }
	}


	
	
}
