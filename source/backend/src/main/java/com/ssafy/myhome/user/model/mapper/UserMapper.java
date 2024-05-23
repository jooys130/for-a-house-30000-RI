package com.ssafy.myhome.user.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.myhome.user.model.UserDto;

@Mapper
public interface UserMapper {
	UserDto login(UserDto UserDto) throws SQLException;
	UserDto userInfo(String userId) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	void regist(UserDto userDto) throws SQLException;
	void remove(String userId) throws SQLException;
	void update(UserDto userDto) throws SQLException;
}
