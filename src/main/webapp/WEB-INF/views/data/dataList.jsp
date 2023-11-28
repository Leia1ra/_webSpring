<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
    .dataList{
        /*margin: 30px 0 10px;*/
        overflow: auto;
    }
    .dataList>li{
        float: left;
        height: 40px;
        line-height: 40px;
        border-bottom: 1px solid #ddd;
        width: 10%;
    }
    .dataList>li:nth-child(5n+2){
        width: 60%;
        /*말줄임*/
        white-space: nowrap;/* 줄바꿈 x */
        overflow:hidden;/*넘친데이터 숨김*/
        text-overflow: ellipsis;
    }
</style>
<main>
    <h1>자료실 글 목록</h1>
    <!-- 로그인 상태일 때 -->
    <c:if test="${logStatus=='Y'}">
        <div>
            <a href="<%=request.getContextPath()%>/data/write">자료 올리기</a>
        </div>
    </c:if>

    <!-- 글 목록 -->
    <ul class="dataList">
        <li>번호</li>
        <li>제목</li>
        <li>글쓴이</li>
        <li>조회수</li>
        <li>등록일</li>

        <c:forEach var="dVO" items="${list}">
            <li>${dVO.no}</li>
            <li><a href="<%=request.getContextPath()%>/data/view/${dVO.no}">${dVO.subject}</a></li>
<%--            <li><a href="<%=request.getContextPath()%>/data/view?no=${dVO.no}">${dVO.subject}</a></li>--%>
            <li>${dVO.userid}</li>
            <li>${dVO.hit}</li>
            <li>${dVO.writedate}</li>

        </c:forEach>
    </ul>
</main>