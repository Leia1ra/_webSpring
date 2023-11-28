package com.multi.campus.service;

import com.multi.campus.vo.UsersVO;

public interface UsersService {
    public int idCheck(String userid);

    /*회원가입*/
    public int usersInsert(UsersVO vo);

    /*로그인*/
    public UsersVO loginSelect(String userid, String userpwd);
}
