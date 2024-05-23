package com.ssafy.myhome.apt.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel(value = "DongCodeDto : 좋아요 ", description = "좋아요가 눌린 아파트와 유저 정보")
public class FavDto {
	@ApiModelProperty(value = "아이디")
	private String userId;
	@ApiModelProperty(value="아파트 코드")
	private String aptCode;
}
