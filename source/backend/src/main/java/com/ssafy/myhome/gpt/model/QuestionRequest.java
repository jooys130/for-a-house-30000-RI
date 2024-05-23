package com.ssafy.myhome.gpt.model;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class QuestionRequest implements Serializable {
    private String question;
}