package com.ssafy.myhome.notice.model.service;

import java.util.Map;

import com.ssafy.myhome.notice.model.NoticeDto;
import com.ssafy.myhome.notice.model.NoticeListDto;

public interface NoticeService {

	void writeArticle(NoticeDto boardDto) throws Exception;
	NoticeListDto listArticle(Map<String, String> map) throws Exception; 
	NoticeDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	void modifyArticle(NoticeDto boardDto) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
}
