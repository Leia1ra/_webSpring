package com.multi.campus.controller;

import com.multi.campus.service.DataService;
import com.multi.campus.vo.DataVO;
import com.multi.campus.vo.DatafileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/data")
public class DataController {
    @Autowired
    DataService service;

    //자료실 목록
    @GetMapping("/list")
    public String dataList(Model model){
        List<DataVO> list = service.dataList();
        model.addAttribute("list", list);

        return "data/dataList";
    }

    @GetMapping("/write")
    public String dataWrite(){
        return "data/dataWrite";
    }

    @PostMapping("/writeOk")
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public ModelAndView writeOk(DataVO vo, HttpSession session, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        // 1. (Request)제목 글내용, (session)글쓴이
        vo.setUserid((String)session.getAttribute("logId"));

        // 2. 파일 업로드(rename) ================================
        // > 업로드할 위치 폴더 구하기(절대주소)
        String path = session.getServletContext().getRealPath("/uploadFile");
        System.out.println("path : " + path);
        // > HttpServletRequest -> MultipartHttpSerbletRequest객체를 구한다.
        MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
        // > mr객체 내에 파일의 수만큼 MultipartFile객체가 있다.
        List<MultipartFile> filesList = mr.getFiles("filename"); // input 태그의 name 속성값

        // 모듈화
        List<DatafileVO> uploadFileList = new DataController().fileUpload(path, filesList);
        //

        try{
            // 3. DB 레코드 추가(원글 1 : 업로드 파일 N) ================
            // 원글 insert 추가 -> no를 구해와야 한다.
            int result = service.dataInsert(vo);
            System.out.println(vo.getNo());

            // 업로드한 파일명들 insert
            for(DatafileVO fvo: uploadFileList){
                fvo.setNo(vo.getNo());
            }
            int fileResult = service.dataFileInsert(uploadFileList);
            // 4. 추가 성공하면 -> 자료실 목록
            mav.setViewName("redirect:list");
        } catch (Exception e){
            e.printStackTrace();
            // 5. 실패하면 레코드와, 파일을 삭제하고 글올리기 폼으로
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            // 이미 업로드 된 파일을 삭제
            for(DatafileVO fvo : uploadFileList){
                File f = new File(path, fvo.getFilename());
                f.delete();
            }
            // 글 등록 폼으로 이동
            mav.setViewName("data/dataResult");
        }

        return mav;
    }

    //글 내용 보기
    @GetMapping("view/{no}") //@PathVariable : url매핑주소에 변수 없이 전송되는 데이터를 request하는 어노테이션
    public ModelAndView dataView(@PathVariable("no") int no){
        // 1. 조회수
        service.dataHitCount(no);

        // 2. 원글 선택(no같은 레코드)
        DataVO vo = service.dataSelect(no);

        // 3. 원글에 첨부된 파일 선택
        List<DatafileVO> fileList = service.getDataFile(no);

        ModelAndView mav = new ModelAndView();
        mav.addObject("vo", vo);
        mav.addObject("fileList", fileList);
        mav.setViewName("data/dataView");

        return mav;
    }

    // 글 수정 폼
    @GetMapping("/edit/{no}")
    public ModelAndView dataEdit(@PathVariable("no") int no){
        ModelAndView mav = new ModelAndView();
        // 원글 선택
        mav.addObject("vo", service.dataSelect(no));
        // 첨부 파일
        List<DatafileVO> fList = service.getDataFile(no);
        mav.addObject("fList", fList);
        mav.addObject("fileCount", fList.size());// 첨부파일의 수
        // view 페이지
        mav.setViewName("data/dataEdit");
        return mav;
    }

