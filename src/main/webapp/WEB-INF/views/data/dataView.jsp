<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
    function dataDel() {
        if(confirm("글을 정말로 삭제하시겠습니까?")){
            location.href = "<%=request.getContextPath()%>/data/del/${vo.no}";
        }
    }

</script>
<main>
    <h1>글 내용 보기</h1>
    <ul>
        <li>번호 : ${vo.no}</li>
        <li>글쓴이 : ${vo.userid}</li>
        <li>조회수 : ${vo.hit}</li>
        <li>등록일 : ${vo.hit}</li>
        <li>제목 : ${vo.subject}</li>
        <li>${vo.content}</li>
        <li>
            첨부파일:
            <c:forEach var="f" items="${fileList}">
                <a href="${pageContext.servletContext.contextPath}/uploadFile/${f.filename}" download>${f.filename}</a>
            </c:forEach>
        </li>
    </ul>
    <div>
        <c:if test="${logStatus == 'Y' && logId == vo.userid}">
            <a href="<%=request.getContextPath()%>/data/edit/${vo.no}">수정</a>
            <a href="javascript:dataDel()">삭제</a>
        </c:if>
    </div>
</main>