package com.multi.campus.mapper;

import com.multi.campus.vo.BoardVO;
import com.multi.campus.vo.PagingVO;

import java.util.List;

public interface BoardMapper {
    public int boardInsert(BoardVO vo);
    public List<BoardVO> boardPageList(PagingVO pvo);
    public int totalRecord(PagingVO pvo);
    public BoardVO boardSelect(int no);
    public void hitCount(int no); // 조회수 증가
    public int boardUpdate(BoardVO vo);//게시판 수정
    public int boardDelete(int no);
}