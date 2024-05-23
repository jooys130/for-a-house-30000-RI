package com.ssafy.myhome.notice.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.myhome.notice.model.NoticeDto;
import com.ssafy.myhome.notice.model.NoticeListDto;
import com.ssafy.myhome.notice.model.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	NoticeMapper mapper; 
	
	

	public NoticeServiceImpl(NoticeMapper mapper) {
		super();
		this.mapper = mapper;
	}

	@Override
	public void writeArticle(NoticeDto boardDto) throws Exception {
		System.out.println(boardDto);
		mapper.writeArticle(boardDto);
	}

	@Override
	public NoticeListDto listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "b.user_id");
		List<NoticeDto> list = mapper.listArticle(param);

		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
		int totalArticleCount = mapper.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		NoticeListDto noticeListDto = new NoticeListDto();
		noticeListDto.setArticles(list);
		noticeListDto.setCurrentPage(currentPage);
		noticeListDto.setTotalPageCount(totalPageCount);
		
		System.out.println(noticeListDto);

		return noticeListDto;
	}

	@Override
	public NoticeDto getArticle(int articleNo) throws Exception {
		return mapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		System.out.println("updateHit (Service) >>>>> " + articleNo);
		mapper.updateHit(articleNo);
	}

	@Override
	public void modifyArticle(NoticeDto noticeDto) throws Exception {
		mapper.modifyArticle(noticeDto);

	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		mapper.deleteArticle(articleNo);

	}

}
