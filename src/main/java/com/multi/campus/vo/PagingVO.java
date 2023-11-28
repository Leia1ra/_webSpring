package com.multi.campus.vo;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class PagingVO {
    private int nowPage = 1;        // 현재 페이지
    private int onePageRecord = 5;  // 한번에 표시할 레코드 수

    private int totalRecord;        // 총 레코드 수
    private int totalPage;          // 총 페이지 수
    private int offsetPoint = (nowPage-1) * onePageRecord; // 레코드 선택시 시작 위치

    // 페이지 넘버링
    private int onePageCount = 5; // 한번에 출력할 페이지 수
    private int startPage = 1;    // 시작 페이지 번호

    // 검색어
    private String searchKey;
    private String searchWord;

    public int getNowPage() {
        return nowPage;
    }
    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
        //해당페이지 위치를 꼐산
        offsetPoint = (nowPage-1)*onePageRecord;

        // 시작페이지 : 현재 페이지번호가 바뀔때
        startPage = (nowPage-1)/onePageCount*onePageCount+1;

    }


    public int getOnePageRecord() {
        return onePageRecord;
    }
    public void setOnePageRecord(int onePageRecord) {
        this.onePageRecord = onePageRecord;
    }



    public int getTotalRecord() {
        return totalRecord;
    }
    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        totalPage = (int)Math.ceil(totalRecord/(double)onePageRecord);
    }



    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;

    }



    public int getOffsetPoint() {
        return offsetPoint;
    }
    public void setOffsetPoint(int offsetPoint) {
        this.offsetPoint = offsetPoint;
    }



    public int getOnePageCount() {
        return onePageCount;
    }
    public void setOnePageCount(int onePageCount) {
        this.onePageCount = onePageCount;
    }



    public int getStartPage() {
        return startPage;
    }
    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }




    public String getSearchKey() {
        return searchKey;
    }
    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }



    public String getSearchWord() {
        return searchWord;
    }
    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}
