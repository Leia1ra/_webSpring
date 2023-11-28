package com.multi.campus.vo;

import lombok.*;
@Data // @Getter @Setter @ToString, equals 다 만들어주는 것
@NoArgsConstructor  // 매개변수(Parameter)가 없는 생성자
@AllArgsConstructor // 매개변수(Parameter)가 있는 생성자
public class BoardVO {
    private int no;
    private String subject;
    private String content;
    private String userid;
    private int hit;
    private String writedate;
    private String ip;
}
