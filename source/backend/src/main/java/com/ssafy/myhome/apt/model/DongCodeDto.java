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
@ApiModel(value = "DongCodeDto : 동코드 정보", description = "시도,구군,동에 따른 동코드를 나타낸다.")
public class DongCodeDto {
	@ApiModelProperty(value="동코드")
	private String dongCode;
	@ApiModelProperty(value="시도명")
	private String sidoName;
	@ApiModelProperty(value="구군명")
	private String gugunName;
	@ApiModelProperty(value="동이름")
	private String dongName;

}
