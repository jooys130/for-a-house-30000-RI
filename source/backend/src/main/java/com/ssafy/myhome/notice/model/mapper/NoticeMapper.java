package com.ssafy.myhome.notice.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.myhome.notice.model.NoticeDto;

@Mapper
public interface NoticeMapper {
	void writeArticle(NoticeDto NoticeDto) throws SQLException;

	void registerFile(NoticeDto NoticeDto) throws Exception;

	List<NoticeDto> listArticle(Map<String, Object> param) throws SQLException;

	int getTotalArticleCount(Map<String, Object> param) throws SQLException;

	NoticeDto getArticle(int articleNo) throws SQLException;

	void updateHit(int articleNo) throws SQLException;

	void modifyArticle(NoticeDto NoticeDto) throws SQLException;

	void deleteFile(int articleNo) throws Exception;

	void deleteArticle(int articleNo) throws SQLException;

//	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
}
