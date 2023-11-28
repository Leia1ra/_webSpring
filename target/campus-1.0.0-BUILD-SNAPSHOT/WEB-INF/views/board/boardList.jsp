<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
    #boardTop{
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }
    /*#boardTop>div{
        float: left;
        width: 50%;
    }
    #boardTop>div:last-of-type{
        text-align: right;
    }*/
    #boardList, .page>ul{
        margin: 30px 0 10px;
        overflow: auto;
    }
    #boardList>li{
        float: left;
        height: 40px;
        line-height: 40px;
        border-bottom: 1px solid #ddd;
        width: 10%;
    }
    #boardList>li:nth-child(5n+2){
        width: 60%;
        /*말줄임*/
        white-space: nowrap;/* 줄바꿈 x */
        overflow:hidden;/*넘친데이터 숨김*/
        text-overflow: ellipsis;
    }

    .page li{
        float: left;
        height: 40px;
        line-height: 40px;
        padding: 0 10px;
    }

    .search{
        text-align: center;
    }
</style>
<script>
    function searchCheck() {
        if(document.getElementById("searchWord").value == ""){
            alert("검색어를 입력 후 검색하세요");
            return false;
        }
    }
</script>
<main>
    <h1>게시판 목록</h1>
    <c:if test="${logStatus !='' && logStatus == 'Y'}">
        <div><a href="<%=request.getContextPath()%>/board/write">글쓰기</a></div>
    </c:if>
    <div id="boardTop">
        <div>총 레코드 수 : ${pVO.totalRecord}개</div>
        <div>현재 페이지 : ${pVO.nowPage}/${pVO.totalPage}</div>
    </div>
    <ul id="boardList">
        <li>번호</li>
        <li>제목</li>
        <li>글쓴이</li>
        <li>조회수</li>
        <li>등록일</li>

        <c:forEach var="bVO" items="${list}">
            <li>${bVO.no}</li>
            <li><a href="<%=request.getContextPath()%>/board/view?no=${bVO.no}&nowPage=${pVO.nowPage}<c:if test="${pVO.searchWord != null}">&searchKey=${pVO.searchKey}&searchWord=${pVO.searchWord}</c:if>">${bVO.subject}</a></li>
            <li>${bVO.userid}</li>
            <li>${bVO.hit}</li>
            <li>${bVO.writedate}</li>
        </c:forEach>

    </ul>
    <div class="page">
        <ul>
            <%--prev : 현재 보는 페이지를 기준으로 이전페이지로 이동--%>
            <c:if test="${pVO.nowPage == 1}">
                <li>prev</li>
            </c:if>
            <c:if test="${pVO.nowPage>1}">
                <li><a href="<%=request.getContextPath()%>/board/list?nowPage=${pVO.nowPage - 1}<c:if test="${pVO.searchWord != null}">&searchKey=${pVO.searchKey}&searchWord=${pVO.searchWord}</c:if>">prev</a></li>
            </c:if>

            <%--page--%>
            <c:forEach var="p" begin="${pVO.startPage}" end="${pVO.startPage + pVO.onePageCount -1}">
                <c:if test="${p <= pVO.totalPage}">
                    <%--출력할 페이지 번호가 총 페이지 수보다 작거나 같을때 페이지 번호 출력--%>
                    <c:if test="${p == pVO.nowPage}"><%--현재 페이지일때--%>
                        <li style="background-color: #ddd">
                    </c:if><%--현재 페이지가 아닐때--%>
                    <c:if test="${p != pVO.nowPage}">
                        <li>
                    </c:if>
                        <a href="<%=request.getContextPath()%>/board/list?nowPage=${p}<c:if test="${pVO.searchWord != null}">&searchKey=${pVO.searchKey}&searchWord=${pVO.searchWord}</c:if>">${p}</a>
                    </li>
                </c:if>
            </c:forEach>

            <%--next--%>
            <c:if test="${pVO.nowPage < pVO.totalPage}">
                <li><a href="<%=request.getContextPath()%>/board/list?nowPage=${pVO.nowPage + 1}<c:if test="${pVO.searchWord != null}">&searchKey=${pVO.searchKey}&searchWord=${pVO.searchWord}</c:if>">next</a></li>
            </c:if>
            <c:if test="${pVO.nowPage >= pVO.totalPage}">
                <li>next</li>
            </c:if>
        </ul>
    </div>
    <div class="search">
        <form action="<%=request.getContextPath()%>/board/list" onsubmit="return searchCheck()">
            <select name="searchKey">
                <option value="subject">제목</option>
                <option value="content">글내용</option>
                <option value="userid">글쓴이</option>
            </select>
            <input type="text" name="searchWord" id="searchWord">
            <input type="submit" value="Search">
        </form>
    </div>

</main>
