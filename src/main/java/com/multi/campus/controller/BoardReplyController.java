package com.multi.campus.controller;

import com.multi.campus.service.BoardReplyService;
import com.multi.campus.vo.BoardReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller @RequestMapping("/boardReply")
public class BoardReplyController {
    @Autowired
    BoardReplyService service;

    // 댓글 등록
    @PostMapping("/write")
    @ResponseBody
    public int replyWrite(BoardReplyVO vo, HttpSession session){
        vo.setUserid((String) session.getAttribute("logId"));

        int result = service.replyInsert(vo);
        return result;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<BoardReplyVO> replyList(int no){
        List<BoardReplyVO> replyList = service.replySelect(no);
        for(BoardReplyVO k : replyList){
            System.out.println(k.toString());
        }
        return replyList;
    }

    @PostMapping("/editOk")
    @ResponseBody
    public String replyEdit(BoardReplyVO vo){
        System.out.println(vo.toString());
        return service.replyUpdate(vo) + "";
    }

    @GetMapping("/delete")
    @ResponseBody
    public String replyDelete(int replyno){
        return service.replyDelete(replyno) + "";
    }
}
