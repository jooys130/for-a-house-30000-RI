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
public class InfoDto {
	@ApiModelProperty(value = "시")
	private String sidoName;
	@ApiModelProperty(value = "구")
	private String gugunName;
	@ApiModelProperty(value = "동")
	private String dong;
	@ApiModelProperty(value = "아파트 이름")
	private String apartmentName;
	@ApiModelProperty(value = "평균 거래 가격")
	private String dealAmount;
}
