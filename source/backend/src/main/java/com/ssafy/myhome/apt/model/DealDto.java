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
@ApiModel(value = "DealDto : 부동산 거래 정보", description = "부동산 거래 세부 정보를 나타낸다.")
public class DealDto {
	@ApiModelProperty(value="거래금액")
	private String dealAmount;
	@ApiModelProperty(value="거래년도")
	private int dealYear;
	@ApiModelProperty(value="거래월")
	private int dealMonth;
	@ApiModelProperty(value="평수")
	private String area;
	@ApiModelProperty(value="층수")
	private String floor;
	@ApiModelProperty(value="아파트코드")
	private String aptCode;
}
