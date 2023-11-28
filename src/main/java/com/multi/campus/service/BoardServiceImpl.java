package com.multi.campus.service;

import com.multi.campus.mapper.BoardMapper;
import com.multi.campus.vo.BoardVO;
import com.multi.campus.vo.PagingVO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{
    @Inject
    BoardMapper mapper;

    @Override
    public int boardInsert(BoardVO vo) {
        return mapper.boardInsert(vo);
    }

    @Override
    public List<BoardVO> boardPageList(PagingVO pvo) {
        return mapper.boardPageList(pvo);
    }

    @Override
    public int totalRecord(PagingVO pvo) {
        return mapper.totalRecord(pvo);
    }

    @Override
    public BoardVO boardSelect(int no) {
        return mapper.boardSelect(no);
    }

    @Override
    public void hitCount(int no) {
        mapper.hitCount(no);
    }

    @Override
    public int boardUpdate(BoardVO vo) {
        return mapper.boardUpdate(vo);
    }

    @Override
    public int boardDelete(int no) {
        return mapper.boardDelete(no);
    }
    /*mapper는 원래 DAO 클래스라고 생각하면 된다고 함 */

}
