package com.multi.campus.mapper;

import com.multi.campus.vo.BoardReplyVO;

import java.util.List;

public interface BoardReplyMapper {
    public int replyInsert(BoardReplyVO vo);//댓글

    public List<BoardReplyVO> replySelect(int no);

    public int replyUpdate(BoardReplyVO vo);
    public int replyDelete(int replyno);
}
