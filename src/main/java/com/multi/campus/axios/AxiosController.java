package com.multi.campus.axios;

import com.multi.campus.service.BoardService;
import com.multi.campus.vo.BoardVO;
import com.multi.campus.vo.PagingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/axios")
@RestController // 비동기식 컨트롤러
public class AxiosController {
    @Autowired
    BoardService service;

    @GetMapping("/test1")
    public String axiosTest(){
        return "정상 실행됨";
    }

    @PostMapping("/test2")
    public String axiosTest2(int num, String name){
        System.out.println("num -> " + num);
        System.out.println("name -> " + name);
        return "번호 : " + num + ", 이름 : " + name;
    }

    @GetMapping("/boardList")
    public HashMap axiosBoardList(PagingVO pVO){
        // 총레코드 수
        pVO.setTotalRecord(service.totalRecord(pVO));

        // 해당 페이지의 레코드 선택
        List<BoardVO> list = service.boardPageList(pVO);

        // int pageArr[] = new int[pVO.getOnePageCount()];
        List<Integer> pageArr = new ArrayList<Integer>();
        for(int n = pVO.getStartPage(); n< pVO.getStartPage()+ pVO.getOnePageCount(); n++){
            pageArr.add(n);
        }

        HashMap map = new HashMap();
        map.put("page", pVO);
        map.put("list", list);
        map.put("pageArr", pageArr);

        return map;
    }
}
