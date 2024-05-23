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
@ApiModel(value="AptDto : 아파트 정보", description = "아파트의 정보를 나타낸다")
public class AptDto {
	@ApiModelProperty(value="아파트 코드")
	private String aptCode;
	@ApiModelProperty(value="준공년도")
	private int buildYear; 
	@ApiModelProperty(value="동 이름")
	private String dong;
	@ApiModelProperty(value="본번")
	private String bonbun;
	@ApiModelProperty(value="부번")
	private String bubun;
	@ApiModelProperty(value="시군구코드")
	private String sigunguCode; 
	@ApiModelProperty(value="동코드")
	private String dongCode;
	@ApiModelProperty(value="아파트명")
	private String apartmentName;
	@ApiModelProperty(value="경도")
	private String lng;
	@ApiModelProperty(value="위도")
	private String lat;
	@ApiModelProperty(value="좋아요 여부")
	private String likeStatus = "0";
}
