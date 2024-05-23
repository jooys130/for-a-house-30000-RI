package com.ssafy.myhome.user.model.service;

import java.util.List;

import com.ssafy.myhome.user.model.UserDto;

public interface UserService {
	UserDto login(UserDto UserDto) throws Exception;
	UserDto userInfo(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;
	void regist(UserDto userDto) throws Exception;
	void remove(String userId) throws Exception;
	void update(UserDto userDto) throws Exception;
}
