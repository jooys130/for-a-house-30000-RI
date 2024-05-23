package com.ssafy.myhome.config;

public class ChatGptConfig {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String API_KEY = "다주김"; //실행시 원래 api key로 바꾸고, git에 올릴 때에는 임의 값으로 다시 설정!
    public static final String MODEL = "gpt-3.5-turbo-instruct";
    public static final Integer MAX_TOKEN = 300;
    public static final Double TEMPERATURE = 0.0;
    public static final Double TOP_P = 1.0;
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    public static final String URL = "https://api.openai.com/v1/completions";
}
