package com.ssafy.myhome.apt.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.myhome.apt.model.AptDto;
import com.ssafy.myhome.apt.model.DealDto;
import com.ssafy.myhome.apt.model.FavDto;
import com.ssafy.myhome.apt.model.InfoDto;
import com.ssafy.myhome.apt.model.mapper.AptMapper;

@Service
public class AptServiceImpl implements AptService {

	private AptMapper aptMapper;
	
	
	public AptServiceImpl(AptMapper aptMapper) {
		super();
		this.aptMapper = aptMapper;
	}

	@Override
	public List<AptDto> getApt(Map<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(aptMapper.getApt(map));
		return aptMapper.getApt(map);
	}
	
	public void saveKeyword(String keyword,String userId) throws Exception
	{
		Map<String,String> map=new HashMap<String, String>();
		map.put("keyword", keyword);
		map.put("userId", userId);
		aptMapper.saveKeyword(map);
	}
	public List<DealDto> dealSearch(String aptCode) throws Exception
	{
		return aptMapper.dealSearch(aptCode);
	}

	@Override
	public void likeApt(String userId,String aptCode) throws Exception {
		Map<String,String> map=new HashMap<String, String>();
		map.put("userId", userId);
		map.put("aptCode", aptCode);
		aptMapper.likeApt(map);
	}

	@Override
	public List<Integer> getValidYear(String aptCode) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(aptMapper.getValidYear(aptCode));
		return aptMapper.getValidYear(aptCode);
	}

	@Override
	public void deletelikeApt(Map<String, String> map) throws Exception {
		aptMapper.deletelikeApt(map);
	}

	@Override
	public FavDto likeSearch(String userId, String aptCode) throws Exception {
		Map<String,String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("aptCode", aptCode);
		
		return aptMapper.likeSearch(map);
	}

	@Override
	public void updatelikeApt(Map<String, String> map) throws Exception {
		aptMapper.updatelikeApt(map);
	}

	@Override
	public List<FavDto> likeSearchAll(String userId) throws Exception {
		List<FavDto> favlist = aptMapper.likeSearchAll(userId);
		return favlist;
	}

	@Override
	public List<InfoDto> searchByName(String aptName) throws Exception {
		return aptMapper.searchByName(aptName);
	}

	@Override
	public List<String> searchByCode(List<String> aptCodes) throws Exception {
		System.out.println("mapper >>>> " + aptMapper.searchByCode(aptCodes));
		return aptMapper.searchByCode(aptCodes);
	}
	
	
}
