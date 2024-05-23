package com.ssafy.myhome.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.stereotype.Component;

@Component
// SHA-256 알고리즘을 활용한 해시함수
public class SHAUtil {
	
	// 1. Message
	public static String sha256(String userPWD) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(userPWD.getBytes(StandardCharsets.UTF_8));
			
			StringBuilder hexString = new StringBuilder();
			
			for(byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				
				if(hex.length()==1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			
			return hexString.toString();
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
