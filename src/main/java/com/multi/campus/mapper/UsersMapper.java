package com.multi.campus.mapper;

import com.multi.campus.vo.UsersVO;

public interface UsersMapper {
    public int idCheck(String userid);/*아이디 중복검사*/

    /*회원가입*/
    public int usersInsert(UsersVO vo);

    /*로그인*/
    public UsersVO loginSelect(String userid, String userpwd);

}
