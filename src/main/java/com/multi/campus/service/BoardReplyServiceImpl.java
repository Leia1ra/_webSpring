package com.multi.campus.service;

import com.multi.campus.mapper.BoardReplyMapper;
import com.multi.campus.vo.BoardReplyVO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BoardReplyServiceImpl implements BoardReplyService{
    @Inject
    BoardReplyMapper mapper;

    @Override
    public int replyInsert(BoardReplyVO vo) {
        return mapper.replyInsert(vo);
    }

    @Override
    public List<BoardReplyVO> replySelect(int no) {
        return mapper.replySelect(no);
    }

    @Override
    public int replyUpdate(BoardReplyVO vo) {
        return mapper.replyUpdate(vo);
    }

    @Override
    public int replyDelete(int replyno) {
        return mapper.replyDelete(replyno);
    }
}
