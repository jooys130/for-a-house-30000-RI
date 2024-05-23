package com.ssafy.myhome.apt.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.myhome.apt.model.AptDto;
import com.ssafy.myhome.apt.model.DealDto;
import com.ssafy.myhome.apt.model.FavDto;
import com.ssafy.myhome.apt.model.InfoDto;

public interface AptMapper {
	List<AptDto> getApt(Map	<String,String> map) throws SQLException;
	void saveKeyword(Map<String,String> map) throws SQLException;
	List<DealDto> dealSearch(String aptCode) throws SQLException;
	void likeApt(Map<String,String> map) throws SQLException;
	List<Integer> getValidYear(String aptCode) throws SQLException;
	void deletelikeApt(Map<String, String> map)throws SQLException;
	FavDto likeSearch(Map<String, String> map) throws SQLException;
	void updatelikeApt(Map<String, String> map)throws SQLException;
	List<FavDto> likeSearchAll(String userId) throws SQLException;
	List<InfoDto> searchByName(String aptName) throws SQLException;
	List<String> searchByCode(List<String> aptCodes) throws SQLException;
}