    // 자료실 글 수정(DB)
    @PostMapping("/editOk")
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public ModelAndView dataEditOk(DataVO vo, HttpServletRequest request){
        vo.setUserid((String) request.getSession().getAttribute("logId"));

        //1. 삭제한 파일 목록 vo.getDelFile();
        //2. 이미 이전에 업로드한 첨부파일을 얻어오기
        List<DatafileVO> dbFileList = service.getDataFile(vo.getNo());

        //3. 새로 업로드 파일 처리
        String path = request.getSession().getServletContext().getRealPath("/uploadFile");
        MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
        List<MultipartFile> mrFileList = mr.getFiles("filename");

        // 새로 추가한 파일 목록
        List<DatafileVO> newFileList = fileUpload(path, mrFileList);

        // 데이터베이스 업데이트
        ModelAndView mav = new ModelAndView();
        try{
            // 1. 삭제 파일, 새로이 업로드 된 파일, DB파일을 정리함
            // 삭제 파일이 있으면 DBFileList에서 삭제파일VO제거
            if(vo.getDelFile() != null && vo.getDelFile().size()>0){
                for(int i = 0; i<vo.getDelFile().size(); i++){
                    String del = vo.getDelFile().get(i);
                    for(int j = 0; j<dbFileList.size(); j++){
                        DatafileVO dfvo = dbFileList.get(j);
                        if(del.equals(dfvo.getFilename())){
                            dbFileList.remove(j);
                            j--;
                        }
                    }
                }
            }// 파일 삭제
            // 새로 업로드된 파일을 DBFile로 추가하는 작업
            if(newFileList.size()>0){
                for(DatafileVO dfvo :newFileList){
                    dfvo.setNo(vo.getNo());
                    dbFileList.add(dfvo);
                }
            }

            // 3. 기존 첨부파일 삭제
            int delResult = service.dataDelete(vo.getNo());

            // 4. 첨부파일(DB) 추가(Insert)
            int insertResult = service.dataFileInsert(dbFileList);

            // 2. 원글 업데이트
            int result = service.dataUpdate(vo);

            // 5. 삭제된 파일 제거
            if(vo.getDelFile() != null && vo.getDelFile().size()>0){
                for(String delFileName :vo.getDelFile()){
                    File f = new File(path, delFileName);
                    f.delete();
                }
            }

            // 6. 뷰로 이동 -> 글 내용
            mav.setViewName("redirect:view/"+vo.getNo());
        }catch (Exception e){
            e.printStackTrace();
            // 1. rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 2. 새로 업로드한 파일 지우기
            for(DatafileVO dfvo:newFileList){
                File f = new File(path, dfvo.getFilename());
                f.delete();
            }
            // 3. 이전페이지로 이동(자료실 수정페이지)
            mav.setViewName("data/dataResult");
        }
        return mav;
    }

    // 자료실 삭제
    @GetMapping("/del/{no}")
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public ModelAndView dataDelete(@PathVariable("no") int no, HttpSession session){
        ModelAndView mav = new ModelAndView();
        String userid = (String) session.getAttribute("logId");
        try{
            // 첨부된 파일명을 먼저선택 -> 파일삭제를 위해서
            List<DatafileVO> fileList= service.getDataFile(no);

            String path = session.getServletContext().getRealPath("/uploadFile");
            int result = service.dataRecordDelete(no, userid); // 원글, 첨부파일명 레코드가 삭제된다 (Cascade 때문에)

            for (DatafileVO dfvo:fileList) {
                File f = new File(path,dfvo.getFilename());
                f.delete();
            }
            // 삭제 되었을 때
            mav.setViewName("redirect:/data/list");

        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 삭제 못했을 때
            mav.setViewName("redirect:/data/view/"+no);
        }
        return mav;
    }

    // 파일 업로드 구현
    public List<DatafileVO> fileUpload(String path, List<MultipartFile> filesList){
        // 업로드한 파일 목록을 보관 DatafileVO -> list
        List<DatafileVO> uploadFileList = new ArrayList<DatafileVO>();

        if(filesList != null){ // 업로드 파일이 있을 때 if 1
            for(int i = 0; i<filesList.size(); i++){ // 첨부된 파일만큼 반복 수행 for 1
                MultipartFile mf = filesList.get(i);
                String orgFileName = mf.getOriginalFilename();// 파일 이름
                if(orgFileName != null && !orgFileName.equals("")){ // 원래 파일명이 있으면 if 2
                    // 이미 서버에 같은 파일명이 있으면 rename 수행해야 한다.
                    File f = new File(path, orgFileName);
                    if(f.exists()){ // 파일이 있으면 true if 3
                        // 파일명 찾기
                        for(int renameNum = 1; ; renameNum++){ // fileIndexing for 2
                            // 확장자와 파일명을 분리
                            int point = orgFileName.indexOf(".");
                            String fileNameNoExt = orgFileName.substring(0, point);// 파일명(확장자 제외)
                            String ext = orgFileName.substring(point+1); // 확장자

                            // 새로운 파일명 만들기
                            String newFilename= fileNameNoExt + "(" + renameNum +")." + ext;
                            f = new File(path, newFilename);
                            if(!f.exists()){ // 새로운 파일명이 서버에 있는지 확인 if 4
                                orgFileName = newFilename;
                                break;
                            } // if 4
                        } // for 2

                    } // if 3

                    // 업로드
                    try {
                        mf.transferTo(f);// 실제 서버에 업로드 된다
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 업로드할 파일 보관
                    DatafileVO fvo = new DatafileVO();
                    fvo.setFilename(orgFileName);
                    uploadFileList.add(fvo);
                } // if 2
            } // for 1
        } // if 1

        return uploadFileList;
    }
}
