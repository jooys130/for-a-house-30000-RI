package com.ssafy.myhome.user.model;

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
@ApiModel(value = "MemberDto : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class UserDto {
	
	@ApiModelProperty(value = "아이디")
	private String userId;
	@ApiModelProperty(value = "이름")
	private String name;
	@ApiModelProperty(value = "비밀번호")
	private String password;
	@ApiModelProperty(value = "이메일 아이디")
	private String emailAccount;
	@ApiModelProperty(value = "이메일 도메인")
	private String emailDomain;
	@ApiModelProperty(value =  "유저 타입")
	private int type;
	@ApiModelProperty(value =  "프로필이미지")
	private String profileImg;
	@ApiModelProperty(value = "accessToken")
	private String accessToken;
	@ApiModelProperty(value = "refreshToken")
	private String refreshToken;
	@ApiModelProperty(value = "가입일")
	private String createdTime;
	@ApiModelProperty(value = "수정일")
	private String	updatedTime;
	@ApiModelProperty(value = "삭제일")
	private String deletedTime;
}
