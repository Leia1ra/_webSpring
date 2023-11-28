package com.multi.campus.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class DataVO {
    private int no;
    private String subject;
    private String content;
    private String userid;
    private int hit;
    private String writedate;

    // 삭제된 파일 정보 보관 객체, 배열, 컬랙션(List)
    private List<String> delFile;
}
