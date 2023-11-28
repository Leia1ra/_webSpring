package com.multi.campus.controller;

import com.multi.campus.service.BoardService;
import com.multi.campus.vo.BoardVO;
import com.multi.campus.vo.PagingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BoardController {
    @Autowired
    BoardService service;

    @GetMapping("/board/list")
    public ModelAndView boardList(PagingVO pvo){
        ModelAndView mav = new ModelAndView();
        // 총레코드수
        pvo.setTotalRecord(service.totalRecord(pvo));

        // DB검색
        List<BoardVO> list = service.boardPageList(pvo);

        mav.addObject("pVO",pvo);
        mav.addObject("list",list);
        mav.setViewName("board/boardList");
        return mav;
    }

    @GetMapping("/board/write")
    public String boardWrite(){
        return "board/boardWrite";
    }

    @PostMapping("/board/writeOk")
    public ModelAndView boardWriteOk(BoardVO vo, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        vo.setIp(request.getRemoteAddr());
        vo.setUserid((String)request.getSession().getAttribute("logId"));

        int result = service.boardInsert(vo);
        if(result > 0) {
            mav.setViewName("redirect:list");
        } else {
            mav.addObject("msg", "등록");
            mav.setViewName("board/boardResult");
        }
        return mav;
    }

    @GetMapping("/board/view")
    public ModelAndView boardView(int no, PagingVO pVO){
        service.hitCount(no);

        BoardVO vo = service.boardSelect(no);
        ModelAndView mav = new ModelAndView();
        mav.addObject("vo", vo);
        mav.addObject("pVO",pVO);
        mav.setViewName("board/boardView");
        return mav;
    }

    @GetMapping("/board/edit")
    public ModelAndView boardEdit(int no){
        ModelAndView mav = new ModelAndView();
        mav.addObject("vo", service.boardSelect(no));
        mav.setViewName("board/boardEdit");

        return mav;
    }
    @PostMapping("/board/editOk")
    public ModelAndView boardEditOk(BoardVO vo){
        ModelAndView mav = new ModelAndView();
        int result = service.boardUpdate(vo);
        if(result > 0){
            mav.setViewName("redirect:view?no="+vo.getNo());
        } else {
            mav.addObject("msg", "수정");
            mav.setViewName("board/boardResult");
        }
        return mav;
    }

    @GetMapping("board/delete")
    public ModelAndView boardDelete(int no){
        ModelAndView mav = new ModelAndView();
        int result = service.boardDelete(no);
        if(result>0){
            mav.setViewName("redirect:list");
        } else {
            mav.setViewName("redirect:view?no="+no);
        }
        return mav;
    }
}
