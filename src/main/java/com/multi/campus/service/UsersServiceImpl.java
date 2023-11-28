package com.multi.campus.service;

import com.multi.campus.vo.UsersVO;
import com.multi.campus.mapper.UsersMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UsersServiceImpl implements UsersService{
    @Inject
    UsersMapper mapper;
    @Override
    public int idCheck(String userid) {
        return mapper.idCheck(userid);
    }

    @Override
    public int usersInsert(UsersVO vo) {
        return mapper.usersInsert(vo);
    }

    @Override
    public UsersVO loginSelect(String userid, String userpwd) {
        return mapper.loginSelect(userid, userpwd);
    }
}
